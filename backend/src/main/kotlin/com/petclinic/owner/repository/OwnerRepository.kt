package com.petclinic.owner.repository


import com.petclinic.owner.model.Owner
import com.petclinic.owner.model.OwnerByTelephone
import com.petclinic.owner.model.OwnerByTelephoneKey
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
