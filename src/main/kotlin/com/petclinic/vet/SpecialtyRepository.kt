package com.petclinic.vet

import com.petclinic.vet.model.Specialty
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import java.util.*

interface SpecialtyRepository : ReactiveCassandraRepository<Specialty, UUID> {

}
