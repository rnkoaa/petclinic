package com.petclinic.vet

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.petclinic.vet.model.Vet
import com.petclinic.vet.model.VetSpecialty
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/petclinic/v1")
class VetController(val vetService: VetService, val specialtyService: SpecialtyService) {

    @GetMapping("/vets", produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType
            .APPLICATION_JSON_VALUE])
    fun findAll(): Flux<VetResponse> {
        return vetService.findAll()
                .map { createVetResponse(it) }
    }

    @PostMapping("/vet", produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType
            .APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createVet(@Valid @RequestBody request: CreateVetRequest): Mono<VetResponse> {
        val requestMaybe = createVetFromRequest(request)
        return requestMaybe
                .flatMap { vetService.save(it) }
                .map { createVetResponse(it) }
    }

    fun createVetFromRequest(request: CreateVetRequest): Mono<Vet> {
        if (request.specialties.isEmpty()) {
            val vet = Vet(request.id, request.lastName, request.firstName, request.telephone, request.email, setOf())
            return Mono.just(vet)
        }

        val vetSpecialties = Flux.fromIterable(request.specialties)
                .flatMap {
                    specialtyService.findByName(it)
                }
                .map { s ->
                    VetSpecialty(s.id, s.name)
                }
                .collectList()
                .map { it.toSet() }

        return Mono.just(request)
                .zipWith(vetSpecialties)
                .map {
                    val req = it.t1
                    val specialties = it.t2
                    Vet(req.id,
                            req.lastName,
                            req.firstName,
                            req.telephone, req.email,
                            specialties)
                }
    }

    fun createVetResponse(vet: Vet): VetResponse {
        if (vet.specialties.isNotEmpty()) {
            val specialtyResponses = vet.specialties
                    .map {
                        SpecialtyResponse(it.id!!, it.name)
                    }
                    .toSet()
            return VetResponse(vet, specialtyResponses)
        }
        return VetResponse(vet)
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy::class)
data class CreateVetRequest(val id: UUID?,
                            val firstName: String,
                            val lastName: String,
                            val telephone: String,
                            val email: String = "",
                            val specialties: Set<String> = setOf()) {
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy::class)
data class VetResponse(val id: UUID,
                       val firstName: String,
                       val lastName: String,
                       val specialties: Set<SpecialtyResponse> = setOf()) {
    constructor(vet: Vet) : this(vet.id!!, vet.firstName, vet.lastName)
    constructor(vet: Vet, specialResponses: Set<SpecialtyResponse>) : this(vet.id!!, vet.firstName, vet.lastName, specialResponses)
}