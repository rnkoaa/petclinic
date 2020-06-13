package com.petclinic.owner.repository

import com.petclinic.owner.model.Pet
import com.petclinic.owner.model.PetByOwner
import com.petclinic.owner.model.PetByOwnerKey
import org.springframework.data.cassandra.repository.Query
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import reactor.core.publisher.Flux
import java.util.*

interface PetRepository : ReactiveCassandraRepository<Pet, UUID> {
}

interface PetByOwnerRepository : ReactiveCassandraRepository<PetByOwner, PetByOwnerKey> {

    @Query("select * from pet_by_owner where owner_id = ?0")
    fun findByOwnerId(ownerId: UUID): Flux<PetByOwner>
}