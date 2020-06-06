package com.petclinic.vet

import com.petclinic.common.model.NamedEntity
import org.springframework.data.cassandra.core.mapping.Table

@Table(value = "specialty")
class Specialty(override var id: String?, name: String) : NamedEntity(id, name)