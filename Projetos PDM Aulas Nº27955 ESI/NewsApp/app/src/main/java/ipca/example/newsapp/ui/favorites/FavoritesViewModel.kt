package ipca.example.newsapp.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ipca.example.newsapp.database.AppDatabase
import ipca.example.newsapp.database.ProductLocal
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val dao = db.productDao()


    val favorites = dao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addFavorite(product: ProductLocal) {
        viewModelScope.launch {
            dao.insert(product)
        }
    }

    fun removeFavorite(product: ProductLocal) {
        viewModelScope.launch {
            dao.delete(product)
        }
    }
}

//vais buscar os dados ao room e entregar no view

