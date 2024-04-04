package pt.rvcoding.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import pt.rvcoding.domain.Route


fun Routing.root() {
    staticResources("/rvcbanner", "static") { default("rvcbanner.jpg") }
    staticResources("/styles", "styles") { default("styles.css") }

    get(Route.Hello.path) {
        call.respond(
            message = "Hello, Kotlin Multiplatform guinea pig!",
            status = HttpStatusCode.OK
        )
    }
}