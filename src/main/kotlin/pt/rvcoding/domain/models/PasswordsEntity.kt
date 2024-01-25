package pt.rvcoding.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class PasswordsEntity(
    val id: String, // auto generate
    val email: String,
    val previousPasswords: List<String>
)