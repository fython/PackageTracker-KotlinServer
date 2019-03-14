package moe.feng.express.helper.api

import moe.feng.express.helper.model.Package
import moe.feng.express.helper.model.kuaidi100.Kuaidi100GuessCompanyResult
import moe.feng.express.helper.model.kuaidi100.Kuaidi100Package
import moe.feng.express.helper.utils.HttpUtils

object Kuaidi100Api : PackageInfoProvider, PackageCompanyProvider {

    const val DETECT_COMPANY_URL = "https://www.kuaidi100.com/autonumber/autoComNum?text=%s"
    const val QUERY_PACKAGE_URL = "https://www.kuaidi100.com/query?type=%s&postid=%s"

    override suspend fun query(id: String, company: String?): Package {
        val targetCompany = company ?: guessCompany(id).first()

        return HttpUtils.getAsJsonModel<Kuaidi100Package>(QUERY_PACKAGE_URL.format(targetCompany, id)).toPackage()
    }

    override suspend fun guessCompany(id: String): List<String> {
        return HttpUtils.getAsJsonModel<Kuaidi100GuessCompanyResult>(DETECT_COMPANY_URL.format(id))
            .list.map { it.company }
    }

}