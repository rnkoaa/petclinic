package com.petclinic.owner

import org.springframework.data.cassandra.repository.Query
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface PetRepository : ReactiveCassandraRepository<Pet, UUID> {
}

interface PetByOwnerRepository : ReactiveCassandraRepository<PetByOwner, PetByOwnerKey> {

    @Query("select * from pet_by_owner where owner_id = ?0")
    fun findByOwnerId(ownerId: UUID): Flux<PetByOwner>
}