package com.petclinic.owner

import com.petclinic.common.model.NamedEntity
import com.petclinic.visit.Visit
import org.springframework.data.annotation.Id
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.time.LocalDate

class PetType(@Id override var id: String?, name: String) : NamedEntity(id, name)

@Table(value = "pet")
class Pet(@PrimaryKey override var id: String?,
          var birthDate: LocalDate,
          var type: PetType,
          var visits: Set<Visit>,
          var name: String) : NamedEntity(id, name) {

}