package com.petclinic.owner.service


import com.petclinic.owner.model.Pet
import com.petclinic.owner.model.PetByOwner
import com.petclinic.owner.repository.PetByOwnerRepository
import com.petclinic.owner.repository.PetRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface PetService {
    fun findByOwner(ownerId: String): Flux<Pet>
    fun findAll(): Flux<Pet>
    fun findByOwnerId(ownerId: UUID): Flux<Pet>
    fun save(pet: Pet): Mono<Pet>
    fun find(id: UUID): Mono<Pet>
}

@Service
class PetServiceImpl(val petRepository: PetRepository, val petByOwnerRepository: PetByOwnerRepository) : PetService {
    override fun findByOwner(ownerId: String): Flux<Pet> {
        return petByOwnerRepository.findByOwnerId(UUID.fromString(ownerId))
                .map { p -> Pet(p) }
    }

    override fun findAll(): Flux<Pet> {
        return petRepository.findAll()
    }

    override fun findByOwnerId(ownerId: UUID): Flux<Pet> {
        return petByOwnerRepository.findByOwnerId(ownerId)
                .map { p -> Pet(p) }
    }

    override fun save(pet: Pet): Mono<Pet> {
        return petByOwnerRepository.save(PetByOwner(pet))
                .flatMap {
                    petRepository.save(pet)
                }
    }

    override fun find(id: UUID): Mono<Pet> {
        return petRepository.findById(id)
    }
}