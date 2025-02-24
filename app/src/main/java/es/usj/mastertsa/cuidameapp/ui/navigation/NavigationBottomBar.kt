package es.usj.mastertsa.cuidameapp.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

@SuppressLint("RestrictedApi")
@Composable
fun NavigationBottomBar(navController: NavController, onClickRoute:(currentRoute: Any)-> Unit){
    BottomAppBar {
        val navBackState by navController.currentBackStackEntryAsState()
        val currentDestination = navBackState?.destination

        Screens.navRoutes.forEach { route ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any {
                    it.hasRoute(route.route::class)
                } == true,
                onClick = { onClickRoute(route.route) },
                icon = {
                    Icon(
                        imageVector = route.icon,
                        contentDescription = "Go ${route.name} screen"
                    )
                },
                label = {
                    Text(text = route.name)
                },
                alwaysShowLabel = true
            )
        }

    }
}