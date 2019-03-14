package moe.feng.express.helper.model.kuaidi100

import com.google.gson.annotations.SerializedName

data class Kuaidi100GuessCompanyResult(
    @SerializedName("comCode") val company: String,
    @SerializedName("num") val number: String,
    @SerializedName("auto") val list: List<Item>
) {

    data class Item(
        @SerializedName("comCode") val company: String,
        @SerializedName("noCount") val numberCount: Int,
        @SerializedName("noPre") val numberPrefix: String,
        val startTime: String,
        val id: String
    )

}