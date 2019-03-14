package moe.feng.express.helper

import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import moe.feng.express.helper.dao.Packages
import moe.feng.express.helper.dao.UserPackages
import moe.feng.express.helper.dao.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

object Config {

    object DB {
        lateinit var jdbcUrl: String
    }

    object BasicAuth {
        var username: String? = null
        var password: String? = null
    }

    lateinit var database: Database

    @KtorExperimentalAPI
    fun init(config: ApplicationConfig) {
        config.config("myserver").let { myserver ->
            DB.jdbcUrl = myserver.property("jdbcUrl").getString()
            myserver.config("basicAuth").let { basicAuth ->
                BasicAuth.username = basicAuth.propertyOrNull("username")?.getString()
                BasicAuth.password = basicAuth.propertyOrNull("password")?.getString()
            }
        }

        if (!DB.jdbcUrl.startsWith("jdbc:sqlite")) {
            System.err.println("Warning: Current version of Push Server may not supports this type of database.")
        }

        database = Database.connect(DB.jdbcUrl, driver = "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
        transaction {
            SchemaUtils.create(Users, Packages, UserPackages)
        }
    }

}