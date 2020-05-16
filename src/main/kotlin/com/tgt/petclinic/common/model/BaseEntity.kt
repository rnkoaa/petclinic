package com.tgt.petclinic.common.model

import org.springframework.data.annotation.Id

abstract class BaseEntity(open var id: String?) {

    fun isNew(): Boolean {
        return this.id == null || this.id == ""
    }
}

abstract class NamedEntity(@Id override var id: String?, name: String) : BaseEntity(id)

