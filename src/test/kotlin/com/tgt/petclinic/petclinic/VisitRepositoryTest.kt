package com.tgt.petclinic.petclinic

import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.core.publisher.Flux
import java.time.LocalDate
import java.time.LocalDateTime

//@DataMongoTest

class VisitRepositoryTest {
    val visitRepository = mockk<VisitRepository>()

    //
//    private val visit = listOf(
//            Visit("",
//                    LocalDateTime.now(),
//                    Pet("", LocalDate.of(2019, 3, 31), PetType("", "Dog"), emptySet(), "pet1"),
//                    "checkup"
//            ),
//            Visit("",
//                    LocalDateTime.now(),
//                    Pet("", LocalDate.of(2019, 3, 31), PetType("", "Dog"), emptySet(), "pet1"),
//                    "checkup"
//            )
//    )

    @Test
    fun `Context loads`() {
        assertThat(visitRepository).isNotNull
        val pet = Pet("", LocalDate.of(2019, 3, 2),
                PetType("", "Dog"), emptySet(), "pooch")
        val visit = Visit("", LocalDateTime.now().minusDays(1),
                pet,
                "wellness checkup")
//        visitRepository.shouldNotBeNull()s

    }
}