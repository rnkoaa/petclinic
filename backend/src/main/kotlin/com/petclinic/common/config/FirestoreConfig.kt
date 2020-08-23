package com.petclinic.common.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.FirestoreOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import java.io.File
import java.nio.charset.Charset

@Configuration
class FirestoreConfig {

    @Bean
    fun firestore(
            @Value("\${gcloud.project.id}") projectId: String,
            @Value("\${environment:prod}") env: String,
            environment: Environment
    ): Firestore {
        val firestoreOptionsBuilder = FirestoreOptions.getDefaultInstance()
                .toBuilder()
        if (env == "dev") {
            val credentialFileName = environment["firestore.credentials.file.name"]
            if (credentialFileName == null || credentialFileName == "") {
                throw IllegalArgumentException("credential file name not found")
            }
//            val serviceAccount: InputStream = javaClass.getResourceAsStream(credentialFileName)
            val stream = File(credentialFileName).inputStream()
//            println("Credentials: ${File(credentialFileName).readText(Charset.defaultCharset())}")

            val credentials = GoogleCredentials.fromStream(stream)
            firestoreOptionsBuilder.setCredentials(credentials)
        } else {
            firestoreOptionsBuilder
                    .setProjectId(projectId)
                    .setCredentials(GoogleCredentials.getApplicationDefault())
        }
        return firestoreOptionsBuilder
                .build().service
    }
}