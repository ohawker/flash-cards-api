package com.hawklogica.flashcardsapi

import com.hawklogica.flashcardsapi.Util.readFile
import com.hawklogica.flashcardsapi.controller.BASE_CARD
import com.hawklogica.flashcardsapi.controller.RANDOM_CARDS
import com.hawklogica.flashcardsapi.repository.FlashCardRepository
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class FlashCardTests : TestBase() {

    @Autowired
    private lateinit var flashCardRepository: FlashCardRepository

    @Test
    fun `should create flashcard`() {
        val requestJsonFilePath = "/request/createFlashCard.json"

        val request = readFile(requestJsonFilePath)
        val cardId = RestAssured.given()
            .port(port)
            .contentType(ContentType.JSON)
            .request()
            .body(request)
            .contentType(ContentType.JSON)
            .`when`()
            .post(BASE_CARD)
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .and()
            .extract().path<Int>("id")

        val flashCard = flashCardRepository.findById(cardId)
        Assertions.assertNotNull(flashCard.get())
        Assertions.assertEquals("FR", flashCard.get().targetLanguage)
        Assertions.assertEquals("potato", flashCard.get().english)
        Assertions.assertEquals("pomme de terre", flashCard.get().translation)
    }

    @Test
    fun `should update specific flashcard to correct guess`() {
        val cardId = 101
        RestAssured.given()
            .port(port)
            .contentType(ContentType.JSON)
            .request()
            .contentType(ContentType.JSON)
            .queryParam("correct", "false")
            .`when`()
            .patch("$BASE_CARD/$cardId")
            .then()
            .statusCode(HttpStatus.OK.value())

        val flashCard = flashCardRepository.findById(cardId)
        Assertions.assertNotNull(flashCard.get())
        Assertions.assertEquals(1, flashCard.get().tries)
        Assertions.assertEquals(0, flashCard.get().correctAnswers)
    }

    @Test
    fun `should fetch random x amount of flashcards for a specific language`() {
        RestAssured.given()
            .port(port)
            .contentType(ContentType.JSON)
            .request()
            .contentType(ContentType.JSON)
            .queryParam("language", "FR")
            .queryParam("amount", 10)
            .`when`()
            .get(RANDOM_CARDS)
            .then()
            .statusCode(HttpStatus.OK.value())
            .and()
            .assertThat().body("cards.size()", equalTo(10))
    }
}
