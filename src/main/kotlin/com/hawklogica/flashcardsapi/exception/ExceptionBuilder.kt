package com.hawklogica.flashcardsapi.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail

object ExceptionBuilder {

    fun cardNotFound(id: Int): ProblemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND,
            "Given card with id: '$id' not found!",
        )
}
