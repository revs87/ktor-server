package pt.rvcoding

import io.ktor.server.application.*
import pt.rvcoding.plugins.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
//fun main(args: Array<String>): Unit {
//    embeddedServer(Netty, port = 8080) {
//        module()
//    }.start(wait = true)
//}

@Suppress("Unused")
fun Application.module() {
    configureSerialization()
    configureKoin()
    configureRouting("rvc")
    configureMonitoring()
    configureDefaultHeader()
    configureStatusPages()
}
