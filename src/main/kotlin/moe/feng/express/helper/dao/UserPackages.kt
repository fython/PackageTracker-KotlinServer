package moe.feng.express.helper.dao

import moe.feng.express.helper.model.Package
import moe.feng.express.helper.model.User
import moe.feng.express.helper.model.UserPackage
import org.jetbrains.exposed.sql.*

object UserPackages : Table() {

    val userToken = text("token") references Users.deviceToken
    val id = text("id")

    fun all() = selectAll()
        .map(::UserPackage)

    fun get(deviceToken: String, id: String) = select {
        (UserPackages.userToken eq deviceToken) and (UserPackages.id eq id)
    }
        .firstOrNull()
        ?.let(::UserPackage)

    fun getByUser(deviceToken: String) = select { UserPackages.userToken eq deviceToken }
        .map(::UserPackage)

    fun getByUser(user: User) = UserPackages.getByUser(user.deviceToken)

    fun getByPackage(id: String) = select { UserPackages.id eq id }
        .map(::UserPackage)

    fun getByPackage(pack: Package) = UserPackages.getByPackage(pack.id)

    fun insert(obj: UserPackage) = insert {
        it[UserPackages.userToken] = obj.userToken
        it[UserPackages.id] = obj.id
    }

    fun insertAll(vararg items: UserPackage) = items.map { item ->
        insert {
            it[UserPackages.userToken] = item.userToken
            it[UserPackages.id] = item.id
        }
    }

    fun delete(obj: UserPackage) = deleteWhere {
        (UserPackages.userToken eq obj.userToken) and
                ((UserPackages.id eq obj.id) or (UserPackages.id like "${obj.id}+%"))
    }

    fun deleteByUser(deviceToken: String) = deleteWhere {
        UserPackages.userToken eq deviceToken
    }

    fun deleteByUser(user: User) = UserPackages.deleteByUser(user.deviceToken)

    fun deleteByPackage(id: String) = deleteWhere {
        UserPackages.id eq id
    }

    fun deleteByPackage(pack: Package) = UserPackages.deleteByPackage(pack.id)

}