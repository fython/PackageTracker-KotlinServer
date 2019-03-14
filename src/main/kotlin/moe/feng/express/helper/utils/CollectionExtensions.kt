package moe.feng.express.helper.utils

fun Map<String, String>.destructureGet(vararg keys: String): Array<String?> {
    return keys.map { key -> this[key] }.toTypedArray()
}