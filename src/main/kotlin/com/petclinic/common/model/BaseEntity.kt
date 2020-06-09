package com.petclinic.common.model

import java.util.*

abstract class BaseEntity(open var id: UUID?) {

    fun isNew(): Boolean {
        return this.id == null
    }
}

abstract class NamedEntity(override var id: UUID?, open var name: String) : BaseEntity(id)

