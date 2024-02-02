package pt.rvcoding.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject
import pt.rvcoding.domain.auth.AuthRepository
import pt.rvcoding.domain.auth.AuthResponse
import pt.rvcoding.domain.auth.LoginResult
import pt.rvcoding.domain.auth.RegisterResult.*
import pt.rvcoding.repository.PPKGenerator


fun Routing.authentication(companyId: String) {
    val authRepository: AuthRepository by inject()
    val ppkGenerator: PPKGenerator by inject()

    var isInternal = false


    post("/$companyId/register") {
        val request = call.receive<AuthRequest>()
        try {
            val (username, password) = listOf(ppkGenerator.decrypt(request.username), ppkGenerator.decrypt(request.password))
            when (val result = authRepository.register(username, password)) {
                is InvalidParametersError, is CriteriaNotMetError -> call.respond(
                    message = AuthResponse(result.code, result::class.simpleName.toString(), "User registration failed."),
                    status = HttpStatusCode.BadRequest
                )
                is UnauthorizedError -> onUnauthorizedError()
                is UserAlreadyRegisteredError -> {
                    isInternal = true
                    call.respondRedirect(url = "/$companyId/loginInternal?username=$username&password=$password", permanent = false)
                }
                is Success -> call.respond(
                    message = AuthResponse(result.code, result::class.simpleName.toString(), "User registered successfully."),
                    status = HttpStatusCode.OK
                )
            }
        } catch (e: Exception) { onUnauthorizedError() }
    }

    post("/$companyId/login") {
        val request = call.receive<AuthRequest>()
        try {
            val (username, password) = listOf(ppkGenerator.decrypt(request.username), ppkGenerator.decrypt(request.password))
            loginHandle(authRepository, username, password)
        } catch (e: Exception) { onUnauthorizedError() }
    }
    get("/$companyId/loginInternal") {
        if (!isInternal) {
            onUnauthorizedError()
            return@get
        }
        else { isInternal = false }
        val username = call.request.queryParameters["username"] ?: ""
        val password = call.request.queryParameters["password"] ?: ""
        loginHandle(authRepository, username, password)
    }

//    post("/$companyId/changepassword") {
//    }

}

private suspend fun PipelineContext<Unit, ApplicationCall>.loginHandle(
    authRepository: AuthRepository,
    username: String,
    password: String
) {
    when (val result = authRepository.login(username, password)) {
        LoginResult.CredentialsMismatchError -> call.respond(
            message = AuthResponse(result.code, result::class.simpleName.toString(), "User login failed."),
            status = HttpStatusCode.PreconditionFailed
        )
        LoginResult.UnauthorizedError -> onUnauthorizedError()
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

suspend fun PipelineContext<Unit, ApplicationCall>.onUnauthorizedError() {
    call.respond(
        message = AuthResponse(
            LoginResult.UnauthorizedError.code,
            LoginResult.UnauthorizedError::class.simpleName.toString(),
            "User unauthorized."
        ),
        status = HttpStatusCode.Unauthorized
    )
}

@Serializable
data class AuthRequest(
    val username: String,
    val password: String
)
