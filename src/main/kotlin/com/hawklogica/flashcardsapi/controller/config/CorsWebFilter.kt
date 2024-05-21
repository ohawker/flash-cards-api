package com.hawklogica.flashcardsapi.controller.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
class CorsWebFilter : WebFluxConfigurer {

    @Value("\${security.cors.allowedMethods}")
    lateinit var allowedMethods: String

    @Value("\${security.cors.allowedHeaders}")
    lateinit var allowedHeaders: String

    @Value("\${security.cors.allowedOrigins}")
    lateinit var allowedOrigins: String

    @Value("\${security.cors.maxAge}")
    var maxAge: Long = 0

    override fun addCorsMappings(corsRegistry: CorsRegistry) {
        corsRegistry.addMapping("/**")
            .allowedMethods(*allowedMethods.split(",").toTypedArray())
            .allowedOrigins(*allowedOrigins.split(",").toTypedArray())
            .allowedHeaders(*allowedHeaders.split(",").toTypedArray())
            .maxAge(maxAge)
    }
}
