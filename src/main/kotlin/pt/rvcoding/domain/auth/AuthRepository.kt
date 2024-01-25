package pt.rvcoding.domain.auth

interface AuthRepository {
    fun register(username: String?, password: String?): RegisterResult
    fun login(username: String?, password: String?): LoginResult
    fun changePassword(username: String?, password: String?, newPassword: String?): ChangePasswordResult
}

sealed class RegisterResult(val code: Int) {
    data object Success : RegisterResult(100)
    data object InvalidParametersError : RegisterResult(101)
    data object CriteriaNotMetError : RegisterResult(102)
    data object UserAlreadyRegisteredError : RegisterResult(103)
}

sealed class LoginResult(val code: Int) {
    data object Success : LoginResult(200)
    data object InvalidParametersError : LoginResult(201)
    data object CredentialsMismatchError : LoginResult(202)
    data object UnauthorizedError : LoginResult(203)
}

sealed class ChangePasswordResult(val code: Int) {
    data object Success : ChangePasswordResult(300)
    data object InvalidParametersError : ChangePasswordResult(301)
    data object CredentialsMismatchError : ChangePasswordResult(302)
    data object CriteriaNotMetError : ChangePasswordResult(303)
}