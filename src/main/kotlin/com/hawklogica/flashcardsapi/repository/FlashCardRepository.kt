package com.hawklogica.flashcardsapi.repository

import com.hawklogica.flashcardsapi.repository.entities.FlashCard
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface FlashCardRepository : JpaRepository<FlashCard, Int> {

    @Query("select * from flash_card where target_language = :lang ORDER BY random() LIMIT :size", nativeQuery = true)
    fun randomByLanguage(@Param("lang")language: String, @Param("size") limit: Int): List<FlashCard>
}
