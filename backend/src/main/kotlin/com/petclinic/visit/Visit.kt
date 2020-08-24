package com.petclinic.visit


import com.petclinic.common.model.BaseEntity
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.time.Instant
import java.util.*

@Table(value = "visit")
data class Visit(@PrimaryKey override var id: UUID?,
                 val date: Instant,
                 @Column("pet_id")
                 val petId: UUID,
                 @Column("owner_id")
                 val ownerId: UUID,
                 @Column("vet_id")
                 val vetId: UUID?,
                 val description: String) : BaseEntity(id) {

}

