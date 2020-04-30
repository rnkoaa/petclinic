package com.tgt.petclinic.petclinic

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface OwnerRepository : ReactiveMongoRepository<Owner, String> {

}