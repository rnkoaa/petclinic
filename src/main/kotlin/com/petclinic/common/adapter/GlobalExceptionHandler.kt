package com.petclinic.common.adapter


import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.core.annotation.Order
import org.springframework.core.codec.DecodingException
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.ServerWebInputException
import org.springframework.web.server.WebExceptionHandler
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.nio.charset.Charset

private fun writeValueAsBytes(objectMapper: ObjectMapper, exceptionResponse: ExceptionResponse?): ByteArray? {
    return try {
        objectMapper.writeValueAsBytes(exceptionResponse)
    } catch (e: JsonProcessingException) {
        e.message!!.toByteArray(Charset.defaultCharset())
    }
}

private fun wrapResponse(objectMapper: ObjectMapper, ex: Throwable, exchange: ServerWebExchange): Mono<Void> {
    val errorResponse: ExceptionResponse? = ex.message?.let { ExceptionResponse(it) }
    val bytes: ByteArray? = writeValueAsBytes(objectMapper, errorResponse)
    if (bytes != null) {
        val buffer: DataBuffer = exchange.response.bufferFactory().wrap(bytes)
        return exchange.response.writeWith(Flux.just(buffer))
    }
    return Mono.empty()
}

@Component
@Order(-2)
class GlobalExceptionHandler(val objectMapper: ObjectMapper) : WebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        if (ex is NotFoundException ) {
            exchange.response.headers
                    .add("Content-Type", "application/json")
            exchange.response.statusCode = HttpStatus.NOT_FOUND
            return wrapResponse(objectMapper, ex, exchange)
        }
        return Mono.error(ex)
    }


}

@Component
@Order(-1)
class MissingParameterExceptionHandler(val objectMapper: ObjectMapper) : WebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        if (ex is ServerWebInputException || ex is MissingKotlinParameterException || ex is DecodingException) {
            exchange.response.headers
                    .add("Content-Type", "application/json")
            exchange.response.statusCode = HttpStatus.BAD_REQUEST
            return wrapResponse(objectMapper, ex, exchange)
        }
        return Mono.error(ex)
    }
}

@Component
@Order(-1)
class DuplicateKeyExceptionHandler(val objectMapper: ObjectMapper) : WebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        if (ex is DuplicateKeyException) {
            exchange.response.headers
                    .add("Content-Type", "application/json")
            exchange.response.statusCode = HttpStatus.CONFLICT
            return wrapResponse(objectMapper, ex, exchange)
        }
        return Mono.error(ex)
    }
}

@Component
@Order(-1)
class InvalidIdExceptionHandler(val objectMapper: ObjectMapper) : WebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        if (ex is InvalidProductIdException || ex is IllegalArgumentException) {
            exchange.response.headers
                    .add("Content-Type", "application/json")
            exchange.response.statusCode = HttpStatus.BAD_REQUEST
            return wrapResponse(objectMapper, ex, exchange)
        }
        return Mono.error(ex)
    }
}

