package com.petclinic.vet.repository

import com.google.api.core.ApiFuture
import com.google.api.core.ApiFutures
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.QuerySnapshot
import com.google.cloud.firestore.WriteResult
import com.petclinic.common.ApiFutureCallbackImpl
import com.petclinic.common.ApiFutureDocumentSnapshotCallbackImpl
import com.petclinic.common.ApiFutureQuerySnapshotCallbackImpl
import com.petclinic.common.repository.BaseRepository
import com.petclinic.vet.model.Specialty
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import java.util.concurrent.Executors

interface SpecialtyRepository : BaseRepository<Specialty, UUID> {
    fun findByName(name: String): Mono<Specialty>
}

@Component("specialtyRepository")
class SpecialtyRepositoryImpl(val firestore: Firestore) : SpecialtyRepository {
    var executor = Executors.newScheduledThreadPool(2)
    fun mapDocumentToSpecialty(doc: DocumentSnapshot): Specialty {
        val specialtyId = UUID.fromString(doc.id)
        val name = doc["name"] as String
        return Specialty(specialtyId, name)
    }

    override fun findByName(name: String): Mono<Specialty> {
        val ownerCollection = firestore.collection("specialty")
        val querySnapshot = ownerCollection.whereEqualTo("name", name).get()
        return Mono.create<QuerySnapshot> { monoSink ->
            ApiFutures.addCallback(querySnapshot,
                    ApiFutureQuerySnapshotCallbackImpl(monoSink), executor)
        }
                .filter { it.documents.size > 0 }
                .map { it.documents[0] }
                .map { doc -> mapDocumentToSpecialty(doc) }
    }

    override fun save(entity: Specialty): Mono<Specialty> {
        val specialtyWithId = if (!entity.isNew()) entity else entity.copy(id = UUID.randomUUID())
        val document = firestore.collection("specialty").document(specialtyWithId.id!!.toString())
        val data = mutableMapOf<String, Any?>()
        data["specialty_id"] = specialtyWithId.id.toString()
        data["name"] = specialtyWithId.name
        val resultFuture: ApiFuture<WriteResult> = document.set(data)

        return Mono.create<WriteResult> { sink ->
            ApiFutures.addCallback(resultFuture, ApiFutureCallbackImpl(sink), executor)
        }
                .map { specialtyWithId }
    }

    override fun save(entities: List<Specialty>): Flux<Specialty> {
        val savedSpecialties = entities.map { save(it) }
        return Flux.mergeSequential(savedSpecialties)
    }

    override fun findById(id: UUID): Mono<Specialty> {
        val documentFuture = firestore.collection("specialty").document(id.toString()).get()
        return Mono.create<DocumentSnapshot> { monoSink ->
            ApiFutures.addCallback(documentFuture, ApiFutureDocumentSnapshotCallbackImpl(monoSink), executor)
        }.map { doc -> mapDocumentToSpecialty(doc) }
    }

    override fun findAll(): Flux<Specialty> {
        val querySnapshot = firestore.collection("specialty").get()

        return Mono.create<QuerySnapshot> { monoSink ->
            ApiFutures.addCallback(querySnapshot, ApiFutureQuerySnapshotCallbackImpl(monoSink), executor)
        }.filter { it.documents.size > 0 }
                .map { it.documents }
                .flatMapIterable { it }
                .map { doc -> mapDocumentToSpecialty(doc) }

    }

}