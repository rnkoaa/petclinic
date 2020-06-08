package com.petclinic.owner

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.petclinic.common.adapter.NotFoundException
import com.petclinic.visit.Visit
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/petclinic/v1")
class PetController(val petService: PetService, val ownerService: OwnerService) {

    //    @Operation(hidden = true, summary = "Ignore this method from being publicly documented")
    @GetMapping(value = ["/owner/{id}/pets"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun findPetsByOwner(@PathVariable("id") id: String): Flux<PetResponse> {

        return petService.findByOwner(id)
                .map { o -> createPetResponse(o) }
    }

    @PostMapping(value = ["/owner/{id}/pet"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createPet(@RequestBody request: PetRequest, @PathVariable("id") id: String): Mono<PetResponse> {

        val requestItem = if (request.ownerId == null) request.copy(ownerId = UUID.fromString(id)) else request
        val pet = requestItem.toPet()

        return ownerService.findById(id)
                .switchIfEmpty(Mono.error(NotFoundException("owner with id $id does not exist")))
                .flatMap {
                    val toSave = pet.copy(id = UUID.randomUUID())
                    petService.save(toSave)
                            .map { createPetResponse(it) }
                }
    }

    fun createPetResponse(pet: Pet): PetResponse {
        return PetResponse(pet)
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy::class)
data class PetRequest(var id: UUID?,
                      var ownerId: UUID?,
                      val birthDate: LocalDate,
                      var type: PetType,
                      var name: String,
                      var visits: Set<Visit> = setOf()) {

    fun toPet(): Pet {
        return Pet(id, ownerId!!, birthDate, type, name, visits)
    }


}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy::class)
data class PetResponse(val id: String?,
                       val ownerId: String?,
                       val birthDate: LocalDate?,
                       var type: PetType,
                       var name: String,
                       var visits: Set<Visit> = setOf()) {
    constructor(pet: Pet) : this(pet.id?.toString(), pet.ownerId.toString(), pet.birthDate, pet.type, pet.name, pet.visits)
}