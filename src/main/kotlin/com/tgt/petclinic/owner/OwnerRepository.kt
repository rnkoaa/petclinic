package com.tgt.petclinic.owner

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface OwnerRepository : ReactiveMongoRepository<Owner, String> {

}