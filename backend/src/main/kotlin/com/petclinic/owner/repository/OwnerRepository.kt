package com.petclinic.owner.repository

import com.petclinic.common.repository.BaseRepository
import com.petclinic.owner.model.Owner
import com.petclinic.owner.model.OwnerByTelephone
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface OwnerRepository : BaseRepository<Owner, UUID> {
    fun findByTelephone(telephone: String): Mono<OwnerByTelephone>
}

@Component("ownerRepository")
class OwnerRepositoryImpl : OwnerRepository {
    override fun findByTelephone(telephone: String): Mono<OwnerByTelephone> {
        TODO("Not yet implemented")
    }

    override fun save(item: Owner): Mono<Owner> {
        TODO("Not yet implemented")
    }

    override fun save(item: List<Owner>): Flux<Owner> {
        TODO("Not yet implemented")
    }

    override fun findById(id: UUID): Mono<Owner> {
        TODO("Not yet implemented")
    }

    override fun findAll(): Flux<Owner> {
        TODO("Not yet implemented")
    }

}
