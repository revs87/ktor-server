package pt.rvcoding.plugins

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pt.rvcoding.domain.Route

class RoutingRootTest {

    @Test
    fun `testRoot GIVEN root endpoint THEN returns Hello message`() = testApplication {
        val response = client.get(Route.Hello.path)
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Hello, Kotlin Multiplatform guinea pig!", response.bodyAsText())
    }

}