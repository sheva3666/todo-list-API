package entities

import kotlinx.serialization.Serializable


@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String,
    val password: String
)
