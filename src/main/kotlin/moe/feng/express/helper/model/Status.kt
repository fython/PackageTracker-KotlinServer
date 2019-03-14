package moe.feng.express.helper.model

import java.util.*

data class Status(
    val content: String,
    val location: String? = null,
    val time: Date = Calendar.getInstance().time
)