package com.petclinic.vet.model

import com.petclinic.common.model.NamedEntity
import com.petclinic.common.model.Person
import java.util.*

data class VetSpecialty(override var id: UUID?, override var name: String) : NamedEntity(id, name) {
    companion object {
        fun from(specialty: Specialty): VetSpecialty {
            return VetSpecialty(specialty.id, specialty.name)
        }
    }
}

data class Vet(override var id: UUID?,
               override var lastName: String,
               override var firstName: String,
               var telephone: String = "",
               var email: String = "",
               var specialties: Set<VetSpecialty>
) : Person(id, firstName, lastName)

data class VetByTelephoneKey(var telephone: String,
                             var firstName: String,
                             var lastName: String
)

data class VetByTelephone(val vetByTelephoneKey: VetByTelephoneKey,
                          val email: String = "",
                          var id: UUID?
) {
    constructor(vet: Vet) : this(VetByTelephoneKey(vet.telephone, vet.lastName, vet.firstName), vet.email, vet.id)
}