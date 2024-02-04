package pt.rvcoding.domain

import pt.rvcoding.domain.Configuration.Companion.COMPANY_ID as id

sealed class Route(val path: String) {
    data object Configuration: Route("/$id/configuration")
    data object Register: Route("/$id/register")
    data object Login: Route("/$id/login")
    data object LoginInternal: Route("/$id/loginInternal")
    data object Users: Route("/$id/users")
    data object Appointments: Route("/$id/appointments")
}