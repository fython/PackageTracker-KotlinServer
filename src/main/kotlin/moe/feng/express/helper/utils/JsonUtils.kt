package moe.feng.express.helper.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object JsonUtils {

    const val RECOMMENDED_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

    var GSON: Gson = GsonBuilder().apply {
        setDateFormat(RECOMMENDED_DATE_FORMAT)
    }.create()
        private set

    fun setGsonInstance(newGsonInstance: Gson) {
        GSON = newGsonInstance
    }

    fun <T> parse(json: String, clazz: Class<T>): T {
        return GSON.fromJson(json, clazz)
    }

    inline fun <reified T> parse(json: String): T {
        return JsonUtils.parse(json, T::class.java)
    }

    fun <E> parseList(json: String, elementArrayClass: Class<Array<E>>): List<E> {
        return JsonUtils.parse(json, elementArrayClass).toList()
    }

    inline fun <reified E> parseList(json: String): List<E> {
        return JsonUtils.parseList(json, Array<E>::class.java)
    }

    fun <T> stringify(obj: T): String {
        return GSON.toJson(obj)
    }

}