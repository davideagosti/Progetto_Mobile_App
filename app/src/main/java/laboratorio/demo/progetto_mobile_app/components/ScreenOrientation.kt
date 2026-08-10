package laboratorio.demo.progetto_mobile_app.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun isLandscape(): Boolean {
    return LocalConfiguration.current.orientation ==
            Configuration.ORIENTATION_LANDSCAPE
}