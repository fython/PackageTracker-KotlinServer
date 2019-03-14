package moe.feng.express.helper.dao

import moe.feng.express.helper.dao.types.objectList
import moe.feng.express.helper.model.Package
import moe.feng.express.helper.model.Status
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime

object Packages : Table() {

    val id = text("id").primaryKey()
    val message = text("message").nullable()
    val company = text("company").nullable()
    val statusCode = varchar("status_code", 5).nullable()
    val state = varchar("state", 5).nullable()
    val isChecked = bool("is_checked").default(false)
    val data = objectList("data", Array<Status>::class.java).clientDefault { emptyList() }
    val updateTime = datetime("update_time").clientDefault { DateTime.now() }

    fun all() = selectAll()
        .map(::Package)

    fun get(id: String) = select { Packages.id eq id }
        .map(::Package)
        .firstOrNull()

    fun insert(pack: Package) = insert {
        it[Packages.id] = pack.id
        it[Packages.message] = pack.message
        it[Packages.company] = pack.company
        it[Packages.statusCode] = pack.statusCode
        it[Packages.state] = pack.state
        it[Packages.isChecked] = pack.isChecked
        it[Packages.data] = pack.data
        it[Packages.updateTime] = DateTime(pack.updateTime)
    }

    fun update(pack: Package) = update({ Packages.id eq pack.id }) {
        it[Packages.message] = pack.message
        it[Packages.company] = pack.company
        it[Packages.statusCode] = pack.statusCode
        it[Packages.state] = pack.state
        it[Packages.isChecked] = pack.isChecked
        it[Packages.data] = pack.data
        it[Packages.updateTime] = DateTime(pack.updateTime)
    }

    fun delete(pack: Package): Int {
        UserPackages.deleteByPackage(pack)
        return deleteWhere { Packages.id eq pack.id }
    }

}