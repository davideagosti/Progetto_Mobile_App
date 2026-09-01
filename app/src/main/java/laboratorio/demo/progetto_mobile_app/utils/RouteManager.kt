package laboratorio.demo.progetto_mobile_app.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import java.security.MessageDigest
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import laboratorio.demo.progetto_mobile_app.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.HttpException
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

// ======================================================
// RISULTATO DEL PERCORSO
// ======================================================

data class RouteResult(
    val points: List<LatLng>,
    val distanceMeters: Int,
    val durationSeconds: Int
)

// ======================================================
// REQUEST GOOGLE ROUTES API
// ======================================================

data class RouteRequest(
    val origin: Waypoint,
    val destination: Waypoint,
    val travelMode: String = "DRIVE"//,
    //val routingPreference: String = "TRAFFIC_AWARE"
)

data class Waypoint(
    val location: RouteLocation
)

data class RouteLocation(
    val latLng: LatLngGoogle
)

data class LatLngGoogle(
    val latitude: Double,
    val longitude: Double
)

// ======================================================
// RESPONSE GOOGLE ROUTES API
// ======================================================

data class RouteResponse(
    val routes: List<Route>
)

data class Route(
    val distanceMeters: Int,
    val duration: String,
    val polyline: PolylineData
)

data class PolylineData(
    val encodedPolyline: String
)

// ======================================================
// API GOOGLE
// ======================================================

interface RoutesApi {

    @Headers(
        "Content-Type: application/json",
        "X-Goog-FieldMask: routes.distanceMeters,routes.duration,routes.polyline.encodedPolyline"
    )
    @POST("directions/v2:computeRoutes")
    suspend fun computeRoutes(
        @Header("X-Goog-Api-Key") apiKey: String,
        @Header("X-Android-Package") packageName: String,
        @Header("X-Android-Cert") certificateSha1: String,
        @Body request: RouteRequest
    ): RouteResponse
}

// ======================================================
// ROUTE MANAGER
// ======================================================

class RouteManager(
    private val context: Context
) {

    private val apiKey: String = BuildConfig.MAPS_API_KEY

    private val routesApi: RoutesApi =
        Retrofit.Builder()
            .baseUrl("https://routes.googleapis.com/")
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(RoutesApi::class.java)

    // Chiave impronta Sha-1 certificato Android
    private fun getCertificateSha1(): String {

        val packageInfo = if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
        ) {

            context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNING_CERTIFICATES
            )

        } else {

            @Suppress("DEPRECATION")
            context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNATURES
            )
        }

        val signatures = if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
        ) {

            packageInfo.signingInfo.apkContentsSigners

        } else {

            @Suppress("DEPRECATION")
            packageInfo.signatures
        }

        val certificate = signatures.first()

        val sha1 = MessageDigest
            .getInstance("SHA-1")
            .digest(certificate.toByteArray())

        return sha1.joinToString("") {
            "%02X".format(it)
        }
    }

    // ==================================================
    // CALCOLO PERCORSO
    // ==================================================

    suspend fun calculateRoute(
        origin: LatLng,
        destination: LatLng
    ): RouteResult? {

        return withContext(Dispatchers.IO) {

            try {

                println("=================================")
                println("🗺 ROUTES API")
                println("=================================")

                println("📦 PACKAGE: ${context.packageName}")

                val certificateSha1 = getCertificateSha1()

                println(
                    "🔐 CERTIFICATO SHA-1: $certificateSha1"
                )

                println(
                    "📍 ORIGIN: " +
                            "${origin.latitude}, ${origin.longitude}"
                )

                println(
                    "🏁 DESTINATION: " +
                            "${destination.latitude}, ${destination.longitude}"
                )

                val request = RouteRequest(

                    origin = Waypoint(
                        location = RouteLocation(
                            latLng = LatLngGoogle(
                                latitude = origin.latitude,
                                longitude = origin.longitude
                            )
                        )
                    ),

                    destination = Waypoint(
                        location = RouteLocation(
                            latLng = LatLngGoogle(
                                latitude = destination.latitude,
                                longitude = destination.longitude
                            )
                        )
                    )
                )

                println("📡 Invio richiesta a Google Routes API...")

                // Chiamata Google
                val response = routesApi.computeRoutes(
                    apiKey = apiKey,
                    packageName = context.packageName,
                    certificateSha1 = certificateSha1,
                    request = request
                )

                println("✅ Risposta ricevuta")

                println(
                    "📦 Numero routes: ${response.routes.size}"
                )

                // Route
                val route = response.routes.firstOrNull()
                    ?: run {

                        println("❌ Google non ha restituito nessun percorso")

                        return@withContext null
                    }

                println(
                    "📏 Distanza: ${route.distanceMeters} metri"
                )

                println(
                    "⏱ Durata: ${route.duration}"
                )

                println(
                    "🔗 Polyline: ${route.polyline.encodedPolyline}"
                )

//                val encodedPolyline =
//                    route.polyline.encodedPolyline

                // Polyline
                val decodedPoints =
                    PolyUtil.decode(
                        route.polyline.encodedPolyline
                    )
                    //PolyUtil.decode(encodedPolyline)

                println(
                    "📍 Punti polyline: ${decodedPoints.size}"
                )

                return@withContext RouteResult(
                    points = decodedPoints,
                    distanceMeters = route.distanceMeters,
                    durationSeconds = parseDuration(
                        route.duration
                    )
                )

            } catch (e: HttpException) {

//                println("❌ ROUTES API ERROR")
//                println("HTTP CODE: ${e.code()}")
//                println("ERROR BODY: ${e.response()?.errorBody()?.string()}")

                println("=================================")
                println("❌ GOOGLE ROUTES HTTP ERROR")
                println("=================================")

                println(
                    "HTTP CODE: ${e.code()}"
                )

                println(
                    "ERROR BODY: " +
                            e.response()
                                ?.errorBody()
                                ?.string()
                )

                e.printStackTrace()

                null

            } catch (e: Exception) {

                //println("❌ ERRORE ROUTE: ${e.message}")

                println("=================================")
                println("❌ ERRORE GENERICO ROUTES API")
                println("=================================")

                println("Tipo: ${e::class.java.simpleName}")
                println(
                    "MESSAGGIO: ${e.message}"
                )

                e.printStackTrace()

                null
            }
        }
    }

    // ==================================================
    // CONVERSIONE DURATA
    // ==================================================

    private fun parseDuration(
        duration: String
    ): Int {

        return try {

            duration
                .removeSuffix("s")
                .toDouble()
                .toInt()

        } catch (e: Exception) {

            0
        }
    }
}