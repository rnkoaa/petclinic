package com.clinic.pet.petclinic.common

abstract class Person(override var id: Int?,
                      open var lastName: String?,
                      open var firstName: String?) : BaseEntity(id) {
}