package com.fabianofranca.randomuser

import com.fabianofranca.randomuser.models.GetUsersArgs
import com.fabianofranca.randomuser.models.RandomUserResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory

class RandomUserClientImpl(
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }
) : RandomUserClient {
    private val logger = LoggerFactory.getLogger("com.fabianofranca.randomuser.ApiRequestLogger")

    override suspend fun getUsers(args: GetUsersArgs): RandomUserResponse {
        logger.info("Making API request to randomuser.me with parameters: results=${args.results}, page=${args.page}, nationality=${args.nationality}")

        val response = client.get("https://randomuser.me/api/") {
            url {
                parameters.append(GetUsersArgs.PARAM_RESULTS, args.results.toString())
                parameters.append(GetUsersArgs.PARAM_PAGE, args.page.toString())
                parameters.append(GetUsersArgs.PARAM_NATIONALITY, args.nationality)
            }
        }

        logger.info("Received response from randomuser.me with status: ${response.status}")
        return response.body()
    }
}
