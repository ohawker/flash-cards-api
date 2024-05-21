package com.hawklogica.flashcardsapi.controller.dto

data class FlashCardDto(
    val id: Long,
    val english: String,
    val translation: String,
    val targetLanguage: String,
    val tries: Int,
    val correctAnswers: Int,
)
