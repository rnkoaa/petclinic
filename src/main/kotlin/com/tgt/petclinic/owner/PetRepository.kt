package com.tgt.petclinic.owner

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface PetRepository : ReactiveMongoRepository<Pet, String> {
}