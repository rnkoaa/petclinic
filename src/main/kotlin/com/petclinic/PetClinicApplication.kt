package com.petclinic

import com.petclinic.owner.Owner
import com.petclinic.owner.OwnerByTelephone
import com.petclinic.owner.OwnerByTelephoneRepository
import com.petclinic.owner.OwnerRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.util.*

@SpringBootApplication
class PetClinicApplication

fun main(args: Array<String>) {
    runApplication<PetClinicApplication>(*args)
}

//@Component
//class Runner(val ownerRepository: OwnerRepository,
//             val ownerByTelephoneRepository: OwnerByTelephoneRepository) : CommandLineRunner {
//    override fun run(vararg args: String?) {
//        println("Command line Runner is running.")
//        val owner = Owner(UUID.randomUUID(), "Agyei", "Richard", "29 lower c", "Rose", "333-665-0098")
//        val saved = ownerRepository.save(owner)
//        saved.block()
//        ownerByTelephoneRepository.save(OwnerByTelephone(owner)).block()
////        val all = ownerRepository.findAll()
////        all.subscribe { res -> println(res) }
//
//        ownerByTelephoneRepository.findAll()
//                .subscribe { res -> println("owner by repository $res") }
//
//
//    }
//
//}
//
