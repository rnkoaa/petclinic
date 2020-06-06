package com.petclinic.vet

import com.petclinic.common.model.Person
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table(value = "vet")
class Vet(@PrimaryKey override var id: String?,
          firstName: String,
          lastName: String,
          var specialties: Set<Specialty>
) : Person(id, firstName, lastName)