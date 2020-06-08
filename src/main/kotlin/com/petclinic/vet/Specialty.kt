package com.petclinic.vet

import com.petclinic.common.model.NamedEntity
import org.springframework.data.cassandra.core.mapping.Table
import java.util.*

//@Table(value = "specialty")
class Specialty(override var id: UUID?, name: String) : NamedEntity(id, name)