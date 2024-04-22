package com.example

import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)


fun Application.module() {
    configureSerialization()
    configureRouting()
    install(CORS) {
        anyHost()
    }
}
