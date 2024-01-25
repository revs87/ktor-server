package pt.rvcoding.repository

import pt.rvcoding.domain.auth.AuthRepository
import pt.rvcoding.domain.auth.ChangePasswordResult
import pt.rvcoding.domain.auth.LoginResult
import pt.rvcoding.domain.auth.RegisterResult
import java.util.Collections.synchronizedList

class AuthRepositoryImpl : AuthRepository {
    private val users: MutableList<String> = synchronizedList(mutableListOf())

    override fun register(username: String?, password: String?): RegisterResult {
        return when {
            !validCredentials(username, password) -> RegisterResult.InvalidParametersError
            users.contains(username) -> RegisterResult.UserAlreadyRegisteredError
            else -> {
                username?.let { users.add(it) }
                RegisterResult.Success
            }
        }
    }

    override fun login(username: String?, password: String?): LoginResult {
        return when {
            !validCredentials(username, password) -> LoginResult.InvalidParametersError
            !users.contains(username) -> LoginResult.CredentialsMismatchError
            else -> LoginResult.Success
        }
    }

    override fun changePassword(username: String?, password: String?, newPassword: String?): ChangePasswordResult {
        return ChangePasswordResult.Success
    }

    private fun validCredentials(username: String?, password: String?): Boolean = !(username.isNullOrBlank() || password.isNullOrBlank())
    private fun validPasswordChange(username: String?, password: String?, newPassword: String?): Boolean = !(username.isNullOrBlank() || password.isNullOrBlank() || newPassword.isNullOrBlank())
}