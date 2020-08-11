package com.petclinic.common.repository

import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.QueryDocumentSnapshot
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import java.util.function.Function

interface BaseRepository<T, U> {
    fun save(item: T): Mono<T>
    fun save(item: List<T>): Flux<T>
    fun findById(id: U): Mono<T>
    fun findAll(): Flux<T>
}

abstract class BaseFirestoreRepository<T>(val collectionName: String, val firestore: Firestore) {

    fun saveDocuments(t: List<T>, mapper: Function<T, DocumentReference>): Flux<T> {
        val ownerCollection = firestore.collection(collectionName)
        return Flux.empty()
    }
    fun saveDocument(t: T, mapper: Function<T, DocumentReference>): Mono<T> {

        return Mono.empty()
    }

    fun findDocumentById(id: UUID, mapper: Function<QueryDocumentSnapshot, T>): Mono<T> {
        return Mono.empty()
    }

    fun findAll(mapper: Function<QueryDocumentSnapshot, T>): Flux<T> {
        return Flux.empty()
    }
}