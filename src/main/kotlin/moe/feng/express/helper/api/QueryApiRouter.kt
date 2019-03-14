package moe.feng.express.helper.api

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import moe.feng.express.helper.Constants.MESSAGE_WRONG_PARAMETERS
import moe.feng.express.helper.model.CompanyDetectResultMessage
import moe.feng.express.helper.model.PackageResultMessage
import moe.feng.express.helper.model.SimpleMessage
import java.lang.Exception

object QueryApiRouter {

    var currentPackageInfoProvider: PackageInfoProvider = Kuaidi100Api
    var currentPackageCompanyProvider: PackageCompanyProvider = Kuaidi100Api

    fun install(route: Route) = with(route) {
        get("/detect_company") {
            val id = call.request.queryParameters["id"]

            call.respond(detectCompany(id))
        }

        get("/query") {
            val id = call.request.queryParameters["id"]
            val company = call.request.queryParameters["com"]

            call.respond(queryPackage(id, company))
        }
    }

    suspend fun detectCompany(id: String?): SimpleMessage {
        if (id == null) {
            return MESSAGE_WRONG_PARAMETERS
        }

        try {
            return CompanyDetectResultMessage(
                message = "Done.",
                code = 0,
                company = currentPackageCompanyProvider.guessCompany(id).first()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return SimpleMessage(e.message, -1)
        }
    }

    suspend fun queryPackage(id: String?, company: String?): SimpleMessage {
        if (id == null) {
            return MESSAGE_WRONG_PARAMETERS
        }

        try {
            val targetCompany = company ?: currentPackageCompanyProvider.guessCompany(id).first()

            return PackageResultMessage(
                message = "Done.",
                code = 0,
                data = currentPackageInfoProvider.query(id, targetCompany)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return SimpleMessage(e.message, -1)
        }
    }

}