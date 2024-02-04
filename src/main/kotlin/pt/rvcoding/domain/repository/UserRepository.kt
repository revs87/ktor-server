package pt.rvcoding.domain.repository

import pt.rvcoding.domain.models.User

interface UserRepository {
    fun containsEmail(email: String?): Boolean
    fun get(email: String): User?
    fun getAll(): List<User>
    fun update(user: User)
}