package pt.rvcoding.domain.ppk

import kotlinx.serialization.Serializable

@Serializable
data class PPKResponse(
    val privateKey: String,
    val publicKey: String
)