package com.petclinic.common.model

import java.util.*

open class Person(override var id: UUID?,
                   open var firstName: String,
                   open var lastName: String
) : BaseEntity(id)