package moe.feng.express.helper.model

import moe.feng.express.helper.dao.Packages
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

data class Package(
    val id: String,
    val message: String?,
    val company: String?,
    val statusCode: String?,
    val state: String?,
    val isChecked: Boolean = false,
    val data: List<Status> = emptyList(),
    val updateTime: Date = Calendar.getInstance().time
) {

    constructor(row: ResultRow) : this(
        id = row[Packages.id],
        message = row[Packages.message],
        company = row[Packages.company],
        statusCode = row[Packages.statusCode],
        state = row[Packages.state],
        isChecked = row[Packages.isChecked],
        data = row[Packages.data],
        updateTime = row[Packages.updateTime].toDate()
    )

}