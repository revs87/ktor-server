package pt.rvcoding.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import pt.rvcoding.domain.AuthRepository
import pt.rvcoding.domain.LoginResult
import pt.rvcoding.domain.RegisterResult.*
import pt.rvcoding.domain.models.AuthResponse


fun Routing.authentication(companyId: String) {
    val authRepository: AuthRepository by inject()

    get("/$companyId/register") {
        val username = call.request.queryParameters["username"]
        val password = call.request.queryParameters["password"]
        when (val result = authRepository.register(username, password)) {
            is InvalidParametersError, is CriteriaNotMetError -> call.respond(
                message = AuthResponse(result.code, result::class.simpleName.toString(), "User registration failed."),
                status = HttpStatusCode.BadRequest
            )
            is UserAlreadyRegisteredError -> {
                call.respondRedirect(url = "/$companyId/login?username=$username&password=$password", permanent = false)
            }
            is Success -> call.respond(
                message = AuthResponse(result.code, result::class.simpleName.toString(), "User registered successfully."),
                status = HttpStatusCode.OK
            )
        }
    }

    get("/$companyId/login") {
        val username = call.request.queryParameters["username"]
        val password = call.request.queryParameters["password"]
        when (val result = authRepository.login(username, password)) {
            LoginResult.CredentialsMismatchError -> call.respond(
                message = AuthResponse(result.code, result::class.simpleName.toString(), "User login failed."),
                status = HttpStatusCode.PreconditionFailed
            )
            LoginResult.InvalidParametersError -> call.respond(
                message = AuthResponse(result.code, result::class.simpleName.toString(), "User login failed."),
                status = HttpStatusCode.BadRequest
            )
            LoginResult.Success -> call.respond(
                message = AuthResponse(result.code, result::class.simpleName.toString(), "User logged in successfully."),
                status = HttpStatusCode.OK
            )
        }
    }
    get("/$companyId/changepassword") {
//        when (validPasswordChange(call.request.queryParameters)) {
//            true -> call.respond(message = "Password change SUCCESS", status = HttpStatusCode.OK)
//            false -> call.respond(message = "Password change FAILED", status = HttpStatusCode.BadRequest)
//        }
    }
}
