package com.petclinic.owner

import com.petclinic.common.adapter.InvalidProductIdException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/petclinic/v1")
class OwnerController(val ownerService: OwnerService, val petService: PetService) {
    companion object {
        private val logger = LoggerFactory.getLogger("ProductController")
    }

    //    @Operation(hidden = true, summary = "Ignore this method from being publicly documented")
    @PostMapping(value = ["owner", "/owner"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun create(/*@Valid*/ @RequestBody request: CreateOwnerRequest): Mono<OwnerResponse> {

        val owner = createOwner(request)

        return ownerService.save(owner)
                .map { res -> createOwnerResponse(res) }
    }

    @PostMapping(value = ["owners", "/owners"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType
            .APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun bulkCreate(/*@Valid*/ @RequestBody request: List<CreateOwnerRequest>): Flux<OwnerResponse> {

        val owners = request.map { req -> createOwner(req) }
        return Flux.fromIterable(owners)
                .flatMap { owner -> ownerService.save(owner) }
                .map { res -> createOwnerResponse(res) }
    }

    private fun createOwner(request: CreateOwnerRequest): Owner {
        return Owner(request.id, request.lastName, request.firstName, request.telephone, request.address, request.city)
    }

    private fun createOwnerResponse(owner: Owner): OwnerResponse {
        return OwnerResponse(owner)
    }


    //    @Operation(hidden = true, summary = "Ignore this method from being publicly documented")
    @GetMapping(value = ["owners", "owners/"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun findOwners(): Flux<OwnerResponse> {

        return ownerService.findAll()
                .map { o -> createOwnerResponse(o) }
    }

    //    @Operation(hidden = true, summary = "Ignore this method from being publicly documented")
    @GetMapping(value = ["owner", "owner/"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType
            .APPLICATION_JSON_VALUE])
    fun findOwner(@RequestParam("telephone") telephone: String?): Mono<OwnerResponse> {
        if (telephone != null) {
            return ownerService.findByTelephone(telephone)
                    .map { res -> createOwnerResponse(res) }
        }
        return Mono.empty()
    }

    //    @Operation(hidden = true, summary = "Ignore this method from being publicly documented")
    @GetMapping(value = ["owner/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType
            .APPLICATION_JSON_VALUE])
    fun findOwnerById(@PathVariable("id") id: String, @RequestParam("include_pets") includePets: Boolean?): Mono<OwnerResponse> {
        var includePetB = false
        includePets?.let {
            includePetB = it
        }
        if (includePetB) {
            val findByOwner = petService.findByOwner(id)
            val pets = findByOwner.toIterable().toList()
            print("Pets: ${pets.size}")
//            findByOwner.
        }
        return ownerService.findById(id).map { res -> createOwnerResponse(res) }
    }
}