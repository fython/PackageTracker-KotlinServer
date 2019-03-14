package moe.feng.express.helper

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.basic
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import moe.feng.express.helper.api.QueryApiRouter
import moe.feng.express.helper.api.SubscribeApiRouter
import moe.feng.express.helper.utils.JsonUtils
import org.slf4j.event.Level
import java.io.File

@KtorExperimentalAPI
fun Application.main() {
    println("Package Tracker Push Server")

    environment.config
    Config.init(environment.config)

    // Install features
    install(DefaultHeaders)
    install(CallLogging)

    if (!Config.BasicAuth.username.isNullOrEmpty()) {
        install(Authentication) {
            basic {
                realm = "API Auth"
                validate { credentials ->
                    if (credentials.name == Config.BasicAuth.username &&
                        credentials.password == Config.BasicAuth.password) {
                        UserIdPrincipal(credentials.name)
                    } else {
                        null
                    }
                }
            }
        }
    }

    install(ContentNegotiation) {
        gson {
            setDateFormat(JsonUtils.RECOMMENDED_DATE_FORMAT)
        }
    }

    // Configure routing
    routing {
        QueryApiRouter.install(this)
        route("/subscribe") {
            SubscribeApiRouter.install(this)
        }
    }
}