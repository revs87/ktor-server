package pt.rvcoding.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import pt.rvcoding.routes.authentication
import pt.rvcoding.routes.configuration
import pt.rvcoding.routes.users


fun Application.configureRouting(companyId: String) {
    routing {
        root()
        configuration(companyId)
        authentication(companyId)
        users(companyId)
    }
}

private fun Routing.root() {
    get("/") {
        call.respondText { "Hello World!" }
    }
}
