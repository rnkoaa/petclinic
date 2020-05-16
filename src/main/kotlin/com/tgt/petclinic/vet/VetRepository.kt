package com.tgt.petclinic.vet

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface VetRepository : ReactiveMongoRepository<Vet, String> {
}