package com.petclinic.vet

import com.petclinic.owner.model.OwnerByTelephone
import com.petclinic.vet.model.Vet
import com.petclinic.vet.model.VetByTelephone
import com.petclinic.vet.model.VetByTelephoneKey
import org.springframework.data.cassandra.repository.Query
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import reactor.core.publisher.Mono
import java.util.*

interface VetRepository : ReactiveCassandraRepository<Vet, UUID> {

}

interface VetByTelephoneRepository : ReactiveCassandraRepository<VetByTelephone, VetByTelephoneKey> {

    @Query("select * from vet_by_telephone where telephone = ?0")
    fun findByTelephone(telephone: String): Mono<OwnerByTelephone>
}