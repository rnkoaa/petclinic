package com.petclinic.common.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*

abstract class BaseEntity(open var id: UUID?) {

    @JsonIgnore
    fun isNew(): Boolean {
        return this.id == null
    }
}

abstract class NamedEntity(override var id: UUID?, open var name: String) : BaseEntity(id)

