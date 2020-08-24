package com.petclinic.vet.model


import com.petclinic.common.model.NamedEntity
import com.petclinic.common.model.Person
import com.petclinic.owner.model.Owner
import com.petclinic.owner.model.OwnerByTelephoneKey
import com.petclinic.vet.model.Specialty
import org.springframework.data.cassandra.core.cql.Ordering
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.*
import java.util.*

@UserDefinedType("vet_specialty")
data class VetSpecialty(override var id: UUID?, override var name: String) : NamedEntity(id, name) {
    companion object {
        fun from(specialty: Specialty): VetSpecialty {
            return VetSpecialty(specialty.id, specialty.name)
        }
    }
}

@Table(value = "vet")
data class Vet(@PrimaryKey override var id: UUID?,
               @Column("last_name") override var lastName: String,
               @Column("first_name") override var firstName: String,
               var telephone: String = "",
               var email: String = "",
               var specialties: Set<VetSpecialty>
) : Person(id, firstName, lastName)

@PrimaryKeyClass
data class VetByTelephoneKey(@PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED) //
                             var telephone: String,
                             @PrimaryKeyColumn(value = "first_name", type = PrimaryKeyType.CLUSTERED, ordinal = 0, ordering = Ordering.ASCENDING) //
                             var firstName: String,
                             @PrimaryKeyColumn(value = "last_name", type = PrimaryKeyType.CLUSTERED, ordinal = 1, ordering = Ordering.ASCENDING) //)
                             var lastName: String
)

@Table(value = "vet_by_telephone")
data class VetByTelephone(
        @PrimaryKey val vetByTelephoneKey: VetByTelephoneKey,
        val email: String = "",
        var id: UUID?
) {
    constructor(vet: Vet) : this(VetByTelephoneKey(vet.telephone, vet.lastName, vet.firstName), vet.email, vet.id)
}