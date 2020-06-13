package com.petclinic.visit

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface VisitService {
    fun findAll(): Flux<Visit>
    fun findById(id: String): Mono<Visit>
    fun save(visit: Visit): Mono<Visit>
}

@Service
class VisitServiceImpl(val visitRepository: VisitRepository) : VisitService {
    override fun findAll(): Flux<Visit> {
        return visitRepository.findAll()
    }

    override fun findById(id: String): Mono<Visit> {
        return visitRepository.findById(UUID.fromString(id))
    }

    override fun save(visit: Visit): Mono<Visit> {
        return visitRepository.save(visit)
    }

}