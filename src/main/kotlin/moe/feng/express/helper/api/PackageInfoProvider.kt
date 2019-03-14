package moe.feng.express.helper.api

import moe.feng.express.helper.model.Package

interface PackageInfoProvider {

    suspend fun query(id: String, company: String? = null): Package

}