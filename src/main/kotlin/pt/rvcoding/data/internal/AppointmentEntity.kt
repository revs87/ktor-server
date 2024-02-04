package pt.rvcoding.data.internal

import kotlinx.serialization.Serializable

@Serializable
data class AppointmentEntity(
    val id: String,
    val email: String,
    val phoneNumber: String,
    val date: Long,
    val duration: Long,
    val serviceCode: Int, //CustomerService
    val pending: Boolean,
    val approvedByAdmin: Boolean,
    val cancelledByUser: Boolean,
    val cancelledByAdmin: Boolean,
    val cancellationMessage: String? = null,
    val notified: Boolean,
)