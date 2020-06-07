package com.petclinic.common.model

import org.springframework.data.cassandra.core.mapping.Column
import java.util.*

open class Person(override var id: UUID?,
                  @Column("first_name") open var firstName: String,
                  @Column("first_name") open var lastName: String
) : BaseEntity(id)