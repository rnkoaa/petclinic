package com.petclinic.owner

import org.springframework.dao.DuplicateKeyException
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

        val ownerExists = telephoneExists(owner.telephone)
        return ownerExists.flatMap { exists ->
            if (exists) {
                Mono.error<Throwable>(DuplicateKeyException("an owner exists"))
            }
            saveOwner(owner)
        }

//        return Mono.empty()
    }

    private fun saveOwner(owner: Owner): Mono<Owner> {
        return ownerByTelephoneRepository.save(OwnerByTelephone(owner))
                .then(ownerRepository.save(owner))
    }

    override fun findByTelephone(telephone: String): Mono<Owner> {
        return ownerByTelephoneRepository.findByTelephone(telephone)
                .map { o -> Owner(o) }
    }

    override fun findById(id: String): Mono<Owner> {
        return ownerRepository.findById(UUID.fromString(id))
    }

    fun telephoneExists(telephone: String): Mono<Boolean> {
        return ownerByTelephoneRepository.findByTelephone(telephone)
                .map { true }
                .switchIfEmpty(Mono.just(false))
    }

}