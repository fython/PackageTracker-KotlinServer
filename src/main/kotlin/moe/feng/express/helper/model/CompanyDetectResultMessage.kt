package moe.feng.express.helper.model

class CompanyDetectResultMessage(
    message: String?,
    code: Int,
    val company: String
) : SimpleMessage(message, code)