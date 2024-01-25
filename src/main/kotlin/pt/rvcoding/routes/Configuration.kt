package pt.rvcoding.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject
import pt.rvcoding.repository.PPKGenerator
import pt.rvcoding.routes.ConfigurationKeys.Companion.CURRENCY_KEY
import pt.rvcoding.routes.ConfigurationKeys.Companion.FEATURE_FLAGS_KEY
import pt.rvcoding.routes.ConfigurationKeys.Companion.PUBLIC_KEY_KEY
import pt.rvcoding.routes.ConfigurationKeys.Companion.SYNC_INTERVAL_KEY
import pt.rvcoding.routes.ConfigurationKeys.Companion.TIMEOUT_KEY

fun Routing.configuration(companyId: String) {
    val ppkGenerator: PPKGenerator by inject()

    get("/$companyId/configuration") {
        call.respond(
            message = ConfigurationResponse(
                configuration = mapOf(
                    PUBLIC_KEY_KEY to ppkGenerator.publicKeyAsString(),
                    FEATURE_FLAGS_KEY to "",
                    CURRENCY_KEY to "EUR",
                    SYNC_INTERVAL_KEY to "86400000",
                    TIMEOUT_KEY to "60000",
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
        const val PUBLIC_KEY_KEY = "publicKey"
        const val FEATURE_FLAGS_KEY = "featureFlags"
        const val CURRENCY_KEY = "currency"
        const val SYNC_INTERVAL_KEY = "syncIntervalMillis"
        const val TIMEOUT_KEY = "timeoutMillis"
    }
}