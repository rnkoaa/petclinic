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
import com.petclinic.owner.model.Owner
import com.petclinic.owner.model.Pet
import com.petclinic.owner.model.PetType
import com.petclinic.vet.model.Specialty
import com.petclinic.vet.model.VetSpecialty
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.util.*

interface SpecialtyRepository : BaseRepository<Specialty, UUID> {
    fun findByName(name: String): Mono<Specialty>
}

@Component("specialtyRepository")
class SpecialtyRepositoryImpl(val firestore: Firestore) : SpecialtyRepository {

    fun mapDocumentToSpecialty(doc: DocumentSnapshot): Specialty {
        val specialtyId = UUID.fromString(doc.id)
        val name = doc["name"] as String
        return Specialty(specialtyId, name)
    }

    override fun findByName(name: String): Mono<Specialty> {
        val ownerCollection = firestore.collection("specialty")
        val querySnapshot = ownerCollection.whereEqualTo("name", name).get()
        return Mono.create<QuerySnapshot> { monoSink -> ApiFutures.addCallback(querySnapshot, ApiFutureQuerySnapshotCallbackImpl(monoSink)) }
                .filter { it.documents.size > 0 }
                .map { it.documents[0] }
                .map { doc -> mapDocumentToSpecialty(doc) }
    }

    override fun save(specialty: Specialty): Mono<Specialty> {
        val specialtyWithId = if (!specialty.isNew()) specialty else specialty.copy(id = UUID.randomUUID())
        val document = firestore.collection("specialty").document(specialtyWithId.id!!.toString())
        val data = mutableMapOf<String, Any?>()
        data["specialty_id"] = specialtyWithId.id.toString()
        data["name"] = specialtyWithId.name
        val resultFuture: ApiFuture<WriteResult> = document.set(data)

        return Mono.create<WriteResult> { sink -> ApiFutures.addCallback(resultFuture, ApiFutureCallbackImpl(sink)) }
                .map { specialtyWithId }
    }

    override fun save(specialties: List<Specialty>): Flux<Specialty> {
        val savedSpecialties = specialties.map { save(it) }
        return Flux.mergeSequential(savedSpecialties)
    }

    override fun findById(id: UUID): Mono<Specialty> {
        val documentFuture = firestore.collection("specialty").document(id.toString()).get()
        return Mono.create<DocumentSnapshot> { monoSink -> ApiFutures.addCallback(documentFuture, ApiFutureDocumentSnapshotCallbackImpl(monoSink)) }
                .map { doc -> mapDocumentToSpecialty(doc) }
    }

    override fun findAll(): Flux<Specialty> {
        val querySnapshot = firestore.collection("specialty").get()

        return Mono.create<QuerySnapshot> { monoSink -> ApiFutures.addCallback(querySnapshot, ApiFutureQuerySnapshotCallbackImpl(monoSink)) }
                .filter { it.documents.size > 0 }
                .map { it.documents }
                .flatMapIterable { it }
                .map { doc -> mapDocumentToSpecialty(doc) }
    }

}