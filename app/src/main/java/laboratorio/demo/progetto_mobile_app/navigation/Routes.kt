package laboratorio.demo.progetto_mobile_app.navigation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import laboratorio.demo.progetto_mobile_app.R

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

    object Home : Routes(
        route = "home",
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
}
//object Routes {
//    const val Home = "home"
//    const val Register = "register"
//    const val Login = "login"
//}