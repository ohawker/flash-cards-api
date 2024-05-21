package com.hawklogica.flashcardsapi.controller.dto

data class RandomSelection(
    val found: Int,
    val cards: List<FlashCardDto>,
)
