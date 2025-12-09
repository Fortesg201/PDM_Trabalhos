package ipca.example.newsapp.ui.articles

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ipca.example.newsapp.CartItem
import ipca.example.newsapp.database.ProductLocal
import ipca.example.newsapp.ui.favorites.FavoritesViewModel


@Composable
fun ArticleDetailView(
    modifier: Modifier = Modifier,
    id: String?,
    navController: NavController
) {
    val viewModel: ArticleDetailViewModel = viewModel()
    val uiState by viewModel.uiState
    val context = LocalContext.current

    // ViewModel para os Favoritos (Room)
    val favoritesVM: FavoritesViewModel = viewModel()

    LaunchedEffect(id) {
        viewModel.fetchProductDetails(id)
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.error != null) {
            Text(
                text = uiState.error!!,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        } else if (uiState.article != null) {

            val article = uiState.article!!

            // Conteúdo da página
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título
                Text(
                    text = article.title ?: "Sem Título",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Imagem
                AsyncImage(
                    model = article.urlToImage,
                    contentDescription = article.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)
                        .padding(vertical = 16.dp),
                    contentScale = ContentScale.Crop
                )

                // Preço
                Text(
                    text = "Preço: ${article.price} €",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Autor/Marca
                Text(
                    text = "Marca: ${article.author ?: "Desconhecida"}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Descrição
                Text(
                    text = article.description ?: "Sem descrição.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 100.dp)
                )
            }


            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // 1. Botão FAVORITOS (Room) - Pequeno
                SmallFloatingActionButton(
                    onClick = {
                        val productToSave = ProductLocal(
                            id = id ?: "unknown",
                            title = article.title ?: "Sem Título",
                            description = article.description,
                            price = article.price?.toString()?.toDoubleOrNull() ?: 0.0,
                            imageUrl = article.urlToImage
                        )
                        favoritesVM.addFavorite(productToSave)

                        Toast.makeText(context, "Guardado nos Favoritos!", Toast.LENGTH_SHORT).show()
                    },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorito")
                }

                // 2. Botão CARRINHO (Firebase) - Grande
                FloatingActionButton(
                    onClick = {
                        val user = FirebaseAuth.getInstance().currentUser
                        if (user != null) {
                            val db = FirebaseFirestore.getInstance()

                            // criar objeto e guardar
                            val cartItem = CartItem(
                                id = id ?: "unknown",
                                title = article.title ?: "Sem nome",
                                imageUrl = article.urlToImage
                            )

                            // salva no firestore
                            db.collection("users").document(user.uid)
                                .collection("cart")
                                .add(cartItem)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Adicionado ao Carrinho!", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Erro ao salvar.", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(context, "Faz login primeiro!", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Cart")
                }
            }
        }
    }
}