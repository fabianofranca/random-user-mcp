package com.fabianofranca

import com.fabianofranca.randomuser.RandomUserMcpServer
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.modelcontextprotocol.kotlin.sdk.server.mcp

private val randomUserMcpServer = RandomUserMcpServer()

fun main(args: Array<String>) {
    val command = args.firstOrNull() ?: "stdio"

    when (command) {
        "sse" -> EngineMain.main(args)
        "stdio" -> randomUserMcpServer.startStdioServer()
        else -> {
            System.err.println("Unknown command: $command")
        }
    }
}

fun Application.module() {
    mcp {
        randomUserMcpServer.server
    }
}