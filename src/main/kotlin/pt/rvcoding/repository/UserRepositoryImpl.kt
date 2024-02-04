package pt.rvcoding.repository

import pt.rvcoding.domain.models.User
import pt.rvcoding.domain.repository.UserRepository
import java.util.*

class UserRepositoryImpl: UserRepository {

    private val users: MutableMap<String, User> = Collections.synchronizedMap(mutableMapOf())

    override fun containsEmail(email: String?): Boolean = email?.let { users.contains(email) } ?: false
    override fun validCredentials(email: String, password: String): Boolean {
        val user = users[email]
        return when {
            user == null -> false
            user.password == password -> true
            else -> false
        }
    }
    override fun get(email: String): User? = users[email]
    override fun getAll(): List<User> = users.values.toList()
    override fun update(user: User) { users[user.unique()] = user }

    private fun User.unique(): String = this.email
}