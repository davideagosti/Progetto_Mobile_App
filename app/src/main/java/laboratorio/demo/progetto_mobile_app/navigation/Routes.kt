package laboratorio.demo.progetto_mobile_app.navigation

sealed class Routes(

    val route: String,
    val title: String,
    val showTopBar: Boolean,
    val showBottomBar: Boolean
    //val topBarColor: Color

) {
    object Splash : Routes(
        route = "splash",
        title = "Smart Travel Planner",
        showTopBar = true,
        showBottomBar = true,
    )

//    object Home : Routes(
//        route = "home",
//        title = "",
//        showTopBar = false,
//        showBottomBar = false
//    )

    object Home : Routes(
        route = "home" +
                "?placeId={placeId}" +
                "&name={name}" +
                "&address={address}" +
                "&latitude={latitude}" +
                "&longitude={longitude}",
        title = "",
        showTopBar = false,
        showBottomBar = false
    )

    object Login : Routes(
        route = "login",
        title = "Login",
        showTopBar = true,
        showBottomBar = false
    )

    object Register : Routes(
        route = "register",
        title = "Registrazione",
        showTopBar = true,
        showBottomBar = false
    )

    object EditAccount : Routes(
        route = "edit_account",
        title = "Modifica account",
        showTopBar = true,
        showBottomBar = false
    )

    object Favorites : Routes(
        route = "favorites",
        title = "Gestisci preferiti",
        showTopBar = true,
        showBottomBar = false
    )
}
