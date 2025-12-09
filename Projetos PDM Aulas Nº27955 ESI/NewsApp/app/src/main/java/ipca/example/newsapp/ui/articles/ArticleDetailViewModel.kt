package ipca.example.newsapp.ui.articles

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ipca.example.newsapp.models.Article
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

data class ArticleDetailState(
    val article: Article? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class ArticleDetailViewModel : ViewModel() {

    var uiState = mutableStateOf(ArticleDetailState())
        private set

    fun fetchProductDetails(id: String?) {
        if (id == null || id == "null") {
            uiState.value = ArticleDetailState(error = "Produto não encontrado")
            return
        }

        uiState.value = uiState.value.copy(isLoading = true)

        val request = Request.Builder()
            // detalhe de um produto
            .url("https://dummyjson.com/products/${id}")
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful){
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = "Unexpected code $response"
                        )
                        return
                    }

                    val productResult = response.body!!.string()
                    val jsonResult = JSONObject(productResult)


                    val article = Article.fromJson(jsonResult)

                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        article = article
                    )
                }
            }
        })
    }
}