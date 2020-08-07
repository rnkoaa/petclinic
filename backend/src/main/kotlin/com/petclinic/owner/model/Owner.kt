package com.petclinic.owner.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.petclinic.common.model.Person
import com.petclinic.owner.adapter.PetResponse
import java.util.*

data class Owner(override var id: UUID?,
                 override var lastName: String,
                 override var firstName: String,
                 var telephone: String = "",
                 var address: String? = "",
                 var city: String? = "",
                 var pets: Set<Pet> = setOf()
) : Person(id, firstName, lastName) {
    /**
     * Tell spring data to use this constructor because of transient issues
     */
    constructor(id: UUID?,
                lastName: String,
                firstName: String,
                telephone: String = "",
                address: String? = "",
                city: String? = "") : this(id, lastName, firstName, telephone, address, city, setOf())

    constructor(ownerByTelephone: OwnerByTelephone) :
            this(ownerByTelephone.id, ownerByTelephone.ownerByTelephoneKey.lastName,
                    ownerByTelephone.ownerByTelephoneKey.firstName,
                    ownerByTelephone.ownerByTelephoneKey.telephone,
                    ownerByTelephone.address, ownerByTelephone.city, setOf())
}

data class OwnerByTelephoneKey(
        var telephone: String,
        var firstName: String,
        var lastName: String
)

data class OwnerByTelephone(
        val ownerByTelephoneKey: OwnerByTelephoneKey,
        var address: String?,
        var city: String?,
        var id: UUID?
) {
    constructor(owner: Owner) : this(OwnerByTelephoneKey(owner.telephone, owner.lastName, owner.firstName),
            owner.address, owner.city, owner.id
    )
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy::class)
data class CreateOwnerRequest(
        var id: UUID?,
        val firstName: String,
        val lastName: String,
        val telephone: String,
        var address: String?,
        var city: String?)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy::class)
data class OwnerResponse(
        val id: UUID?,
        val firstName: String,
        val lastName: String,
        val telephone: String,
        var address: String?,
        var city: String?,
        var pets: Set<PetResponse> = setOf()) {

    constructor(owner: Owner) : this(owner.id,
            owner.firstName,
            owner.lastName,
            owner.telephone,
            owner.address,
            owner.city)

    constructor(owner: Owner, pets: Set<PetResponse>) : this(owner.id,
            owner.firstName,
            owner.lastName,
            owner.telephone,
            owner.address,
            owner.city,
            pets)
}