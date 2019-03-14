package moe.feng.express.helper.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.get

object HttpUtils {

    val client = HttpClient(Apache) {

    }

    fun resolveFormBody(bodyString: String?): Map<String, String> {
        return bodyString?.split("&")
            ?.filter { it.count { ch -> ch == '=' } == 1 && it.length > 1 }
            ?.map {
                val parts = it.split("=")
                parts[0] to parts[1]
            }
            ?.toMap() ?: emptyMap()
    }

    suspend inline fun <reified T> getAsJsonModel(url: String): T {
        return client.get<String>(url).let { JsonUtils.parse(it, T::class.java) }
    }

}