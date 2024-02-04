package pt.rvcoding.repository

import org.koin.java.KoinJavaComponent.inject
import pt.rvcoding.domain.models.User
import pt.rvcoding.domain.repository.*
import java.util.*

class AuthRepositoryImpl : AuthRepository {
    private val userRepository: UserRepository by inject(UserRepository::class.java)

    override fun register(username: String?, password: String?): RegisterResult {
        return try {
            when {
                !validCredentials(username, password) -> RegisterResult.InvalidParametersError
                userRepository.containsEmail(username) -> RegisterResult.UserAlreadyRegisteredError
                else -> {
                    val user = User(
                        email = username ?: "",
                        password = password ?: "",
                        lastLogin = Date().time,
                    )
                    userRepository.update(user)
                    RegisterResult.Success
                }
            }
        } catch (e: Exception) { RegisterResult.UnauthorizedError }
    }

    override fun login(username: String?, password: String?): LoginResult {
        return try {
            when {
                !validCredentials(username, password) -> LoginResult.InvalidParametersError
                !userRepository.containsEmail(username) -> LoginResult.CredentialsMismatchError
                else -> {
                    val user = userRepository.get(username ?: "")
                    user?.let {
                        userRepository.update(
                            user.copy(lastLogin = Date().time)
                        )
                    }
                    LoginResult.Success
                }
            }
        } catch (e: Exception) { LoginResult.UnauthorizedError }
    }

    override fun changePassword(username: String?, password: String?, newPassword: String?): ChangePasswordResult {
        return ChangePasswordResult.Success
    }

    private fun validCredentials(username: String?, password: String?): Boolean = !(username.isNullOrBlank() || password.isNullOrBlank())
    private fun validPasswordChange(username: String?, password: String?, newPassword: String?): Boolean = !(username.isNullOrBlank() || password.isNullOrBlank() || newPassword.isNullOrBlank())
}