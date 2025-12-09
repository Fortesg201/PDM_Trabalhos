package ipca.example.newsapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductLocal(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val price: Double?,
    val imageUrl: String?
)

//define as colunas da minha tabela