package com.hawklogica.flashcardsapi.controller

import com.hawklogica.flashcardsapi.controller.dto.CreateCardDto
import com.hawklogica.flashcardsapi.service.CardModificationService
import com.hawklogica.flashcardsapi.service.Result
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import org.springframework.web.reactive.function.server.queryParamOrNull
import kotlin.jvm.optionals.getOrElse

@Service
class CardHandler(
    private val cardModificationService: CardModificationService,
) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(CardHandler::class.java)
    }

    suspend fun randomCards(request: ServerRequest): ServerResponse {
        val params = try {
            val language = request.queryParam("language").getOrElse {
                throw RuntimeException("Language must be supplied in the query parameter")
            }
            val limit = request.queryParamOrNull("amount").toString().toInt()
            Pair(language, limit)
        } catch (exception: Exception) {
            LOGGER.error("Invalid params supplied ${exception.message}")
            return ServerResponse.badRequest().buildAndAwait()
        }

        val results = cardModificationService.fetchRandom(params.first, params.second)
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(results)
    }

    suspend fun createNewFlashCard(request: ServerRequest): ServerResponse {
        val card = try {
            request.awaitBody<CreateCardDto>()
        } catch (exception: Exception) {
            LOGGER.error("Error while parsing CreateCardDto, detail: ${exception.message}")
            return ServerResponse.badRequest().buildAndAwait()
        }

        return when (val result = cardModificationService.create(card)) {
            is Result.Ok -> ServerResponse.created(request.uri())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(result.value)
            is Result.Err -> ServerResponse.badRequest().bodyValueAndAwait(result.error)
        }
    }

    suspend fun updateAnswers(request: ServerRequest): ServerResponse {
        val params = try {
            val id = request.pathVariable("id").toInt()
            val correct = request.queryParam("correct").getOrElse {
                throw RuntimeException("Must provide param 'correct' to indicate if the user has guessed correctly or not")
            }
            Pair(id, correct)
        } catch (exception: Exception) {
            LOGGER.error("Invalid params provided ${exception.message}")
            return ServerResponse.badRequest().buildAndAwait()
        }

        return when (val result = cardModificationService.updateAnswers(params.first, params.second)) {
            is Result.Ok -> ServerResponse.ok().bodyValueAndAwait(result.value)
            is Result.Err -> ServerResponse.badRequest().bodyValueAndAwait(result.error)
        }
    }
}
