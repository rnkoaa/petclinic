package com.petclinic.common.model

import org.springframework.data.cassandra.core.mapping.PrimaryKey

abstract class BaseEntity(open var id: String?) {

    fun isNew(): Boolean {
        return this.id == null || this.id == ""
    }
}

abstract class NamedEntity(@PrimaryKey override var id: String?, name: String) : BaseEntity(id)

