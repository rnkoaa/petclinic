package com.tgt.petclinic.visit

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface VisitRepository : ReactiveMongoRepository<Visit, String> {
}