package com.petclinic.owner.service

import com.petclinic.owner.model.Owner
import com.petclinic.common.adapter.DuplicateKeyException
import com.petclinic.owner.repository.OwnerRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface OwnerService {
    fun findAll(): Flux<Owner>
    fun save(owner: Owner): Mono<Owner>
    fun findByTelephone(telephone: String): Mono<Owner>
    fun findById(id: String): Mono<Owner>
    fun find(id: UUID): Mono<Owner>
    fun exists(id: String): Mono<Boolean>
}

@Service
class OwnerServiceImpl(val ownerRepository: OwnerRepository/*, val ownerIndexClient: OwnerIndexClient*/) : OwnerService {

    override fun findAll(): Flux<Owner> {
        return ownerRepository.findAll()
    }

    override fun exists(id: String): Mono<Boolean> {
        return ownerRepository.findById(UUID.fromString(id))
                .map {
                    true
                }
                .switchIfEmpty(Mono.just(false))
    }

    override fun save(owner: Owner): Mono<Owner> {
        return Mono.just(owner)
                // check to see if an owner with the same telephone exists. if it true,
                // turn the operation on its head so that it will be filtered which will result
                // in a duplicateKeyException
                .filterWhen { o ->
                    telephoneExists(o.telephone).map { e -> !e }
                }
                .switchIfEmpty(Mono.error(DuplicateKeyException("There is an owner with the same telephone " + owner.telephone)))

                // if it does not exist, save this owner.
                .flatMap { saveOwner(owner) }
//                .map { o ->
//                    val ownerRes = ownerIndexClient.indexOwner(o)
//                    if (ownerRes.status > 0) {
//                        println("error index owner: ${ownerRes.message}")
//                    } else {
//                        println("successfully indexed owner")
//                    }
//
//                    o
//                }
    }

    private fun saveOwner(owner: Owner): Mono<Owner> {
        // assign id if there is no id for this user.
        val toSave = if (!owner.isNew()) owner else owner.copy(id = UUID.randomUUID())

//        return ownerByTelephoneRepository.save(OwnerByTelephone(toSave))
//                .then()
        return ownerRepository.save(toSave)
    }

    override fun findByTelephone(telephone: String): Mono<Owner> {
        return ownerRepository.findByTelephone(telephone)
//                .map { o -> Owner(o) }
    }

    override fun findById(id: String): Mono<Owner> {
        return ownerRepository.findById(UUID.fromString(id))
    }

    override fun find(id: UUID): Mono<Owner> {
        return ownerRepository.findById(id)
    }

    /**
     * Returns true if a telephone already exists by querying the telephone table
     */
    fun telephoneExists(telephone: String): Mono<Boolean> {
        return ownerRepository.findByTelephone(telephone)
                .map {
                    true
                }
                .switchIfEmpty(Mono.just(false))


    }

}