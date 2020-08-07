package com.petclinic.common.repository

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BaseRepository<T, U> {
    fun save(item: T): Mono<T>
    fun save(item: List<T>): Flux<T>
    fun findById(id: U): Mono<T>
    fun findAll(): Flux<T>
}