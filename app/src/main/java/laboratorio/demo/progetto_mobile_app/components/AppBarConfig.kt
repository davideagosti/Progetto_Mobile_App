package laboratorio.demo.progetto_mobile_app.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import laboratorio.demo.progetto_mobile_app.R

data class AppBarConfig(

    val backgroundColor: Color,
    val titleColor: Color = Color.White,
    val iconColor: Color = Color.White

)

@Composable
fun GreenAppBar(): AppBarConfig {
    return AppBarConfig(
        backgroundColor = colorResource(R.color.green)
    )
}

@Composable
fun BlueAppBar(): AppBarConfig {
    return AppBarConfig(
        backgroundColor = Color.Blue
    )
}