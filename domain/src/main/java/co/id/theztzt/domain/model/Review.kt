package co.id.theztzt.domain.model

data class Review(
    val id: String?,
    val username: String?,
    val avatarPath: String?,
    val content: String?,
    val rating: Float?,
    val createdAt: String,
)