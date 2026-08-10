package laboratorio.demo.progetto_mobile_app.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.compose.*

import laboratorio.demo.progetto_mobile_app.components.AppScaffold
import laboratorio.demo.progetto_mobile_app.screens.*

import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

import laboratorio.demo.progetto_mobile_app.R
import laboratorio.demo.progetto_mobile_app.components.AppBarConfig
import laboratorio.demo.progetto_mobile_app.screens.HomeScreen
import laboratorio.demo.progetto_mobile_app.screens.login.LoginScreen
import laboratorio.demo.progetto_mobile_app.screens.register.RegisterScreen
import laboratorio.demo.progetto_mobile_app.components.AppScaffold
import laboratorio.demo.progetto_mobile_app.components.GreenAppBar

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    //val navController = rememberNavController()

    val currentRoute =
        navController.currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route

    val screensWithTopBar = listOf(
        Routes.Login,
        Routes.Register
    )

    // ==========================================
    // CONFIGURAZIONE DELLA SCHERMATA CORRENTE
    // ==========================================

    val currentScreen = when(currentRoute) {

        //Routes.Home.route -> Routes.Home
        Routes.Home.route -> ScreenConfig(
            title = "",
            showTopBar = false,
            showBottomBar = false
        )

        //Routes.Login.route -> Routes.Login
        //Routes.Login.route -> GreenAppBar()
        Routes.Login.route -> ScreenConfig(
            title = "Login",
            showTopBar = true,
            showBottomBar = false
        )

        //Routes.Register.route -> Routes.Register
        //Routes.Register.route -> GreenAppBar()
        Routes.Register.route -> ScreenConfig(
            title = "Registrazione",
            showTopBar = true,
            showBottomBar = false
        )

        //else -> Routes.Home
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

        else ->
            AppBarConfig(
                backgroundColor = MaterialTheme.colorScheme.primary
            )
    }

//    val topBarColor = when (currentRoute) {
//
//        Routes.Login.route ->
//            colorResource(R.color.green)
//
//        Routes.Register.route ->
//            colorResource(R.color.green)
//
//        else ->
//            MaterialTheme.colorScheme.primary
//    }

//    val screensWithBottomBar = listOf(
//
//    )

    // ==========================================
    // SCAFFOLD
    // ==========================================

    AppScaffold(

        topBarTitle = currentScreen.title,

        showTopBar = currentScreen.showTopBar,

        showBottomBar = currentScreen.showBottomBar,

        //topBarColor = topBarColor,

        showBackButton = currentRoute != Routes.Home.route,

        onBackClick = {

            navController.navigate(Routes.Home.route) {
                popUpTo(Routes.Home.route) {
                    inclusive = false
                }
                launchSingleTop = true
            }

            /*
            if (currentRoute == Routes.Login.route ||
                currentRoute == Routes.Register.route
            ) {
                navController.navigate(Routes.Home.route) {
                    popUpTo(Routes.Home.route) {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            } else {
                navController.popBackStack()
            }*/
        },

        appBarConfig = appBarConfig
//        topBarTitle = when(currentRoute) {
//
//            Routes.Login.route -> "Login"
//
//            Routes.Register.route -> "Registrazione"
//
//            Routes.Home.route -> ""
//
//            //Routes.Home -> "Smart Travel Planner"
//
//            else -> ""
//        },

        //showTopBar = currentRoute in screensWithTopBar

        //showBottomBar = currentRoute in screensWithBottomBar
        /*
        showTopBar = currentRoute != Routes.Home,

        showBottomBar = currentRoute == Routes.Login ||
                        currentRoute == Routes.Register
        */
        //showBackButton = currentRoute != Routes.Home,

//        onBackClick = {
//            navController.popBackStack()
//        }

    ) { innerPadding ->

        // ======================================
        // NAVIGATION
        // ======================================

        //AppNavigation(navController)

        NavHost(
            navController = navController,
            startDestination = Routes.Home.route
        ) {

            composable(Routes.Home.route) {
                HomeScreen(navController)
            }

            composable(Routes.Register.route) {
                RegisterScreen(navController)
            }

            composable(Routes.Login.route) {
                LoginScreen(navController)
            }
        }
    }
}