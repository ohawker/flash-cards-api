package com.hawklogica.flashcardsapi.service

import com.hawklogica.flashcardsapi.controller.dto.CreateCardDto
import com.hawklogica.flashcardsapi.controller.dto.FlashCardDto
import com.hawklogica.flashcardsapi.controller.dto.RandomSelection
import com.hawklogica.flashcardsapi.exception.ExceptionBuilder
import com.hawklogica.flashcardsapi.repository.FlashCardRepository
import com.hawklogica.flashcardsapi.service.mapper.CardMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class CardModificationService(
    private val flashCardRepository: FlashCardRepository,
    private val mapper: CardMapper,
) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(CardModificationService::class.java)
    }

    fun create(create: CreateCardDto): Result<FlashCardDto, String> {
        LOGGER.info("Received request to create card ${create.english} <-> ${create.translation}")
        val entity = mapper.toEntity(create)
        val savedCard = flashCardRepository.save(entity)
        LOGGER.info("Saved card by english: ${savedCard.english}")
        return Result.Ok(mapper.toDto(savedCard))
    }

    fun fetchRandom(language: String, limit: Int): RandomSelection {
        LOGGER.info("Received request to fetch $limit results for language: $language")
        val results = flashCardRepository.randomByLanguage(language, limit).map(mapper::toDto)
        return RandomSelection(
            results.size,
            results,
        )
    }

    fun updateAnswers(cardId: Int, correct: String): Result<String, String> {
        LOGGER.info("Received request to update answer for $cardId to $correct")
        val answer = correct.toBoolean()
        val card = flashCardRepository.findById(cardId).getOrElse {
            return Result.Err(ExceptionBuilder.cardNotFound(cardId), 404)
        }

        val updatedCard = if (answer) {
            card.copy(
                correctAnswers = card.correctAnswers + 1,
                tries = card.tries + 1,
            )
        } else {
            card.copy(
                tries = card.tries + 1,
            )
        }

        flashCardRepository.save(updatedCard)
        return Result.Ok("${updatedCard.correctAnswers}/${updatedCard.tries}")
    }
}
