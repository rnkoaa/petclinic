package com.petclinic.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.petclinic.common.dates.InstantDateDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import java.time.Instant

//@Configuration
//class JacksonModule {
//    @Bean
//    fun mappingJackson2HttpMessageConverter(): MappingJackson2HttpMessageConverter {
//        return MappingJackson2HttpMessageConverter().apply {
//            val javaTimeModule = JavaTimeModule().apply {
//                addDeserializer(Instant::class.java, InstantDateDeserializer())
//            }
//            this.objectMapper = ObjectMapper().apply {
//                registerModule(javaTimeModule)
//                registerModule(KotlinModule())
//            }
//        }
//    }
//}