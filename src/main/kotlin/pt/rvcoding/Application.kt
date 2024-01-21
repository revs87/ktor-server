package pt.rvcoding

import io.ktor.server.application.*
import pt.rvcoding.plugins.configureRouting

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
//fun main(args: Array<String>): Unit {
//    embeddedServer(Netty, port = 8080) {
//        module()
//    }.start(wait = true)
//}

@Suppress("Unused")
fun Application.module() {
    configureRouting()
}
