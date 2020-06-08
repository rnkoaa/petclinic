package com.petclinic.owner

import org.springframework.data.cassandra.repository.Query
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import reactor.core.publisher.Mono
import java.util.*

interface OwnerRepository : ReactiveCassandraRepository<Owner, UUID> {

}

interface OwnerByTelephoneRepository : ReactiveCassandraRepository<OwnerByTelephone, OwnerByTelephoneKey> {

    @Query("select * from owner_by_telephone where telephone = ?0")
    fun findByTelephone(telephone: String): Mono<OwnerByTelephone>
}