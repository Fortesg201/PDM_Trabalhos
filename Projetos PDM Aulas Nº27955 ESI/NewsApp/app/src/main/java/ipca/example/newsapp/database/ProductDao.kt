package ipca.example.newsapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAll(): Flow<List<ProductLocal>>

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): ProductLocal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductLocal)

    // Remove
    @Delete
    suspend fun delete(product: ProductLocal)
}

//define os "comandos" que posso dar ao banco

