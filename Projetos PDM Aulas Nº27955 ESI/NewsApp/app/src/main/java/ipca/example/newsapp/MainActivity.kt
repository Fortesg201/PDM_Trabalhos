package ipca.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import ipca.example.newsapp.ui.LoginScreen
import ipca.example.newsapp.ui.ProfileView
import ipca.example.newsapp.ui.articles.ArticleDetailView
import ipca.example.newsapp.ui.articles.ArticlesListView
import ipca.example.newsapp.ui.components.MyBottomBar
import ipca.example.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // está na firebase ou n
        val auth = FirebaseAuth.getInstance()

        setContent {
            //login ou app
            var isLoggedIn by remember { mutableStateOf(auth.currentUser != null) }

            NewsAppTheme {
                if (!isLoggedIn) {
                    // sem login
                    LoginScreen(onLoginSuccess = {
                        isLoggedIn = true
                    })
                } else {
                    // com login
                    val navController = rememberNavController()
                    var navTitle by remember { mutableStateOf("TechCrunch") }
                    var isHomeScreen by remember { mutableStateOf(true) }

                    Scaffold(modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                title = { Text(text = navTitle) },
                                navigationIcon = {
                                    if (!isHomeScreen)
                                        IconButton(onClick = { navController.popBackStack() }) {
                                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                                        }
                                },
                                actions = {
                                    // Botão de logout
                                    Button(onClick = {
                                        auth.signOut()
                                        isLoggedIn = false
                                    }) {
                                        Text("Sair")
                                    }
                                }
                            )
                        },
                        bottomBar = {
                            MyBottomBar(navController = navController)
                        }

                    ) { innerPadding ->
                        NavHost(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            startDestination = "tech_crunch"
                        ) {
                            composable("tech_crunch") {
                                navTitle = "TechCrunch"
                                isHomeScreen = true
                                ArticlesListView(navController = navController, source = "techcrunch")
                            }
                            composable("bloomberg") {
                                navTitle = "Bloomberg"
                                isHomeScreen = true
                                ArticlesListView(navController = navController, source = "bloomberg")
                            }
                            composable("espn") {
                                navTitle = "ESPN"
                                isHomeScreen = true
                                ArticlesListView(navController = navController, source = "espn")
                            }

                            //caminho para a pagina perfil
                            composable("profile") {
                                navTitle = "Perfil & Carrinho"
                                isHomeScreen = true
                                ProfileView()
                            }
                            composable("favorites") {
                                navTitle = "Favoritos"
                                isHomeScreen = true


                                ipca.example.newsapp.ui.favorites.FavoritesView()
                            }


                            composable("product/{id}") { navBackStackEntry ->
                                val id = navBackStackEntry.arguments?.getString("id")
                                isHomeScreen = false
                                ArticleDetailView(id = id, navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}