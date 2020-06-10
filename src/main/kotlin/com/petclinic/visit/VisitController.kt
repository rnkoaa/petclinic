package com.petclinic.visit

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.petclinic.common.adapter.NotFoundException
import com.petclinic.owner.adapter.PetResponse
import com.petclinic.owner.model.Owner
import com.petclinic.owner.model.OwnerResponse
import com.petclinic.owner.model.Pet
import com.petclinic.owner.service.OwnerService
import com.petclinic.owner.service.PetService
import com.petclinic.vet.model.Vet
import com.petclinic.vet.service.VetService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Instant
import java.time.*
import java.time.LocalDate
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/petclinic/v1")
class VisitController(val visitService: VisitService,
                      val petService: PetService,
                      val ownerService: OwnerService,
                      val vetService: VetService
) {
    @GetMapping(value = ["visits", "visits/"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun findVisits(): Flux<Visit> {
//    fun findVisits(): Flux<VisitResponse> {

        return visitService.findAll()
//                .map { o -> createVisitResponse(o) }
    }

    @PostMapping(value = ["visits", "visits/"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType
            .APPLICATION_JSON_VALUE])
    fun createVisit(@Valid @RequestBody requestBody: VisitRequest): Mono<CreateVisitResponse> {
        val ownerMaybe = ownerService.find(requestBody.owner)
                .switchIfEmpty(Mono.error(NotFoundException("owner with id ${requestBody.owner} does not exist")))
        val petMaybe = petService.find(requestBody.pet)
                .switchIfEmpty(Mono.error(NotFoundException("owner with id ${requestBody.owner} does not exist")))
//        val vetMaybe = vetService.find(requestBody.vet)
//                .switchIfEmpty(Mono.error(NotFoundException("vet with id ${requestBody.vet} does not exist")))

        // combine all these sources if they are all available to create a visit.
        return Mono.zip(petMaybe, ownerMaybe/*, vetMaybe*/)
                .map { t ->
                    val pet = t.t1
                    val owner = t.t2
//                    val vet = t.t3

                    val id = requestBody.id ?: UUID.randomUUID()
                    val visitDate = requestBody.date ?: LocalDate.now()
                    val instant = visitDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
                    val visit = Visit(id, instant, pet.id!!, owner.id!!, null, requestBody.description)
                    visit
                }
                .flatMap { visitService.save(it) }
                .map { CreateVisitResponse(it.id!!, it.date, it.petId, it.ownerId, it.vetId, it.description) }

    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy::class)
class CreateVisitResponse(val id: UUID,
                          val date: Instant,
                          val pet: UUID,
                          val owner: UUID,
                          val vet: UUID?,
                          val description: String
) {

}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy::class)
class VisitResponse(val id: UUID,
                    val date: Instant,
                    val pet: PetResponse,
                    val owner: OwnerResponse,
                    val vet: PetResponse,
                    val description: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy::class)
class VisitRequest(val id: UUID?,
                   val date: LocalDate?,
                   val pet: UUID,
                   val owner: UUID,
                   val vet: UUID?,
                   val description: String
)

