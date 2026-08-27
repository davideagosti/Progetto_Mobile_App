package laboratorio.demo.progetto_mobile_app.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

import androidx.navigation.NavType
import androidx.navigation.navArgument

import androidx.navigation.compose.*
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

import com.google.firebase.auth.FirebaseAuth

import laboratorio.demo.progetto_mobile_app.components.AppBarConfig
import laboratorio.demo.progetto_mobile_app.components.AppScaffold
import laboratorio.demo.progetto_mobile_app.components.GreenAppBar
import laboratorio.demo.progetto_mobile_app.screens.HomeScreen
import laboratorio.demo.progetto_mobile_app.screens.login.LoginScreen
import laboratorio.demo.progetto_mobile_app.screens.register.RegisterScreen
import laboratorio.demo.progetto_mobile_app.screens.editaccount.EditAccountScreen
import laboratorio.demo.progetto_mobile_app.screens.FavoritesScreen

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding

@Composable
fun AppNavigation(
    navController: NavHostController
) {

    val currentRoute =
        navController.currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route

    // ==========================================
    // CONTROLLO SESSIONE FIREBASE
    // ==========================================

    val currentUser = FirebaseAuth.getInstance().currentUser

    val startDestination =
        if (currentUser != null) {
            Routes.Home.route
        } else {
            Routes.Login.route
        }

    val screensWithTopBar = listOf(
        Routes.Login,
        Routes.Register
    )

    // ==========================================
    // CONFIGURAZIONE DELLA SCHERMATA CORRENTE
    // ==========================================

    val currentScreen = when(currentRoute) {

        Routes.Home.route -> ScreenConfig(
            title = "",
            showTopBar = false,
            showBottomBar = false
        )

        Routes.Login.route -> ScreenConfig(
            title = "Login",
            showTopBar = true,
            showBottomBar = false
        )

        Routes.Register.route -> ScreenConfig(
            title = "Registrazione",
            showTopBar = true,
            showBottomBar = false
        )

        Routes.EditAccount.route -> ScreenConfig(
            title = "Modifica account",
            showTopBar = true,
            showBottomBar = false
        )

        Routes.Favorites.route -> ScreenConfig(
            title = "Gestisci preferiti",
            showTopBar = true,
            showBottomBar = false
        )

        else -> ScreenConfig(
            title = "",
            showTopBar = false,
            showBottomBar = false
        )
    }

    // ==========================================
    // CONFIGURAZIONE DELLA TOP BAR
    // ==========================================

    val appBarConfig = when (currentRoute) {

        Routes.Login.route ->
            GreenAppBar()

        Routes.Register.route ->
            GreenAppBar()

        Routes.EditAccount.route ->
            GreenAppBar()

        Routes.Favorites.route ->
            GreenAppBar()

        else ->
            AppBarConfig(
                backgroundColor = MaterialTheme.colorScheme.primary
            )
    }

    // ==========================================
    // SCAFFOLD
    // ==========================================

    AppScaffold(

        topBarTitle = currentScreen.title,

        showTopBar = currentScreen.showTopBar,

        showBottomBar = currentScreen.showBottomBar,

        showBackButton = currentRoute != Routes.Home.route,

        onBackClick = {

            navController.navigate(Routes.Home.route) {
                popUpTo(Routes.Home.route) {
                    inclusive = false
                }
                launchSingleTop = true
            }

        },

        appBarConfig = appBarConfig

    ) { innerPadding ->

        // ======================================
        // NAVIGATION
        // ======================================

        NavHost(
            navController = navController,
            startDestination = Routes.Home.route,

            modifier = Modifier.padding(innerPadding)
        ) {

//            composable(Routes.Home.route) {
//                HomeScreen(navController)
//            }

            composable(

                route = Routes.Home.route,

                arguments = listOf(

                    navArgument("placeId") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    },

                    navArgument("name") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    },

                    navArgument("address") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    },

                    navArgument("latitude") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    },

                    navArgument("longitude") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) { backStackEntry ->

                // ==========================================
                // RECUPERO PARAMETRI DALLA NAVIGATION
                // ==========================================

                val placeId =
                    backStackEntry.arguments
                        ?.getString("placeId")

                val name =
                    backStackEntry.arguments
                        ?.getString("name")

                val address =
                    backStackEntry.arguments
                        ?.getString("address")

                val latitude =
                    backStackEntry.arguments
                        ?.getString("latitude")
                        ?.toDoubleOrNull()

                val longitude =
                    backStackEntry.arguments
                        ?.getString("longitude")
                        ?.toDoubleOrNull()

                HomeScreen(
                    navController = navController,

                    favoritePlaceId = placeId,

                    favoritePlaceName = name,

                    favoritePlaceAddress = address,

                    favoriteLatitude = latitude,

                    favoriteLongitude = longitude
                )
            }

            composable(Routes.Register.route) {
                RegisterScreen(navController)
            }

            composable(Routes.Login.route) {
                LoginScreen(navController)
            }

            composable(Routes.EditAccount.route) {
                EditAccountScreen(navController)
            }

            composable(Routes.Favorites.route) {
                FavoritesScreen(navController)
            }
        }
    }
}