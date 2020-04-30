package com.tgt.petclinic.petclinic

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "vet")
class Vet(override @Id var id: String?,
          firstName: String,
          lastName: String,
          specialties: Set<Specialty>
) : Person(id, firstName, lastName)