package com.petclinic.owner.adapter

import com.petclinic.common.adapter.NotFoundException
import com.petclinic.owner.model.CreateOwnerRequest
import com.petclinic.owner.model.Owner
import com.petclinic.owner.model.OwnerResponse
import com.petclinic.owner.service.OwnerService
import com.petclinic.owner.service.PetService
import io.swagger.v3.oas.annotations.Operation
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/petclinic/v1")
class OwnerController(val ownerService: OwnerService, val petService: PetService) {
    companion object {
        private val logger = LoggerFactory.getLogger("ProductController")
    }

    @Operation(description = "Creates an owner in the platform if the owner does not exist. The required" +
            "unique key is a telephone. No two owners will have the same phonenumber.",
            summary = "create an owner")
    @PostMapping(value = ["owner", "/owner"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: CreateOwnerRequest): Mono<OwnerResponse> {
        val owner = createOwner(request)
        return ownerService.save(owner)
                .map { res -> createOwnerResponse(res) }
    }

    @Operation(description = "Creates a list of owners in the platform if the owner does not exist. The required" +
            "unique key is a telephone. No two owners will have the same phonenumber.",
            summary = "create an owner")
    @PostMapping(value = ["owners", "/owners"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType
            .APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun bulkCreate(@Valid @RequestBody request: List<CreateOwnerRequest>): Flux<OwnerResponse> {

        val owners = request.map { req -> createOwner(req) }
        return Flux.fromIterable(owners)
                .flatMap { owner -> ownerService.save(owner) }
                .map { res -> createOwnerResponse(res) }
    }


    @Operation(description = "returns all owners that are available in the system",
            summary = "find all owners")
    @GetMapping(value = ["owners", "owners/"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun findOwners(): Flux<OwnerResponse> {
        return ownerService.findAll()
                .map { o -> createOwnerResponse(o) }
    }

    @Operation(description = "find an owner by the telephone",
            summary = "find owner by telephone")
    @GetMapping(value = ["owner", "owner/"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType
            .APPLICATION_JSON_VALUE])
    fun findOwner(@RequestParam("telephone") telephone: String?): Mono<OwnerResponse> {
        if (telephone != null) {
            return ownerService.findByTelephone(telephone)
                    .map { res -> createOwnerResponse(res) }
        }
        return Mono.empty()
    }

    @Operation(description = "find owner by id",
            summary = "find owner by id")
    @GetMapping(value = ["owner/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType
            .APPLICATION_JSON_VALUE])
    fun findOwnerById(@PathVariable("id") id: String, @RequestParam("include_pets") includePets: Boolean?): Mono<OwnerResponse> {
        var includePetB = false
        includePets?.let {
            includePetB = it
        }

        val maybeOwner = ownerService.findById(id)
                .switchIfEmpty(Mono.error(NotFoundException("owner with id $id does not exist")))

        // should the we include the pets in the
        if (includePetB) {
            val pets = petService.findByOwner(id)
                    .collectList()
                    .map { it.toSet() }

            // add the pets to the owner before responding the client
            return maybeOwner
                    .zipWith(pets)
                    .map {
                        val owner = it.t1
                        val ownerPets = it.t2
                        val ownerCopy = owner.copy(pets = ownerPets)
                        val ownerResponse = createOwnerResponse(ownerCopy)
                        val petRes = ownerResponse.pets
                                .map { p -> p.copy(ownerId = null) }
                                .toSet()
                        ownerResponse.copy(pets = petRes)
                    }
        }
        return maybeOwner.map { res -> createOwnerResponse(res) }
    }

    private fun createOwner(request: CreateOwnerRequest): Owner {
        return Owner(request.id, request.lastName, request.firstName, request.telephone, request.address, request.city)
    }

    private fun createOwnerResponse(owner: Owner): OwnerResponse {
        if (owner.pets.isNotEmpty()) {
            val petResponses = owner.pets
                    .map { pet -> PetResponse(pet) }
                    .toSet()
            return OwnerResponse(owner, petResponses)
        }
        return OwnerResponse(owner)
    }
}