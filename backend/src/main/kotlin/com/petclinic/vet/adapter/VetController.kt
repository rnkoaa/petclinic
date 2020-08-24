package com.petclinic.vet.adapter


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.petclinic.common.adapter.NotFoundException
import com.petclinic.vet.model.Vet
import com.petclinic.vet.model.VetSpecialty
import com.petclinic.vet.service.SpecialtyService
import com.petclinic.vet.service.VetService
import io.swagger.v3.oas.annotations.Operation
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

    @Operation(summary = "find all vets", description = "find all vets available without any filters")
    @GetMapping("/vets", produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType
            .APPLICATION_JSON_VALUE])
    fun findAll(): Flux<VetResponse> {
        return vetService.findAll()
                .map { createVetResponse(it) }
    }

    @Operation(summary = "find a vet by id", description = "find vet by id.")
    @GetMapping("/vets/{id}", produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType
            .APPLICATION_JSON_VALUE])
    fun findById(@PathVariable("id") id: String): Mono<VetResponse> {
        return vetService.findById(id)
                .switchIfEmpty(Mono.error(NotFoundException("There is no vet with the id $id")))
                .map { createVetResponse(it) }
    }

    @Operation(summary = "add a specialty to a vet", description = "add a specialty to vet if the vet does not have " +
            "the specialty")
    @PutMapping("/vets/{id}/specialty", produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType
            .APPLICATION_JSON_VALUE])
    fun addSpecialtyToVet(@PathVariable("id") id: String,
                          @Valid @RequestBody specialtyRequest: SpecialtyRequest): Mono<VetResponse> {
        val specialty = createSpecialtyFromRequest(specialtyRequest)
        val specialtyMono = if (specialty.isNew()) specialtyService.save(specialty) else Mono.just(specialty)

        return Mono.just(id)
                .filterWhen { item ->
                    vetExists(item) //.map { e -> e }
                }
                .switchIfEmpty(Mono.error(NotFoundException("There is no vet with id $id")))
                .flatMap { vetService.findById(id) }
                .zipWith(specialtyMono)
                .map {
                    val vet = it.t1
                    val vSpecialty = it.t2
                    vet.copy(specialties = vet.specialties + VetSpecialty(vSpecialty.id, vSpecialty.name))
                }
                .flatMap { vetService.update(it) }
                .map { createVetResponse(it) }
    }
    // fun findById
    // fun add Specialty By Id

    @Operation(summary = "create a vet", description = "create a new vet in the system")
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

    fun vetExists(id: String): Mono<Boolean> {
        return vetService.findById(id).map { true }.switchIfEmpty(Mono.just(false))
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