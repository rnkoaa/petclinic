package com.petclinic.visit

import com.petclinic.common.repository.BaseRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*


interface VisitRepository : BaseRepository<Visit, UUID>  {
}

@Component
class VisitRepositoryImpl : VisitRepository {
    override fun save(item: Visit): Mono<Visit> {
        TODO("Not yet implemented")
    }

    override fun save(item: List<Visit>): Flux<Visit> {
        TODO("Not yet implemented")
    }

    override fun findById(id: UUID): Mono<Visit> {
        TODO("Not yet implemented")
    }

    override fun findAll(): Flux<Visit> {
        TODO("Not yet implemented")
    }

}