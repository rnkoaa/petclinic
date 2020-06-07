package com.petclinic.visit

import com.petclinic.common.model.BaseEntity
import com.petclinic.owner.Pet
import org.springframework.data.annotation.Id
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

//@Table(value = "visit")
class Visit(@PrimaryKey override var id: UUID?,
            date: LocalDateTime,
            pet: Pet,
            description: String) : BaseEntity(id)