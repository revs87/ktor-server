package pt.rvcoding

import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ApplicationTest {

    @Test
    fun `testSum GIVEN 2 plus 2 THEN returns 4`() = testApplication {
        assertEquals(4, 2 + 2)
    }

}