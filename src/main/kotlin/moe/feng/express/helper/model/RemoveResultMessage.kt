package moe.feng.express.helper.model

import com.google.gson.annotations.SerializedName

class RemoveResultMessage(
    message: String?,
    code: Int,
    @SerializedName("remove_count") val removeCount: Int
) : SimpleMessage(message, code)