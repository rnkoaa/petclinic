package com.tgt.petclinic.owner

import com.tgt.petclinic.common.model.Person
import com.tgt.petclinic.petclinic.Pet
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "owner")
data class Owner(@Id override var id: String?,
                 var lastName: String,
                 var firstName: String,
                 var address: String = "",
                 var city: String = "",
                 var telephone: String = "",
                 var pets: Set<Pet>

) : Person(id, firstName, lastName)