package laboratorio.demo.progetto_mobile_app.components
/*
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import laboratorio.demo.progetto_mobile_app.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(

    showTopBar: Boolean = true,
    showBottomBar: Boolean = true,

    topBarTitle: String = "",
    bottomBarText: String = "",

    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold (
        topBar = {
            if (showTopBar) {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {

                            if (topBarTitle.isNotEmpty()) {

                                Text(
                                    text = topBarTitle,
                                    fontSize = 26.sp
                                )
                            }
                        }
                    },

                    colors = TopAppBarDefaults.topAppBarColors(
                        //containerColor = Color(0xFF1976D2), // Blu
                        containerColor = colorResource(id = R.color.green),
                        titleContentColor = Color.White
                    )
                )
            }
        },

        bottomBar = {

            if (showBottomBar) {

                BottomAppBar (
                    containerColor = colorResource(id = R.color.green)
                )   {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (bottomBarText.isNotEmpty()) {

                                Text(
                                    text = bottomBarText,
                                    color = Color.White,
                                    fontSize = 24.sp
                                )
                            }
                        }
                }
            }

        }
    ) { padding ->
        content(padding)

    }
}


*/
