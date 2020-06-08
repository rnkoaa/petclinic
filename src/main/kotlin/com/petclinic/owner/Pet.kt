package com.petclinic.owner

import com.petclinic.common.model.NamedEntity
import com.petclinic.visit.Visit
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.*
import java.time.LocalDate
import org.springframework.data.annotation.Transient
import java.util.*

@UserDefinedType("pet_type")
class PetType(val name: String)

@Table(value = "pet")
data class Pet(@PrimaryKey override var id: UUID?,
               @Column("owner_id")
               val ownerId: UUID,
               @Column("birth_date")
               var birthDate: LocalDate,
               @Column("pet_type")
               var type: PetType,
               override var name: String,
               @Transient
               var visits: Set<Visit>
) : NamedEntity(id, name) {

    @PersistenceConstructor
    constructor(id: UUID?,
                ownerId: UUID,
                birthDate: LocalDate,
                type: PetType,
                name: String) : this(id, ownerId, birthDate, type, name, setOf())

    constructor(petByOwner: PetByOwner) : this(petByOwner.petByOwnerKey.petId, petByOwner.petByOwnerKey.ownerId, petByOwner.birthDate,
            petByOwner.type, petByOwner.name, petByOwner.visits)
}

@PrimaryKeyClass
data class PetByOwnerKey(
        @PrimaryKeyColumn(value = "owner_id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
        val ownerId: UUID,
        @PrimaryKeyColumn(name = "pet_id", type = PrimaryKeyType.CLUSTERED, ordinal = 1)
        val petId: UUID
)

@Table(value = "pet_by_owner")
data class PetByOwner(
        @PrimaryKey val petByOwnerKey: PetByOwnerKey,
        @Column("birth_date")
        var birthDate: LocalDate,
        @Column("pet_type")
        var type: PetType,
        var name: String,
        @Transient
        var visits: Set<Visit>
) {
    @PersistenceConstructor
    constructor(petByOwnerKey: PetByOwnerKey,
                birthDate: LocalDate,
                type: PetType,
                name: String) : this(petByOwnerKey, birthDate, type, name, setOf())

    constructor(pet: Pet) : this(PetByOwnerKey(pet.ownerId, pet.id!!), pet.birthDate, pet.type, pet.name, pet.visits)
}