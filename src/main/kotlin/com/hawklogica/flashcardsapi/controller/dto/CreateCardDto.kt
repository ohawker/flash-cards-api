package com.hawklogica.flashcardsapi.controller.dto

data class CreateCardDto(
    val english: String,
    val translation: String,
    val targetLanguage: String,
)
