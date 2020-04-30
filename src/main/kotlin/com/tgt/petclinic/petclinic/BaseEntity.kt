package com.tgt.petclinic.petclinic

import org.springframework.data.annotation.Id

open class BaseEntity(open var id: String?) {

    fun isNew(): Boolean {
        return this.id == null || this.id == ""
    }
}

abstract class NamedEntity(override @Id var id: String?, name: String) : BaseEntity(id)

