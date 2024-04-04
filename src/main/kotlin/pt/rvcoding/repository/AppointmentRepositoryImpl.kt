package pt.rvcoding.repository

import pt.rvcoding.domain.models.Appointment
import pt.rvcoding.domain.repository.AppointmentRepository

class AppointmentRepositoryImpl : AppointmentRepository {
    private val appointments: MutableMap<String, Appointment> = mutableMapOf()

    override fun update(appointment: Appointment) {
        appointments[appointment.unique()] = appointment
    }

    override fun remove(appointment: Appointment) {
        appointments.remove(appointment.unique())
    }

    override fun getAll(): List<Appointment> = appointments.values.toList()

    private fun Appointment.unique(): String = "${this.email}-${this.phoneNumber}-${this.date}-${this.duration}"
}