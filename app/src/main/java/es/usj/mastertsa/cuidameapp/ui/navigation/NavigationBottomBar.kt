package es.usj.mastertsa.cuidameapp.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

@SuppressLint("RestrictedApi")
@Composable
fun NavigationBottomBar(navController: NavController, onClickRoute:(currentRoute: Any)-> Unit){
    BottomAppBar(containerColor = Color.White, contentColor = Color.White) {
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
                colors = NavigationBarItemColors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    selectedIndicatorColor = Color.White,
                    unselectedIconColor = Color.DarkGray,
                    unselectedTextColor = Color.DarkGray,
                    disabledIconColor = Color.LightGray,
                    disabledTextColor = Color.LightGray
                ),
                alwaysShowLabel = true
            )
        }

    }
}