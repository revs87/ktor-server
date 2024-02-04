package pt.rvcoding.domain.response

import kotlinx.serialization.Serializable
import pt.rvcoding.domain.models.User

@Serializable
data class UsersResponse(
    val users: List<User>
)