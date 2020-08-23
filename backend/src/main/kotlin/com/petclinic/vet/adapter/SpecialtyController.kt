package com.petclinic.vet.adapter

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.petclinic.vet.model.Specialty
import com.petclinic.vet.service.SpecialtyService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import javax.validation.Valid

fun createSpecialtyFromRequest(specialtyRequest: SpecialtyRequest): Specialty {
    if (specialtyRequest.id != null) {
        return Specialty(specialtyRequest.id, specialtyRequest.name)
    }
    return Specialty(UUID.randomUUID(), specialtyRequest.name)
}

fun createSpecialtyResponse(specialty: Specialty): SpecialtyResponse {
    return SpecialtyResponse(specialty.id!!, specialty.name)
}

@RestController
@RequestMapping("/petclinic/v1")
class SpecialtyController(val specialtyService: SpecialtyService) {

    @Operation(summary = "find all specialties", description = "find all available specialties available.")
    @GetMapping(value = ["/specialties"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType
            .APPLICATION_JSON_VALUE])
    fun findAll(): Flux<SpecialtyResponse> {
        return specialtyService.findAll()
                .map { createSpecialtyResponse(it) }
    }

    @Operation(summary = "create a specialty", description = "create a specialty if does not exist.")
    @PostMapping(value = ["/specialty"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType
            .APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createSpecialty(@Valid @RequestBody specialtyRequest: SpecialtyRequest): Mono<SpecialtyResponse> {
        val specialty = createSpecialtyFromRequest(specialtyRequest)
        return specialtyService.save(specialty)
                .map { createSpecialtyResponse(it) }
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
data class SpecialtyRequest(val id: UUID?, val name: String) {

}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
data class SpecialtyResponse(val id: UUID, val name: String) {

}