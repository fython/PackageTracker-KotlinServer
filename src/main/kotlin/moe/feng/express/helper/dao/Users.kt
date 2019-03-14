package moe.feng.express.helper.dao

import moe.feng.express.helper.model.User
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime

object Users : Table() {

    val deviceToken = text("token").primaryKey()
    val registerTime = datetime("register_time").clientDefault { DateTime.now() }

    fun all() = selectAll()
        .map(::User)

    fun get(deviceToken: String) = select { Users.deviceToken eq deviceToken }
        .map(::User)
        .firstOrNull()

    fun getUsersWhoSubscribedId(packId: String) = select {
        Users.deviceToken inList UserPackages.getByPackage(packId).map { it.userToken }
    }
        .map(::User)

    fun insert(user: User) = insert {
        it[Users.deviceToken] = user.deviceToken
        it[Users.registerTime] = DateTime(user.registerTime)
    }

    fun update(user: User) = update({ Users.deviceToken eq user.deviceToken }) {
        it[Users.registerTime] = DateTime(user.registerTime)
    }

    fun delete(user: User): Int {
        UserPackages.deleteByUser(user)
        return deleteWhere { Users.deviceToken eq user.deviceToken }
    }

}