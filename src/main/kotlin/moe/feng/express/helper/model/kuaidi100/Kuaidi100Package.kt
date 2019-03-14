package moe.feng.express.helper.model.kuaidi100

import com.google.gson.annotations.SerializedName
import moe.feng.express.helper.model.Package
import moe.feng.express.helper.model.Status
import java.text.SimpleDateFormat

data class Kuaidi100Package(
    val message: String,
    @SerializedName("nu") val number: String,
    @SerializedName("ischeck") val isChecked: String,
    val condition: String,
    @SerializedName("com") val company: String,
    val status: String,
    val state: String,
    val data: List<StatusItem>
) {

    companion object {

        private val KUAIDI100_TIME_FORMAT = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

    }

    fun toPackage(): Package {
        return Package(
            id = number,
            message = message,
            company = company,
            statusCode = status,
            state = state,
            isChecked = isChecked == "1",
            data = data.map {
                Status(
                    content = it.context,
                    location = it.location,
                    time = KUAIDI100_TIME_FORMAT.parse(it.time)
                )
            }
        )
    }

    data class StatusItem(
        val time: String,
        val context: String,
        val location: String
    )

}