package com.petclinic.common.model

import org.springframework.data.cassandra.core.mapping.PrimaryKey
import java.util.*

abstract class BaseEntity(open var id: UUID?) {

    fun isNew(): Boolean {
        return this.id == null
    }
}

abstract class NamedEntity(@PrimaryKey override var id: UUID?, name: String) : BaseEntity(id)

