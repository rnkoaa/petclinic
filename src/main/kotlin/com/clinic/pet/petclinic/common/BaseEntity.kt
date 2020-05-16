package com.clinic.pet.petclinic.common

abstract class BaseEntity(open var id: Int?) {
    fun isNew(): Boolean {
        return this.id == null || this.id == 0
    }
}