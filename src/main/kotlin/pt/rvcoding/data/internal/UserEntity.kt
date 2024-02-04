package pt.rvcoding.data.internal

import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    val id: String, // auto generate
    val email: String,
    val password: String,
    val phoneNumber: String,
    val firstName: String = "",
    val lastName: String = "",
    val lastLogin: Long = 0L,
    val privateKey: String = "",
    val publicKey: String = "",
)