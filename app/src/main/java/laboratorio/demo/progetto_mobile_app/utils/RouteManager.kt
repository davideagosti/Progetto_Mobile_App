package laboratorio.demo.progetto_mobile_app.utils

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import laboratorio.demo.progetto_mobile_app.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    val travelMode: String = "DRIVE",
    val routingPreference: String = "TRAFFIC_AWARE"
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

    // ==================================================
    // CALCOLO PERCORSO
    // ==================================================

    suspend fun calculateRoute(
        origin: LatLng,
        destination: LatLng
    ): RouteResult? {

        return withContext(Dispatchers.IO) {

            try {

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

                val response = routesApi.computeRoutes(
                    apiKey = apiKey,
                    request = request
                )

                val route = response.routes.firstOrNull()
                    ?: return@withContext null

                val encodedPolyline =
                    route.polyline.encodedPolyline

                val decodedPoints =
                    PolyUtil.decode(encodedPolyline)

                RouteResult(
                    points = decodedPoints,
                    distanceMeters = route.distanceMeters,
                    durationSeconds = parseDuration(
                        route.duration
                    )
                )

            } catch (e: Exception) {

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