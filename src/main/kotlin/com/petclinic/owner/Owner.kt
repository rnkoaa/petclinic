package com.petclinic.owner

import com.petclinic.common.model.Person
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table(value = "owner")
data class Owner(@PrimaryKey override var id: String?,
                 var lastName: String,
                 var firstName: String,
                 var address: String = "",
                 var city: String = "",
                 var telephone: String = "",
                 var pets: Set<Pet>

) : Person(id, firstName, lastName)