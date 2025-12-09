package ipca.example.newsapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ipca.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun MyBottomBar(
    navController: NavController
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar {
        // Item 1: TechCrunch
        NavigationBarItem(
            selected = currentRoute == "tech_crunch",
            icon = {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "TechCrunch"
                )
            },
            label = {
                Text("TechCrunch")
            },
            onClick = {
                navController.navigate("tech_crunch")
            }
        )

        // Item 2: Bloomberg
        NavigationBarItem(
            selected = currentRoute == "bloomberg",
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Bloomberg"
                )
            },
            label = {
                Text("Bloomberg")
            },
            onClick = {
                navController.navigate("bloomberg")
            }
        )

        // Item 3: ESPN
        NavigationBarItem(
            selected = currentRoute == "espn",
            icon = {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "ESPN"
                )
            },
            label = {
                Text("ESPN")
            },
            onClick = {
                navController.navigate("espn")
            }
        )

        // Item 4: Favoritos
        NavigationBarItem(
            selected = currentRoute == "favorites",
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favoritos"
                )
            },
            label = {
                Text("Favoritos")
            },
            onClick = {
                navController.navigate("favorites")
            }
        )

        // Item 5: Perfil
        NavigationBarItem(
            selected = currentRoute == "profile",
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil"
                )
            },
            label = {
                Text("Perfil")
            },
            onClick = {
                navController.navigate("profile")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyBottomBarPreview(){
    NewsAppTheme {
        MyBottomBar(navController = rememberNavController())
    }
}