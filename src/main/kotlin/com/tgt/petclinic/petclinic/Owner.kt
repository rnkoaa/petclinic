package com.tgt.petclinic.petclinic

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "owner")
class Owner(override @Id var id: String?,
            var name: String,
            address: String,
            city: String,
            telephone: String,
            pets: Set<Pet>

) : Person(id, name)