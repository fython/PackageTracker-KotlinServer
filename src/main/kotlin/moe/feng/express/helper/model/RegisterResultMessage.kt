package moe.feng.express.helper.model

import java.util.*

class RegisterResultMessage(
    message: String?,
    code: Int,
    val token: String?,
    val time: Date?
) : SimpleMessage(message, code)