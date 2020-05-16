package com.tgt.petclinic.vet

import com.tgt.petclinic.common.model.Person
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "vet")
class Vet(@Id override var id: String?,
          firstName: String,
          lastName: String,
          var specialties: Set<Specialty>
) : Person(id, firstName, lastName)