package entities

import kotlinx.serialization.Serializable


@Serializable
data class ToDo(
    val id: Int,
    val userId: String,
    var title: String,
    var status: String
)

