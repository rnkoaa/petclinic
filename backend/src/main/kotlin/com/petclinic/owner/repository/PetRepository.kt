package com.petclinic.owner.repository

import com.petclinic.common.repository.BaseRepository
import com.petclinic.owner.model.Pet
import com.petclinic.owner.model.PetByOwner
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface PetRepository : BaseRepository<Pet, UUID> {
    fun findByOwnerId(ownerId: UUID): Flux<PetByOwner>
}

@Component("petRepository")
class PetRepositoryImpl : PetRepository {
    override fun findByOwnerId(ownerId: UUID): Flux<PetByOwner> {
        TODO("Not yet implemented")
    }

    override fun save(item: Pet): Mono<Pet> {
        TODO("Not yet implemented")
    }

    override fun save(item: List<Pet>): Flux<Pet> {
        TODO("Not yet implemented")
    }

    override fun findById(id: UUID): Mono<Pet> {
        TODO("Not yet implemented")
    }

    override fun findAll(): Flux<Pet> {
        TODO("Not yet implemented")
    }

}

