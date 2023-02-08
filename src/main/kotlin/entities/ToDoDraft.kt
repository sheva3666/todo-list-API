package entities
import kotlinx.serialization.Serializable


@Serializable
data class ToDoDraft(
    val userId: String,
    val title: String,
    val status: String
)
