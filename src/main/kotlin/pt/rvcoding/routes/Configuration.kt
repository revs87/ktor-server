package pt.rvcoding.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject
import pt.rvcoding.domain.Configuration.Companion.EXPIRATION_TIME
import pt.rvcoding.domain.Configuration.Companion.TIMEOUT
import pt.rvcoding.domain.Route
import pt.rvcoding.repository.PPKGenerator
import pt.rvcoding.routes.ConfigurationKeys.Companion.CURRENCY_KEY
import pt.rvcoding.routes.ConfigurationKeys.Companion.EXPIRATION_TIME_KEY
import pt.rvcoding.routes.ConfigurationKeys.Companion.FEATURE_FLAGS_KEY
import pt.rvcoding.routes.ConfigurationKeys.Companion.PUBLIC_ALGORITHM_KEY
import pt.rvcoding.routes.ConfigurationKeys.Companion.PUBLIC_FORMAT_KEY
import pt.rvcoding.routes.ConfigurationKeys.Companion.PUBLIC_KEY_KEY
import pt.rvcoding.routes.ConfigurationKeys.Companion.TIMEOUT_KEY

fun Routing.configuration() {
    val ppkGenerator: PPKGenerator by inject()

    get(Route.Configuration.path) {
        call.respond(
            message = ConfigurationResponse(
                configuration = mapOf(
                    PUBLIC_KEY_KEY to ppkGenerator.publicKeyAsString(),
                    FEATURE_FLAGS_KEY to "",
                    CURRENCY_KEY to "EUR",
                    EXPIRATION_TIME_KEY to EXPIRATION_TIME.toString(),
                    TIMEOUT_KEY to TIMEOUT.toString(),
                    PUBLIC_ALGORITHM_KEY to ppkGenerator.publicKey.algorithm,
                    PUBLIC_FORMAT_KEY to ppkGenerator.publicKey.format,
                )
            ),
            status = HttpStatusCode.OK
        )
    }
}

@Serializable
data class ConfigurationResponse(
    val configuration: Map<String, String>
)

class ConfigurationKeys {
    companion object {
        const val PUBLIC_KEY_KEY = "public"
        const val PUBLIC_ALGORITHM_KEY = "publicAlgorithm"
        const val PUBLIC_FORMAT_KEY = "publicFormat"
        const val FEATURE_FLAGS_KEY = "featureFlags"
        const val CURRENCY_KEY = "currency"
        const val EXPIRATION_TIME_KEY = "expirationInMillis"
        const val TIMEOUT_KEY = "timeoutInMillis"
    }
}