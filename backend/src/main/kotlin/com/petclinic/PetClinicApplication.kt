package com.petclinic

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.FirestoreOptions
//import com.petclinic.owner.service.OwnerIndexClient
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.asCoroutineDispatcher
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.io.IOException
import java.io.InputStream
import java.util.*
import java.util.concurrent.Executors

@SpringBootApplication
class PetClinicApplication

fun main(args: Array<String>) {
    runApplication<PetClinicApplication>(*args)
}

@Configuration
class IndexConfig {
//    @Bean
//    fun channel(@Value("\${index.grpc.host:localhost}") host: String,
//                @Value("\${index.grpc.port:15050}") port: Int): ManagedChannel {
//        return ManagedChannelBuilder
//                .forAddress(host, port)
//                .usePlaintext()
//                .build()
//    }
//
//    @Bean
//    fun ownerIndexClient(@Value("\${index.grpc.host:localhost}") host: String,
//                         @Value("\${index.grpc.port:15050}") port: Int): OwnerIndexClient {
//        val channelBuilder = ManagedChannelBuilder
//                .forAddress(host, port)
//                .usePlaintext()
//        val dispatcher = Executors.newFixedThreadPool(2)
//                .asCoroutineDispatcher()
//        return OwnerIndexClient(channelBuilder, dispatcher)
//    }
}
