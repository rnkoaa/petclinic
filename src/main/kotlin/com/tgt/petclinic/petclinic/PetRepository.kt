package com.tgt.petclinic.petclinic

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface PetRepository : ReactiveMongoRepository<Pet, String> {
}