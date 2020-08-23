package com.petclinic.config

import com.datastax.oss.driver.api.core.CqlSession
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories
import java.nio.file.Paths

// https://github.com/DataStax-Examples/spring-data-starter
@Configuration
@ConfigurationProperties(prefix = "astra.cassandra")
data class AstraCassandraConfigProperties(
        var username: String = "",
        var password: String = "",
        var keyspaceName: String = "",
        var securePath: String = ""
) {
}

@Configuration
@Profile("astra")
@EnableReactiveCassandraRepositories
class CassandraAstraConnection(private val cassandraConnection: AstraCassandraConfigProperties) {

    @Bean
    fun getDbSession(): CqlSession? {
        return CqlSession.builder()
                .withCloudSecureConnectBundle(Paths.get(cassandraConnection.securePath))
                .withAuthCredentials(cassandraConnection.username, cassandraConnection.password)
                .withKeyspace(cassandraConnection.keyspaceName)
                .build()
    }
}