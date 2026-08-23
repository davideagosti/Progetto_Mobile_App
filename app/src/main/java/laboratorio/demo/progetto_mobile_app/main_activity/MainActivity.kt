package laboratorio.demo.progetto_mobile_app.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

import laboratorio.demo.progetto_mobile_app.navigation.AppNavigation
import laboratorio.demo.progetto_mobile_app.screens.splash.SplashScreen
import laboratorio.demo.progetto_mobile_app.ui.theme.Progetto_Mobile_AppTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            Progetto_Mobile_AppTheme {

                val navController = rememberNavController()

                //AppNavigation(navController)

                var showSplash by rememberSaveable {
                    mutableStateOf(true)
                }

                // Durata della Splash Activity di 3s
                LaunchedEffect(Unit) {

                    delay(3000)

                    showSplash = false
                }


                if(showSplash) {

                    SplashScreen()

                } else {

                    AppNavigation(navController)

                }
            }
        }
    }
}
