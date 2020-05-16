package com.tgt.petclinic.visit

import com.tgt.petclinic.common.model.BaseEntity
import com.tgt.petclinic.owner.Pet
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "visit")
class Visit(override @Id var id: String?,
            date: LocalDateTime,
            pet: Pet,
            description: String) : BaseEntity(id)