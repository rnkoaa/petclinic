package com.petclinic.owner

import com.petclinic.common.model.Person
import org.springframework.data.cassandra.core.cql.Ordering
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.*
import java.util.*

@Table(value = "owner")
data class Owner(@PrimaryKey override var id: UUID?,
                 @Column("last_name") override var lastName: String,
                 @Column("first_name") override var firstName: String,
                 var address: String = "",
                 var city: String = "",
                 var telephone: String = ""
//                 var pets: Set<Pet>

) : Person(id, firstName, lastName) {
    constructor(ownerByTelephone: OwnerByTelephone) :
            this(ownerByTelephone.id, ownerByTelephone.ownerByTelephoneKey.lastName,
                    ownerByTelephone.ownerByTelephoneKey.firstName,
                    ownerByTelephone.address, ownerByTelephone.city, ownerByTelephone.ownerByTelephoneKey.telephone)
}

@PrimaryKeyClass
data class OwnerByTelephoneKey(@PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED) //
                               var telephone: String,
                               @PrimaryKeyColumn(value = "first_name", type = PrimaryKeyType.CLUSTERED, ordinal = 0, ordering = Ordering.ASCENDING) //
                               var firstName: String,
                               @PrimaryKeyColumn(value = "last_name", type = PrimaryKeyType.CLUSTERED, ordinal = 1, ordering = Ordering.ASCENDING) //)
                               var lastName: String
)

@Table(value = "owner_by_telephone")
data class OwnerByTelephone(
        @PrimaryKey val ownerByTelephoneKey: OwnerByTelephoneKey,
        var city: String,
        var address: String,
        var id: UUID?
) {
    constructor(owner: Owner) : this(OwnerByTelephoneKey(owner.telephone, owner.lastName, owner.firstName),
            owner.city, owner.address, owner.id
    )
}