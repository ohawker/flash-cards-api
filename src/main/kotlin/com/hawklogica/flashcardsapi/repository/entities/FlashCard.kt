package com.hawklogica.flashcardsapi.repository.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "flash_card")
data class FlashCard(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val english: String,
    val translation: String,
    val targetLanguage: String,
    val tries: Int,
    val correctAnswers: Int,
)
