package pt.rvcoding.domain.repository

import pt.rvcoding.domain.models.Appointment

interface AppointmentRepository {
    fun update(appointment: Appointment)
    fun remove(appointment: Appointment)
    fun getAll(): List<Appointment>
}