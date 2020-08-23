package com.petclinic.vet.model

import com.petclinic.common.model.NamedEntity
import java.util.*

data class Specialty(override var id: UUID?, override var name: String) : NamedEntity(id, name) {
    constructor(specialtyByName: SpecialtyByName) : this(specialtyByName.specialtyByNameKey.id, specialtyByName.specialtyByNameKey.name)
}

data class SpecialtyByNameKey(val name: String,
                              val id: UUID)

data class SpecialtyByName(val specialtyByNameKey: SpecialtyByNameKey) {

    constructor(specialty: Specialty) : this(SpecialtyByNameKey(specialty.name, specialty.id!!))

    constructor(id: UUID, name: String) : this(SpecialtyByNameKey(name, id))
}