package ipca.example.newsapp.ui.articles

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ipca.example.newsapp.models.Article
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

data class ArticlesListState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ArticlesListViewModel : ViewModel() {

    var uiState = mutableStateOf(ArticlesListState())
        private set


    fun fetchArticles(source : String) {
        uiState.value = uiState.value.copy(isLoading = true)

        val request = Request.Builder()
            .url("https://dummyjson.com/products")
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

                    val newsResult = response.body!!.string()
                    val jsonResult = JSONObject(newsResult)
                    val articlesList = arrayListOf<Article>()


                    val articlesJson = jsonResult.getJSONArray("products")
                    for (i in 0 until articlesJson.length()) {
                        val articleJson = articlesJson.getJSONObject(i)
                        val article = Article.fromJson(articleJson)
                        articlesList.add(article)
                    }

                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        articles = articlesList
                    )
                }
            }
        })
    }
}
