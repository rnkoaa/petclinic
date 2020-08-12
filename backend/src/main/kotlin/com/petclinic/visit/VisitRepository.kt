package com.petclinic.visit

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
import com.petclinic.owner.model.Owner
import com.petclinic.owner.model.Pet
import com.petclinic.owner.model.PetType
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Instant
import java.time.LocalDate
import java.util.*


interface VisitRepository : BaseRepository<Visit, UUID> {
    fun findByPetId(petId: UUID): Flux<Visit>
}

@Component
class VisitRepositoryImpl(val firestore: Firestore) : VisitRepository {
    fun mapDocumentToVisit(doc: DocumentSnapshot): Visit {
        val visitDateStr = doc["visit_date"] as String
        val visitDate = Instant.parse(visitDateStr)

        val petId = UUID.fromString(doc["pet_id"] as String)

        val ownerId = UUID.fromString(doc["owner_id"] as String)
        val vetId = UUID.fromString(doc["vet_id"] as String)
        val description = doc["description"] as String

        return Visit(UUID.fromString(doc.id), visitDate, petId, ownerId, vetId, description)
    }

    fun convertQuerySnapshot(querySnapshot: ApiFuture<QuerySnapshot>): Flux<Visit> {
        return Mono.create<QuerySnapshot> { monoSink -> ApiFutures.addCallback(querySnapshot, ApiFutureQuerySnapshotCallbackImpl(monoSink)) }
                .filter { it.documents.size > 0 }
                .map { it.documents }
                .flatMapIterable { it }
                .map { doc -> mapDocumentToVisit(doc) }
    }

    override fun findByPetId(petId: UUID): Flux<Visit> {
        val visitCollection = firestore.collection("visit")
        val querySnapshot = visitCollection.whereEqualTo("pet_id", petId.toString()).get()
        return convertQuerySnapshot(querySnapshot)
    }

    override fun save(visit: Visit): Mono<Visit> {
        val visitCollection = firestore.collection("visit")
        val visitWithId = if (!visit.isNew()) visit else visit.copy(id = UUID.randomUUID())
        val document = visitCollection.document(visitWithId.id!!.toString())
        val data = mutableMapOf<String, Any?>()
        data["visit_date"] = visit.date.toString()
        data["pet_id"] = visit.petId.toString()
        data["owner_id"] = visit.ownerId.toString()
        data["vet_id"] = visit.vetId.toString()
        data["description"] = visit.description
        val resultFuture: ApiFuture<WriteResult> = document.set(data)

        return Mono.create<WriteResult> { sink -> ApiFutures.addCallback(resultFuture, ApiFutureCallbackImpl(sink)) }
                .map { visitWithId }
    }

    override fun save(visits: List<Visit>): Flux<Visit> {
        val savedVisits = visits.map { save(it) }
        return Flux.mergeSequential(savedVisits)
    }

    override fun findById(id: UUID): Mono<Visit> {
        val documentFuture = firestore.collection("visit").document(id.toString()).get()
        return Mono.create<DocumentSnapshot> { monoSink -> ApiFutures.addCallback(documentFuture, ApiFutureDocumentSnapshotCallbackImpl(monoSink)) }
                .map { doc -> mapDocumentToVisit(doc) }
    }

    override fun findAll(): Flux<Visit> {
        val querySnapshot = firestore.collection("visit").get()
        return convertQuerySnapshot(querySnapshot)
    }

}