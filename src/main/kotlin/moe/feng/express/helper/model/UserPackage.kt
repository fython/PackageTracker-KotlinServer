package moe.feng.express.helper.model

import moe.feng.express.helper.dao.UserPackages
import org.jetbrains.exposed.sql.ResultRow

data class UserPackage(
    val userToken: String,
    val id: String
) {

    constructor(row: ResultRow) : this(
        userToken = row[UserPackages.userToken],
        id = row[UserPackages.id]
    )

}