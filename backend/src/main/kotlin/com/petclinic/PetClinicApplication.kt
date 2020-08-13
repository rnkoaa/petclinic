package com.petclinic

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.FirestoreOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.IOException




@SpringBootApplication
class PetClinicApplication

fun main(args: Array<String>) {
    runApplication<PetClinicApplication>(*args)
}

@Configuration
class FireStoreConfig {

    @Bean
    fun firestore(@Value("\${gcloud.project.id}") projectId: String): Firestore {
        // [START fs_initialize_project_id]
        val firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                .setProjectId(projectId)
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .build();
        return firestoreOptions.service
    }

//    @Bean
//    @Throws(IOException::class)
//    fun firestore(): Firestore? {
//        // Use a service account
//        val serviceAccount: InputStream = javaClass.getResourceAsStream("token.json")
//        //		InputStream serviceAccount = new FileInputStream("path/to/serviceAccount.json");
//        val credentials = GoogleCredentials.fromStream(serviceAccount)
//        val options: FirebaseOptions = Builder()
//                .setCredentials(credentials)
//                .build()
//        FirebaseApp.initializeApp(options)
//        return FirestoreClient.getFirestore()
//    }

}
