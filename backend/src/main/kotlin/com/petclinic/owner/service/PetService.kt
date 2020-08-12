package com.petclinic.owner.service

import com.petclinic.owner.model.Pet
import com.petclinic.owner.repository.PetRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface PetService {
    fun findByOwner(ownerId: String): Flux<Pet>
//    fun findAll(own): Flux<Pet>
    fun findByOwnerId(ownerId: UUID): Flux<Pet>
    fun save(pet: Pet): Mono<Pet>
    fun find(ownerId: UUID, id: UUID): Mono<Pet>
}

@Service
class PetServiceImpl(val petRepository: PetRepository) : PetService {
    override fun findByOwner(ownerId: String): Flux<Pet> {
        return petRepository.findByOwnerId(UUID.fromString(ownerId))
    }

//    override fun findAll(): Flux<Pet> {
//        return petRepository.findAll()
//    }

    override fun findByOwnerId(ownerId: UUID): Flux<Pet> {
        return petRepository.findByOwnerId(ownerId)
    }

    override fun save(pet: Pet): Mono<Pet> {
        return petRepository.save(pet.ownerId, pet)
    }

    override fun find(ownerId: UUID, id: UUID): Mono<Pet> {
        return petRepository.findById(ownerId, id)
    }
}