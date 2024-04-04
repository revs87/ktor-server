package pt.rvcoding.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import pt.rvcoding.domain.Route
import pt.rvcoding.domain.models.Appointment
import pt.rvcoding.domain.models.CustomerCategory
import pt.rvcoding.domain.repository.AppointmentRepository
import pt.rvcoding.domain.response.AppointmentsResponse


fun Routing.appointments() {
    val appointmentRepository: AppointmentRepository by inject()

    get(Route.Appointments.path) {
        val appointments = appointmentRepository.getAll()
        call.respondText(Json.encodeToString(AppointmentsResponse(appointments)), ContentType.Application.Json)
    }

    get(Route.AppointmentServices.path) {
        val services = CustomerCategory.DefaultCustomerCategories
        call.respondText(Json.encodeToString(services), ContentType.Application.Json)
    }

    post(Route.AppointmentSchedule.path) {
        appointmentRepository.update(Appointment.Default)
    }
}