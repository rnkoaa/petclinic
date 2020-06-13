package com.petclinic.visit

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import java.util.*

interface VisitRepository : ReactiveCassandraRepository<Visit, UUID> {
}