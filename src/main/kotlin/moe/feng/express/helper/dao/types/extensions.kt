package moe.feng.express.helper.dao.types

import org.jetbrains.exposed.sql.Table

fun Table.stringList(name: String) =
    registerColumn<List<String>>(name, StringListColumnType)

fun <E> Table.objectList(name: String, elementArrayClass: Class<Array<E>>) =
    registerColumn<List<E>>(name, JsonListColumnType(elementArrayClass))

inline fun <reified E> Table.objectList(name: String) =
    objectList(name, Array<E>::class.java)