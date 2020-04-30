package com.tgt.petclinic.petclinic

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

class PetType(override @Id var id: String?, name: String) : NamedEntity(id, name)

@Document(collection = "pet")
class Pet(override @Id var id: String?,
          birthDate: LocalDate,
          type: PetType,
          visits: Set<Visit>,
          name: String) : NamedEntity(id, name) {

}