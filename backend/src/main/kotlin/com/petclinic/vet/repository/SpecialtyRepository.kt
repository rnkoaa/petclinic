package com.petclinic.vet.repository

import com.petclinic.common.repository.BaseRepository
import com.petclinic.vet.model.Specialty
import com.petclinic.vet.model.VetSpecialty
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface SpecialtyRepository : BaseRepository<Specialty, UUID> {
    fun findByName(name: String): Mono<Specialty>
}

@Component("specialtyRepository")
class SpecialtyRepositoryImpl : SpecialtyRepository {
    override fun findByName(name: String): Mono<Specialty> {
        TODO("Not yet implemented")
    }

    override fun save(item: Specialty): Mono<Specialty> {
        TODO("Not yet implemented")
    }

    override fun save(item: List<Specialty>): Flux<Specialty> {
        TODO("Not yet implemented")
    }

    override fun findById(id: UUID): Mono<Specialty> {
        TODO("Not yet implemented")
    }

    override fun findAll(): Flux<Specialty> {
        TODO("Not yet implemented")
    }

}