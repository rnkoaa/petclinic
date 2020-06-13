package com.petclinic.vet.model

import com.petclinic.common.model.NamedEntity
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.util.*

@Table(value = "specialty")
data class Specialty(@PrimaryKey override var id: UUID?, override var name: String) : NamedEntity(id, name) {
    constructor(specialtyByName: SpecialtyByName) : this(specialtyByName.specialtyByNameKey.id, specialtyByName.specialtyByNameKey.name)
}

@PrimaryKeyClass
data class SpecialtyByNameKey(@PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED) val name: String,
                              @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED) val id: UUID)

@Table(value = "specialty_by_name")
data class SpecialtyByName(@PrimaryKey val specialtyByNameKey: SpecialtyByNameKey) {

    constructor(specialty: Specialty) : this(SpecialtyByNameKey(specialty.name, specialty.id!!))

    constructor(id: UUID, name: String) : this(SpecialtyByNameKey(name, id))
}