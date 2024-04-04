package pt.rvcoding.domain.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Appointment(
    val email: String,
    val phoneNumber: String,
    val date: Long,
    val duration: Long,
    val service: CustomerService
) {
    companion object {
        val Default = Appointment(
            "example@example.com",
            "+351 91 1231212",
            1707087378591,
            (2 * 60 * 1000).toLong(),
            CustomerService.DefaultCustomerServices.first()
        )
        val DefaultAsJson = Json.encodeToString(Default)
    }
}