package com.petclinic.owner

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.util.*

interface OwnerService {
    fun save(owner: Owner): Mono<Owner>
    fun findByTelephone(telephone: String): Mono<Owner>
    fun findById(id: String): Mono<Owner>
}

@Service
class OwnerServiceImpl(val ownerRepository: OwnerRepository,
                       val ownerByTelephoneRepository: OwnerByTelephoneRepository) : OwnerService {
    override fun save(owner: Owner): Mono<Owner> {
        val key = OwnerByTelephoneKey(owner.telephone, owner.firstName, owner.lastName)
        val foundByOwnerTelephone = ownerByTelephoneRepository.findById(key)

        return foundByOwnerTelephone.switchIfEmpty {
            Mono.just(OwnerByTelephone(owner))
        }.map { o -> Owner(o) }
    }

    override fun findByTelephone(telephone: String): Mono<Owner> {
        return ownerByTelephoneRepository.findByTelephone(telephone)
                .map { o -> Owner(o) }
    }

    override fun findById(id: String): Mono<Owner> {
        return ownerRepository.findById(UUID.fromString(id))
    }

}