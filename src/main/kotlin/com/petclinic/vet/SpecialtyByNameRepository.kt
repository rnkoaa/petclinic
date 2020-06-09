package com.petclinic.vet

import com.petclinic.vet.model.SpecialtyByName
import com.petclinic.vet.model.SpecialtyByNameKey
import org.springframework.data.cassandra.repository.Query
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import reactor.core.publisher.Mono

interface SpecialtyByNameRepository : ReactiveCassandraRepository<SpecialtyByName, SpecialtyByNameKey> {

    @Query("select * from petclinic.specialty_by_name where name = ?0")
    fun findByName(name: String): Mono<SpecialtyByName>
}