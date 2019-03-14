package moe.feng.express.helper.dao.types

import moe.feng.express.helper.utils.JsonUtils
import org.jetbrains.exposed.sql.TextColumnType

open class JsonListColumnType<E>(private val elementArrayClass: Class<Array<E>>) : TextColumnType() {

    override fun valueFromDB(value: Any): Any {
        return JsonUtils.parseList(super.valueFromDB(value).toString(), elementArrayClass)
    }

    override fun notNullValueToDB(value: Any): Any {
        return JsonUtils.stringify(value)
    }

}