package com.petclinic.visit

import com.petclinic.common.model.BaseEntity
import com.petclinic.owner.model.Pet
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import java.time.LocalDateTime
import java.util.*

//@Table(value = "visit")
class Visit(@PrimaryKey override var id: UUID?,
            date: LocalDateTime,
            pet: Pet,
            description: String) : BaseEntity(id)