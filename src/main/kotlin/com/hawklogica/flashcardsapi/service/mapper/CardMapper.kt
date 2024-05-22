package com.hawklogica.flashcardsapi.service.mapper

import com.hawklogica.flashcardsapi.controller.dto.CreateCardDto
import com.hawklogica.flashcardsapi.controller.dto.FlashCardDto
import com.hawklogica.flashcardsapi.repository.entities.FlashCard
import org.springframework.stereotype.Service

@Service
class CardMapper {

    fun toEntity(dto: CreateCardDto): FlashCard {
        return FlashCard(
            id = 0,
            english = dto.english,
            translation = dto.translation,
            targetLanguage = dto.targetLanguage,
            tries = 0,
            correctAnswers = 0,
        )
    }

    fun toDto(entity: FlashCard): FlashCardDto {
        return FlashCardDto(
            id = entity.id,
            english = entity.english,
            translation = entity.translation,
            targetLanguage = entity.targetLanguage,
            tries = entity.tries,
            correctAnswers = entity.correctAnswers,
        )
    }
}
