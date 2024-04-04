package pt.rvcoding.plugins

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.koin.core.context.GlobalContext.stopKoin
import pt.rvcoding.domain.Route
import pt.rvcoding.domain.response.AuthResponse

class RoutingRootTest {

    @AfterEach
    fun tearDown() { stopKoin() }

    @Test
    fun `access root endpoint, returns Hello message`() = testApplication {
        val response = client.get(Route.Root.path)
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Hello, Kotlin Multiplatform guinea pig!", response.bodyAsText())
    }

    @Test
    fun `access unknown endpoint, returns Not found message`() = testApplication {
        val response = client.get("/unknown")
        assertEquals(HttpStatusCode.NotFound, response.status)
        val actual = Json.decodeFromString<AuthResponse>(response.bodyAsText())
        assertEquals(Error.NotFoundError.code, actual.code)
        assertEquals("NotFoundError", actual.type)
        assertEquals("Not found.", actual.message)
    }

}