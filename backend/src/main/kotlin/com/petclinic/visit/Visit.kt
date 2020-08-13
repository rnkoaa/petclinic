package com.petclinic.visit

import com.petclinic.common.model.BaseEntity
import java.time.Instant
import java.util.*

data class Visit(override var id: UUID?,
                 val date: Instant,
                 val petId: UUID,
                 val ownerId: UUID,
                 val vetId: UUID?,
                 val description: String) : BaseEntity(id) {

}

