package moe.feng.express.helper.api

import io.ktor.application.call
import io.ktor.request.receiveOrNull
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import moe.feng.express.helper.Constants.MESSAGE_MISSING_TOKEN_PARAMS
import moe.feng.express.helper.Constants.MESSAGE_NO_REGISTERED
import moe.feng.express.helper.Constants.MESSAGE_SUCCEED
import moe.feng.express.helper.Constants.MESSAGE_WRONG_PARAMETERS
import moe.feng.express.helper.dao.UserPackages
import moe.feng.express.helper.dao.Users
import moe.feng.express.helper.model.*
import moe.feng.express.helper.utils.JsonUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object SubscribeApiRouter {

    fun install(route: Route) = with(route) {
        post("register") {
            val params = call.receiveParameters()
            val userToken = params["token"]

            call.respond(registerDevice(userToken))
        }
        post("add") {
            val params = call.receiveParameters()
            val userToken = params["token"]
            val id = params["id"]
            val company = params["com"]

            call.respond(add(userToken, id, company))
        }
        get("list") {
            val userToken = call.request.queryParameters["token"]

            call.respond(list(userToken))
        }
        post("sync") {
            val userToken = call.request.queryParameters["token"]
            val array = call.receiveOrNull<String>()?.let { JsonUtils.parseList<String>(it) }

            call.respond(sync(userToken, array))
        }
        post("remove") {
            val params = call.receiveParameters()
            val userToken = params["token"]
            val itemId = params["id"]

            call.respond(remove(userToken, itemId))
        }
    }

    suspend fun registerDevice(userToken: String?): SimpleMessage {
        if (userToken == null) {
            return MESSAGE_MISSING_TOKEN_PARAMS
        }

        try {
            return transaction {
                val newRegisterTime = Calendar.getInstance().time
                val user = Users.get(userToken)
                if (user != null) {
                    user.registerTime = newRegisterTime
                    Users.update(user)
                } else {
                    Users.insert(User(userToken, newRegisterTime))
                }
                RegisterResultMessage("Succeed.", 0, userToken, newRegisterTime)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return SimpleMessage("Failed to register user: " + e.message, -1)
        }
    }

    suspend fun add(userToken: String?, id: String?, company: String?): SimpleMessage {
        if (userToken == null || id == null) {
            return MESSAGE_WRONG_PARAMETERS
        }

        try {
            return transaction {
                val targetId = company?.let { "$id+$it" } ?: id

                when {
                    Users.get(userToken) == null ->
                        MESSAGE_NO_REGISTERED
                    UserPackages.get(userToken, targetId) != null ->
                        SimpleMessage("The item has been added already.", 1)
                    else -> {
                        UserPackages.insert(UserPackage(userToken, targetId))
                        MESSAGE_SUCCEED
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return SimpleMessage("Failed to add package: " + e.message, -1)
        }
    }

    suspend fun list(userToken: String?): Any {
        if (userToken == null) {
            return MESSAGE_MISSING_TOKEN_PARAMS
        }

        try {
            return transaction {
                if (Users.get(userToken) == null) {
                    MESSAGE_NO_REGISTERED
                } else {
                    UserPackages.getByUser(userToken).map { it.id }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return SimpleMessage("Failed to list subscribed packages: " + e.message, -1)
        }
    }

    suspend fun sync(userToken: String?, array: List<String>?): SimpleMessage {
        if (userToken == null) {
            return MESSAGE_MISSING_TOKEN_PARAMS
        }
        if (array == null) {
            return SimpleMessage("Please post a json array.", -3)
        }

        try {
            return transaction {
                if (Users.get(userToken) == null) {
                    MESSAGE_NO_REGISTERED
                } else {
                    UserPackages.deleteByUser(userToken)
                    UserPackages.insertAll(*array.map { UserPackage(userToken, it) }.toTypedArray())
                    SimpleMessage("Synced.", 0)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return SimpleMessage(e.message, -1)
        }
    }

    suspend fun remove(userToken: String?, itemId: String?): SimpleMessage {
        if (userToken == null || itemId == null) {
            return MESSAGE_WRONG_PARAMETERS
        }

        try {
            return transaction {
                if (Users.get(userToken) == null) {
                    MESSAGE_NO_REGISTERED
                } else {
                    RemoveResultMessage(
                        message = "Succeed.",
                        code = 0,
                        removeCount = UserPackages.delete(UserPackage(userToken, itemId))
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return SimpleMessage(e.message, -1)
        }
    }

}