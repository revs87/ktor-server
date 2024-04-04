package pt.rvcoding.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import pt.rvcoding.routes.*


fun Application.configureRouting() {
    routing {
        root()
        tooling()
        configuration()
        authentication()
        users()
        appointments()
    }
}

