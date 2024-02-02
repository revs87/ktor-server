package pt.rvcoding.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import pt.rvcoding.domain.auth.AuthResponse

fun Application.configureStatusPages() {
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, _ ->
            call.respond(
                message = AuthResponse(
                    Error.NotFoundError.code,
                    Error.NotFoundError::class.simpleName.toString(),
                    "Not found."
                ),
                status = HttpStatusCode.NotFound
            )
        }

        exception<RuntimeException> { call, _ ->
            call.respond(
                message = AuthResponse(
                    Error.UnauthorizedError.code,
                    Error.UnauthorizedError::class.simpleName.toString(),
                    "Unauthorized."
                ),
                status = HttpStatusCode.Unauthorized
            )
        }

    }
}

sealed class Error(val code: Int) {
    data object UnauthorizedError : Error(900)
    data object NotFoundError : Error(901)
}