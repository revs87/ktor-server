package pt.rvcoding.domain.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val code: Int,
    val type: String,
    val message: String
)