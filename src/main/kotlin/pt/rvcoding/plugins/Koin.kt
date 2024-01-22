package pt.rvcoding.plugins

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import pt.rvcoding.di.koinModule

fun Application.configureKoin(){
    install(Koin){
        slf4jLogger(level = org.koin.core.logger.Level.ERROR)
        modules(koinModule)
    }
}