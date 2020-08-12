package com.petclinic.owner.repository

import com.google.api.core.ApiFuture
import com.google.api.core.ApiFutures
import com.google.cloud.firestore.*
import com.petclinic.common.ApiFutureCallbackImpl
import com.petclinic.common.ApiFutureDocumentSnapshotCallbackImpl
import com.petclinic.common.ApiFutureQuerySnapshotCallbackImpl
import com.petclinic.owner.model.Pet
import com.petclinic.owner.model.PetType
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.util.*

interface SubDocumentRepository<T, U, R> {
    fun save(parentId: U, document: T): Mono<T>
    fun saveAll(parentId: U, documents: List<T>): Flux<T>

    fun findById(parentId: U, id: R): Mono<T>
}

interface PetRepository : SubDocumentRepository<Pet, UUID, UUID> {
    fun findByOwnerId(ownerId: UUID): Flux<Pet>
}

@Component("petRepository")
class PetRepositoryImpl(val firestore: Firestore) : PetRepository {
    fun mapDocumentToPet(doc: DocumentSnapshot, ownerId: UUID): Pet {
        val petId = UUID.fromString(doc.id)
        val petDob = doc["birth_date"] as String
        val dateOfBirth = LocalDate.parse(petDob)
        val petType = PetType(doc["pet_type"] as String)
        val name = doc["name"] as String
        return Pet(petId, ownerId, dateOfBirth, petType, name)
    }

    fun mapPetToDocument(collectionReference: CollectionReference, pet: Pet): ApiFuture<WriteResult> {
        val petWithId = if (!pet.isNew()) pet else pet.copy(id = UUID.randomUUID())

        val document = collectionReference.document(petWithId.id!!.toString())
        val data = mutableMapOf<String, Any?>()
        data["pet_id"] = petWithId.id.toString()
        data["owner_id"] = petWithId.ownerId.toString()
        data["name"] = petWithId.name
        data["pet_type"] = petWithId.type.name
        data["birth_date"] = petWithId.birthDate.toString()
        return document.set(data)
    }

    override fun findByOwnerId(ownerId: UUID): Flux<Pet> {
        val querySnapshot = firestore.collection("owner").document(ownerId.toString()).collection("pets").get()

        return Mono.create<QuerySnapshot> { monoSink -> ApiFutures.addCallback(querySnapshot, ApiFutureQuerySnapshotCallbackImpl(monoSink)) }
                .filter { it.documents.size > 0 }
                .map { it.documents }
                .flatMapIterable { it }
                .map { doc -> mapDocumentToPet(doc, ownerId) }
    }

    override fun save(parentId: UUID, document: Pet): Mono<Pet> {
        val collectionReference = firestore.collection("owner").document(parentId.toString()).collection("pets")
        val resultFuture = mapPetToDocument(collectionReference, document)
        return Mono.create<WriteResult> { sink -> ApiFutures.addCallback(resultFuture, ApiFutureCallbackImpl(sink)) }
                .map { document }
    }


    override fun findById(parentId: UUID, id: UUID): Mono<Pet> {
        val documentSnapshot = firestore.collection("owner").document(parentId.toString()).collection("pets")
                .document(id.toString()).get()
        return Mono.create<DocumentSnapshot> { monoSink -> ApiFutures.addCallback(documentSnapshot, ApiFutureDocumentSnapshotCallbackImpl(monoSink)) }
                .map { doc -> mapDocumentToPet(doc, parentId) }
    }


    override fun saveAll(parentId: UUID, documents: List<Pet>): Flux<Pet> {
        val savedOwners = documents.map { save(parentId, it) }
        return Flux.mergeSequential(savedOwners)
    }

}


////@FunctionalInterface
//class DocumentToPetMapper : Function<QueryDocumentSnapshot, Pet> {
//
//}

