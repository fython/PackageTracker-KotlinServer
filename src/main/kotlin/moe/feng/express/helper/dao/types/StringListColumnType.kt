package moe.feng.express.helper.dao.types

object StringListColumnType : JsonListColumnType<String>(Array<String>::class.java)