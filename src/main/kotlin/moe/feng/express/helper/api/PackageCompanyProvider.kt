package moe.feng.express.helper.api

interface PackageCompanyProvider {

    suspend fun guessCompany(id: String): List<String>

}