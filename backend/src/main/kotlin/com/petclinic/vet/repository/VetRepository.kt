package com.petclinic.vet.repository

import com.petclinic.common.repository.BaseRepository
import com.petclinic.vet.model.Vet
import com.petclinic.vet.model.VetByTelephone
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface VetRepository : BaseRepository<Vet, UUID> {
    fun findByTelephone(telephone: String): Mono<VetByTelephone>
}

@Component("vetRepository")
class VetRepositoryImpl : VetRepository {
    override fun findByTelephone(telephone: String): Mono<VetByTelephone> {
        TODO("Not yet implemented")
    }

    override fun save(item: Vet): Mono<Vet> {
        TODO("Not yet implemented")
    }

    override fun save(item: List<Vet>): Flux<Vet> {
        TODO("Not yet implemented")
    }

    override fun findById(id: UUID): Mono<Vet> {
        TODO("Not yet implemented")
    }

    override fun findAll(): Flux<Vet> {
        TODO("Not yet implemented")
    }

}