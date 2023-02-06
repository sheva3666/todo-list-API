package entities
import kotlinx.serialization.Serializable


@Serializable
data class ToDoDraft(
    val title: String,
    val done: Boolean
)
