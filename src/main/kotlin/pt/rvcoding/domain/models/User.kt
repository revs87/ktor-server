package pt.rvcoding.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String,
    val password: String,
    val phoneNumber: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val lastLogin: Long = 0L
)