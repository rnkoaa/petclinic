package com.petclinic.vet

import com.petclinic.owner.model.OwnerByTelephone
import com.petclinic.vet.model.Specialty
import com.petclinic.vet.model.SpecialtyByName
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface SpecialtyService {
    fun findAll(): Flux<Specialty>
     fun save(specialty: Specialty): Mono<Specialty>

}

@Service
class SpecialtyServiceImpl(val specialtyRepository: SpecialtyRepository,
val specialtyByNameRepository: SpecialtyByNameRepository) : SpecialtyService {
    override fun findAll(): Flux<Specialty> {
        return specialtyRepository.findAll()
    }

    override fun save(specialty: Specialty): Mono<Specialty> {
        return Mono.just(specialty)
                // check to see if an owner with the same telephone exists. if it true,
                // turn the operation on its head so that it will be filtered which will result
                // in a duplicateKeyException
                .filterWhen { o ->
                    exists(o.name).map { e -> !e }
                }
                .switchIfEmpty(Mono.error(DuplicateKeyException("There is a specialty with the same name ${specialty.name}")))

                // if it does not exist, save this owner.
                .flatMap { saveSpecialty(specialty) }
    }

    fun saveSpecialty(specialty: Specialty): Mono<Specialty> {
        // assign id if there is no id for this user.
        val toSave = if (!specialty.isNew()) specialty else specialty.copy(id = UUID.randomUUID())

        return specialtyByNameRepository.save(SpecialtyByName(toSave))
                .then(specialtyRepository.save(toSave))
    }

    fun exists(name: String): Mono<Boolean> {
       return specialtyByNameRepository.findByName(name)
               .map {
                   true
               }
               .switchIfEmpty(Mono.just(false))
    }

}
