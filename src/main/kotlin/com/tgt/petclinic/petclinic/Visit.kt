package com.tgt.petclinic.petclinic

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "visit")
class Visit(override @Id var id: String?,
            date: LocalDateTime,
            pet: Pet,
            description: String) : BaseEntity(id)