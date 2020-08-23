package com.petclinic.vet.service

import com.petclinic.common.adapter.DuplicateKeyException
import com.petclinic.vet.model.Vet
import com.petclinic.vet.repository.VetRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface VetService {
    fun findAll(): Flux<Vet>
    fun save(vet: Vet): Mono<Vet>
    fun findById(id: String): Mono<Vet>
    fun find(id: UUID): Mono<Vet>
    fun exists(name: String): Mono<Boolean>
    fun update(vet: Vet): Mono<Vet>
}

@Service
class VetServiceImpl(val vetRepository: VetRepository) : VetService {
    override fun findAll(): Flux<Vet> {
        return vetRepository.findAll()
    }

    override fun save(vet: Vet): Mono<Vet> {
        // assign id if there is no id for this user.
        return Mono.just(vet)
                // check to see if an owner with the same telephone exists. if it true,
                // turn the operation on its head so that it will be filtered which will result
                // in a duplicateKeyException
                .filterWhen { o ->
                    exists(o.telephone).map { e -> !e }
                }
                .switchIfEmpty(Mono.error(DuplicateKeyException("There is a vet with the same telephone ${vet.telephone}")))

                // if it does not exist, save this owner.
                .flatMap { saveVetInternal(vet) }
    }

    override fun findById(id: String): Mono<Vet> {
        return vetRepository.findById(UUID.fromString(id))
    }

    override fun find(id: UUID): Mono<Vet> {
       return vetRepository.findById(id)
    }

    fun saveVetInternal(vet: Vet): Mono<Vet> {
        // assign id if there is no id for this user.
        val toSave = if (!vet.isNew()) vet else vet.copy(id = UUID.randomUUID())

//        return vetByTelephoneRepository.save(VetByTelephone(toSave))
//                .then()
        return vetRepository.save(toSave)
    }

    override fun exists(name: String): Mono<Boolean> {
        return vetRepository.findByTelephone(name)
                .map {
                    true
                }
                .switchIfEmpty(Mono.just(false))
    }

    override fun update(vet: Vet): Mono<Vet> {
        return vetRepository.save(vet)
    }

}
