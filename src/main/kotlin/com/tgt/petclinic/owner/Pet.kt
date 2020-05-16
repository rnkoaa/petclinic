package com.tgt.petclinic.owner

import com.tgt.petclinic.common.model.NamedEntity
import com.tgt.petclinic.petclinic.Visit
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

class PetType(@Id override var id: String?, name: String) : NamedEntity(id, name)

@Document(collection = "pet")
class Pet(@Id override var id: String?,
          var birthDate: LocalDate,
          var type: PetType,
          var visits: Set<Visit>,
          var name: String) : NamedEntity(id, name) {

}