package pt.rvcoding.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Appointment(
    val email: String,
    val phoneNumber: String,
    val date: Long,
    val duration: Long,
    val service: CustomerService
)