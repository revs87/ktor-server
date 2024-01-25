package pt.rvcoding.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject
import pt.rvcoding.repository.PPKGenerator
import pt.rvcoding.repository.decodeToString


fun Routing.tooling() {
    val ppkGenerator: PPKGenerator by inject()

    get("/ppk") {
        val encrypted = call.request.queryParameters["encrypted"] ?: ""
        call.respondHtml {
            head {
                title { + "PPK Generator" }
                link(rel = "stylesheet", href = "/styles")
            }
            body {
                div {
                    style = "max-width: 600px; margin: 0 auto; word-wrap: break-word;"  // Set the maximum width, center the container, and enable word wrapping

                    br
                    p { +"PRIVATE KEY:" }
                    p { +ppkGenerator.privateKey.encoded.decodeToString() }
                    br
                    p { +"PUBLIC KEY:" }
                    p { +ppkGenerator.publicKey.encoded.decodeToString() }
                    br

                    form("/ppkRefresh", method = FormMethod.post) {
                        button {
                            type = ButtonType.submit
                            +"REFRESH"
                        }
                    }
                    br
                }
            }
        }
    }
    post("/ppkRefresh") {
        ppkGenerator.generateNewKeyPair()
        call.respondRedirect("/ppk", permanent = false)
    }
//    get("/ppkRefreshJson") {
//        ppkGenerator.generateNewKeyPair()
//        call.respond(
//            message = PPKResponse(
//                ppkGenerator.privateKey.encoded.decodeToString(),
//                ppkGenerator.publicKey.encoded.decodeToString()
//            )
//        )
//    }

    post("/encrypt") {
        val request = call.receive<EncryptRequest>()
        val encrypted = ppkGenerator.encrypt(request.plain)
        call.respond(message = encrypted, status = HttpStatusCode.OK)
    }
    post("/decrypt") {
        val request = call.receive<DecryptRequest>()
        val decrypted = ppkGenerator.decrypt(request.encrypted)
        call.respond(message = decrypted, status = HttpStatusCode.OK)
    }
}

@Serializable
data class EncryptRequest(val plain: String)

@Serializable
data class DecryptRequest(val encrypted: String)