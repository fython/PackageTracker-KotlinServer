package moe.feng.express.helper.model

class PackageResultMessage(
    message: String?,
    code: Int,
    val data: Package
) : SimpleMessage(message, code)