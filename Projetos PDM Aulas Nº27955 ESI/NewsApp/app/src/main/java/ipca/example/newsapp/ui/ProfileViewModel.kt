package ipca.example.newsapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ipca.example.newsapp.CartItem

class ProfileViewModel : ViewModel() {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var cartItems by mutableStateOf<List<CartItem>>(emptyList())

    init {
        getCartItems()
    }

    fun getCartItems() {
        val user = auth.currentUser
        if (user != null) {
            db.collection("users").document(user.uid).collection("cart")
                .addSnapshotListener { result, e ->
                    if (e != null) return@addSnapshotListener

                    if (result != null) {

                        val items = result.documents.mapNotNull { doc ->
                            val item = doc.toObject(CartItem::class.java)
                            item?.documentId = doc.id
                            item
                        }
                        cartItems = items
                    }
                }
        }
    }

    fun removeItem(item: CartItem) {
        val user = auth.currentUser
        if (user != null && item.documentId.isNotEmpty()) {
            db.collection("users").document(user.uid)
                .collection("cart")
                .document(item.documentId)
                .delete()
        }
    }
}