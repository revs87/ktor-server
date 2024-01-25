package pt.rvcoding.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    val id: String, // auto generate
    val email: String,
    val password: String,
    val firstName: String = "",
    val lastName: String = "",
    val lastLogin: Long = 0L,
    val privateKey: String = "",
    val publicKey: String = "",
)