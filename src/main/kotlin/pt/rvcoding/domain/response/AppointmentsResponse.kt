package pt.rvcoding.domain.response

import kotlinx.serialization.Serializable
import pt.rvcoding.domain.models.Appointment

@Serializable
data class AppointmentsResponse(
    val appointments: List<Appointment>
)