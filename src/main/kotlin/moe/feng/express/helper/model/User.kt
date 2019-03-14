package moe.feng.express.helper.model

import moe.feng.express.helper.dao.Users
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

data class User(
    val deviceToken: String,
    var registerTime: Date
) {

    constructor(row: ResultRow) : this(
        deviceToken = row[Users.deviceToken],
        registerTime = row[Users.registerTime].toDate()
    )

}