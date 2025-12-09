package ipca.example.newsapp.models

import org.json.JSONObject
import java.net.URLDecoder
import java.net.URLEncoder

data class Article (
    var id          : Int? = null,
    var price       : Double? = null,
    var author      : String? = null,
    var title       : String? = null,
    var description : String? = null,
    var url         : String? = null,
    var urlToImage  : String? = null,
    var publishedAt : String? = null,
){
    companion object{
        fun fromJson(json : JSONObject) : Article {
            return Article(
                id = json.optInt("id"),
                price = json.optDouble("price"),
                author = json.optString("brand"),
                title = json.optString("title"),
                description = json.optString("description"),
                url = json.optString("thumbnail"),
                urlToImage = json.optString("thumbnail"),
                publishedAt = null
            )
        }
    }
}


fun String.encodeUrl() : String {
    return URLEncoder.encode(this, "UTF-8")
}

fun String.decodeUrl() : String {
    return URLDecoder.decode(this, "UTF-8")
}