package com.clinic.pet.petclinic.owner

import com.clinic.pet.petclinic.common.Person
import com.clinic.pet.petclinic.pet.Pet

data class Owner(override var id: Int?,
                 override var lastName: String?,
                 override var firstName: String?,
                 var address: String?,
                 var telephone: String?,
                 var pets: Set<Pet>) : Person(id, lastName, firstName) {
}