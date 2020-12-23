package pojo

data class Chat(
    val id: Int,
    val message: String,
    val unread: Boolean,
    val rating: Int // Out of 5
)