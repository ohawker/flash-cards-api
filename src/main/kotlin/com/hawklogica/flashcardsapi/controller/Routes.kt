package com.hawklogica.flashcardsapi.controller

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

const val BASE_URL = "/api/"
const val BASE_CARD = BASE_URL + "card"
const val RANDOM_CARDS = "$BASE_CARD/random"
const val UPDATE_CARD = "$BASE_CARD/{id}"

@Configuration
class RoutesConfig {

    @Bean
    fun routes(
        cardHandler: CardHandler,
    ) = coRouter {
        GET(RANDOM_CARDS, cardHandler::randomCards)
        POST(BASE_CARD, cardHandler::createNewFlashCard)
        PATCH(UPDATE_CARD, cardHandler::updateAnswers)
    }
}
