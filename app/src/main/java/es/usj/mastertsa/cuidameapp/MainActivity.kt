package es.usj.mastertsa.cuidameapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import es.usj.mastertsa.cuidameapp.ui.auth.AuthViewModel
import es.usj.mastertsa.cuidameapp.ui.navigation.NavigationBottomBar
import es.usj.mastertsa.cuidameapp.ui.navigation.NavigationHelper
import es.usj.mastertsa.cuidameapp.ui.theme.CuidaMeAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CuidaMeAppTheme {
                val navController = rememberNavController()
                val snackBarHostState = remember { SnackbarHostState() }
                val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.factory(
                    LocalContext.current))

                Scaffold(
                    snackbarHost = { SnackbarHost(snackBarHostState) },

                    bottomBar = {
                        if (authViewModel.user != null){
                            NavigationBottomBar(navController, onClickRoute = { route ->
                                navController.navigate(route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        inclusive = false
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            })
                        }
                    }
                ) { paddingValues ->
                    NavigationHelper(navController = navController, authViewModel, modifier = Modifier.padding(paddingValues))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CuidaMeAppTheme {
        Greeting("Android")
    }
}
