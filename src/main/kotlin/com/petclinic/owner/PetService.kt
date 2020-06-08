package com.petclinic.owner

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface PetService {
    fun findByOwner(ownerId: String): Flux<Pet>
    fun save(pet: Pet): Mono<Pet>
}

@Service
class PetServiceImpl(val petRepository: PetRepository, val petByOwnerRepository: PetByOwnerRepository) : PetService {
    override fun findByOwner(ownerId: String): Flux<Pet> {
        return petByOwnerRepository.findByOwnerId(UUID.fromString(ownerId))
                .map { p -> Pet(p) }
    }

    override fun save(pet: Pet): Mono<Pet> {
        return petByOwnerRepository.save(PetByOwner(pet))
                .flatMap {
                    petRepository.save(pet)
                }
    }
}