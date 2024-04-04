package pt.rvcoding.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import pt.rvcoding.domain.Route
import pt.rvcoding.domain.repository.UserRepository
import pt.rvcoding.domain.response.UsersResponse


fun Routing.users() {
    val userRepository: UserRepository by inject()

    get(Route.Users.path) {
        val users = userRepository.getAll()
        call.respondText(Json.encodeToString(UsersResponse(users)), ContentType.Application.Json)
    }

}