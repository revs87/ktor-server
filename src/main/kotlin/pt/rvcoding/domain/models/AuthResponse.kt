package pt.rvcoding.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val code: String,
    val type: String,
    val message: String
)