package com.petclinic.owner.model

import com.petclinic.common.model.NamedEntity
import com.petclinic.visit.Visit
import java.time.LocalDate
import java.util.*

data class PetType(val name: String)

data class Pet(override var id: UUID?,
               val ownerId: UUID,
               var birthDate: LocalDate,
               var type: PetType,
               override var name: String,
               @Transient
               var visits: Set<Visit>
) : NamedEntity(id, name) {

    constructor(id: UUID?,
                ownerId: UUID,
                birthDate: LocalDate,
                type: PetType,
                name: String) : this(id, ownerId, birthDate, type, name, setOf())

    constructor(petByOwner: PetByOwner) : this(petByOwner.petByOwnerKey.petId, petByOwner.petByOwnerKey.ownerId, petByOwner.birthDate,
            petByOwner.type, petByOwner.name, petByOwner.visits)
}

data class PetByOwnerKey(
        val ownerId: UUID,
        val petId: UUID
)

data class PetByOwner(
        val petByOwnerKey: PetByOwnerKey,
        var birthDate: LocalDate,
        var type: PetType,
        var name: String,
        @Transient
        var visits: Set<Visit>
) {
    constructor(petByOwnerKey: PetByOwnerKey,
                birthDate: LocalDate,
                type: PetType,
                name: String) : this(petByOwnerKey, birthDate, type, name, setOf())

    constructor(pet: Pet) : this(PetByOwnerKey(pet.ownerId, pet.id!!), pet.birthDate, pet.type, pet.name, pet.visits)
}