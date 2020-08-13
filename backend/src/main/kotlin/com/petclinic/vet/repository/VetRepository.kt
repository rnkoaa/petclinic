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
import com.petclinic.vet.model.Vet
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import java.util.concurrent.Executors

interface VetRepository : BaseRepository<Vet, UUID> {
    fun findByTelephone(telephone: String): Mono<Vet>
}

@Component("vetRepository")
class VetRepositoryImpl(val firestore: Firestore) : VetRepository {
    var executor = Executors.newScheduledThreadPool(2)
    fun mapDocumentToVet(doc: DocumentSnapshot): Vet {
        val specialtyId = UUID.fromString(doc.id)
        val specialtyMap = doc["specialties"] as List<Map<String, String>>
        var specialties = setOf<Specialty>()
        if (specialtyMap.isNotEmpty()) {
            specialties = specialtyMap
                    .map { Specialty(UUID.fromString(it["specialty_id"]), it["name"] as String) }
                    .toSet()
        }

        return Vet(specialtyId, doc["last_name"] as String, doc["first_name"] as String,
                doc["telephone"] as String,
                doc["email"] as String, specialties)
    }

    override fun findByTelephone(telephone: String): Mono<Vet> {
        val ownerCollection = firestore.collection("vet")
        val querySnapshot = ownerCollection.whereEqualTo("telephone", telephone).get()
        return Mono.create<QuerySnapshot> { monoSink ->
            ApiFutures.addCallback(querySnapshot, ApiFutureQuerySnapshotCallbackImpl
            (monoSink), executor)
        }
                .filter { it.documents.size > 0 }
                .map { it.documents[0] }
                .map { doc -> mapDocumentToVet(doc) }
    }

    override fun save(entity: Vet): Mono<Vet> {
        val vetWithId = if (!entity.isNew()) entity else entity.copy(id = UUID.randomUUID())
        val document = firestore.collection("vet").document(vetWithId.id!!.toString())
        val data = mutableMapOf<String, Any?>()
        data["vet_id"] = vetWithId.id
        data["first_name"] = vetWithId.firstName
        data["last_name"] = vetWithId.lastName
        data["telephone"] = vetWithId.telephone
        data["email"] = vetWithId.email

        var vetSpecialties = listOf<Map<String, String>>()
        if (vetWithId.specialties.isNotEmpty()) {
            vetSpecialties = vetWithId.specialties.map {
                mapOf("name" to it.name, "specialty_id" to it.id.toString())
            }
        }
        data["specialties"] = vetSpecialties
        val resultFuture: ApiFuture<WriteResult> = document.set(data)

        return Mono.create<WriteResult> { sink -> ApiFutures.addCallback(resultFuture, ApiFutureCallbackImpl(sink), executor) }
                .map { vetWithId }
    }

    override fun save(entities: List<Vet>): Flux<Vet> {
        val savedVets = entities.map { save(it) }
        return Flux.mergeSequential(savedVets)
    }

    override fun findById(id: UUID): Mono<Vet> {
        val documentFuture = firestore.collection("vet").document(id.toString()).get()
        return Mono.create<DocumentSnapshot> { monoSink ->
            ApiFutures.addCallback(documentFuture,
                    ApiFutureDocumentSnapshotCallbackImpl(monoSink), executor)
        }
                .map { doc -> mapDocumentToVet(doc) }
    }

    override fun findAll(): Flux<Vet> {
        val querySnapshot = firestore.collection("vet").get()

        return Mono.create<QuerySnapshot> { monoSink ->
            ApiFutures.addCallback(querySnapshot, ApiFutureQuerySnapshotCallbackImpl(monoSink), executor)
        }
                .filter { it.documents.size > 0 }
                .map { it.documents }
                .flatMapIterable { it }
                .map { doc -> mapDocumentToVet(doc) }
    }
}
