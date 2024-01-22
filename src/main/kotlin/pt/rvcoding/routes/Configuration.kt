package pt.rvcoding.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.configuration(companyId: String) {
    get("/$companyId/configuration") {
        // send app configuration details in a list of key-values (public key)
        call.respond(message = "configuration details", status = HttpStatusCode.OK)
    }
}