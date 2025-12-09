package ipca.example.newsapp

import com.google.firebase.firestore.Exclude

data class CartItem(
    @get:Exclude var documentId: String = "",
    val id: String = "",
    val title: String = "",
    val imageUrl: String? = null
)