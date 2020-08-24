  
package com.petclinic.owner.model
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.petclinic.common.model.Person
import com.petclinic.owner.adapter.PetResponse
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.annotation.Transient
import org.springframework.data.cassandra.core.cql.Ordering
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.*
import java.util.*

@Table(value = "owner")
data class Owner(@PrimaryKey override var id: UUID?,
                 @Column("last_name") override var lastName: String,
                 @Column("first_name") override var firstName: String,
                 var telephone: String = "",
                 var address: String? = "",
                 var city: String? = "",
                 @Transient
                 var pets: Set<Pet> = setOf()
) : Person(id, firstName, lastName) {
    /**
     * Tell spring data to use this constructor because of transient issues
     */
    @PersistenceConstructor
    constructor(id: UUID?,
                lastName: String,
                firstName: String,
                telephone: String = "",
                address: String? = "",
                city: String? = "") : this(id, lastName, firstName, telephone, address, city, setOf())

    constructor(ownerByTelephone: OwnerByTelephone) :
            this(ownerByTelephone.id, ownerByTelephone.ownerByTelephoneKey.lastName,
                    ownerByTelephone.ownerByTelephoneKey.firstName,
                    ownerByTelephone.ownerByTelephoneKey.telephone,
                    ownerByTelephone.address, ownerByTelephone.city, setOf())
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
        var address: String?,
        var city: String?,
        var id: UUID?
) {
    constructor(owner: Owner) : this(OwnerByTelephoneKey(owner.telephone, owner.lastName, owner.firstName),
            owner.address, owner.city, owner.id
    )
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy::class)
data class CreateOwnerRequest(
        var id: UUID?,
        val firstName: String,
        val lastName: String,
        val telephone: String,
        var address: String?,
        var city: String?)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy::class)
data class OwnerResponse(
        val id: UUID?,
        val firstName: String,
        val lastName: String,
        val telephone: String,
        var address: String?,
        var city: String?,
        var pets: Set<PetResponse> = setOf()) {

    constructor(owner: Owner) : this(owner.id,
            owner.firstName,
            owner.lastName,
            owner.telephone,
            owner.address,
            owner.city)

    constructor(owner: Owner, pets: Set<PetResponse>) : this(owner.id,
            owner.firstName,
            owner.lastName,
            owner.telephone,
            owner.address,
            owner.city,
            pets)
}