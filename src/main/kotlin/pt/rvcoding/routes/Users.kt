package pt.rvcoding.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Routing.users(companyId: String) {
    get("/$companyId/users") {
        call.respondText { "Hello World!" }
    }
    get("/$companyId/users/{username}") {
        val username = call.parameters["username"]
        call.respondText { "Greetings, $username!" }
    }
}