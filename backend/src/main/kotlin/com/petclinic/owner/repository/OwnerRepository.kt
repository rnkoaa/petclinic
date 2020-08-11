package com.petclinic.owner.repository

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
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*


interface OwnerRepository : BaseRepository<Owner, UUID> {
    fun findByTelephone(telephone: String): Mono<Owner>
}

@Component("ownerRepository")
class OwnerRepositoryImpl(val firestore: Firestore) : OwnerRepository {

    override fun findByTelephone(telephone: String): Mono<Owner> {
        val ownerCollection = firestore.collection("owner")
        val querySnapshot = ownerCollection.whereEqualTo("telephone", telephone).get()
        return Mono.create<QuerySnapshot> { monoSink -> ApiFutures.addCallback(querySnapshot, ApiFutureQuerySnapshotCallbackImpl(monoSink)) }
                .filter { it.documents.size > 0 }
                .map { it.documents[0] }
                .map { doc ->
                    Owner(UUID.fromString(doc.id), doc["last_name"] as String, doc["first_name"] as String, doc["telephone"] as String,
                            doc["address"] as String, doc["city"] as String)
                }
    }

    override fun save(owner: Owner): Mono<Owner> {

        val ownerWithId = if (!owner.isNew()) owner else owner.copy(id = UUID.randomUUID())

        val document = firestore.collection("owner").document(ownerWithId.id!!.toString())
        val data = mutableMapOf<String, Any?>()
        data["owner_id"] = ownerWithId.id
        data["first_name"] = ownerWithId.firstName
        data["last_name"] = ownerWithId.lastName
        data["telephone"] = ownerWithId.telephone
        data["address"] = ownerWithId.address
        data["city"] = ownerWithId.city
        val resultFuture: ApiFuture<WriteResult> = document.set(data)

        return Mono.create<WriteResult> { sink -> ApiFutures.addCallback(resultFuture, ApiFutureCallbackImpl(sink)) }
                .map { ownerWithId }
    }

    override fun save(owners: List<Owner>): Flux<Owner> {
        val savedOwners = owners.map { save(it) }
        return Flux.mergeSequential(savedOwners)
    }

    override fun findById(id: UUID): Mono<Owner> {
        val documentFuture = firestore.collection("owner").document(id.toString()).get()
        return Mono.create<DocumentSnapshot> { monoSink -> ApiFutures.addCallback(documentFuture, ApiFutureDocumentSnapshotCallbackImpl(monoSink)) }
                .map { doc ->
                    Owner(UUID.fromString(doc.id), doc["last_name"] as String, doc["first_name"] as String, doc["telephone"] as String,
                            doc["address"] as String, doc["city"] as String)
                }
    }

    override fun findAll(): Flux<Owner> {
        val querySnapshot = firestore.collection("owner").get()

        return Mono.create<QuerySnapshot> { monoSink -> ApiFutures.addCallback(querySnapshot, ApiFutureQuerySnapshotCallbackImpl(monoSink)) }
                .filter { it.documents.size > 0 }
                .map { it.documents }
                .flatMapIterable { it }
                .map { doc ->
                    Owner(UUID.fromString(doc.id), doc["last_name"] as String, doc["first_name"] as String, doc["telephone"] as String,
                            doc["address"] as String, doc["city"] as String)
                }
    }
}
