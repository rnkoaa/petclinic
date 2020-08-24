package com.petclinic.config

import com.datastax.oss.driver.api.core.CqlSession
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories
import java.net.InetSocketAddress

//https://docs.spring.io/spring-data/cassandra/docs/current/reference/html/#reference

@EnableConfigurationProperties(value = [CassandraProperties::class])
@Configuration
@EnableReactiveCassandraRepositories
@Profile("!astra")
class CassandraConfiguration(val cassandraProperties: CassandraProperties) {
    @Bean
    fun getDbSession(): CqlSession? {
        val contactPoints = cassandraProperties.contactPoints.map {
            InetSocketAddress(it, cassandraProperties.port)
        }
        return CqlSession.builder()
                .addContactPoints(contactPoints)
                .withAuthCredentials(cassandraProperties.username, cassandraProperties.password)
                .withKeyspace(cassandraProperties.keyspaceName)
                .withLocalDatacenter(cassandraProperties.localDatacenter)
                .build()
    }
}
/*

@EnableConfigurationProperties(value = [CassandraProperties::class])
@Configuration
@EnableReactiveCassandraRepositories
class CassandraConfiguration : AbstractReactiveCassandraConfiguration() {
    @Autowired
    private lateinit var env: Environment

    */
/**
 * override the cluster bean with extra details so that it can connect if ssl is enabled.
 *//*

    override fun cluster(): CassandraClusterFactoryBean {
        val sslEnabled = getBooleanProperty("spring.data.cassandra.ssl")
        return CassandraClusterFactoryBean().apply {
            this.keyspaceCreations = keyspaceCreations
            this.setContactPoints(contactPoints)
            this.setUsername(cassandraProperties.username)
            this.setPassword(cassandraProperties.password)
            this.setPort(cassandraProperties.port);
            this.setSslEnabled(sslEnabled);
            this.setMetricsEnabled(false)
        }
    }

    fun getBooleanProperty(propertyName: String): Boolean {
        val prop = env.getProperty(propertyName) ?: return false
        return prop.toBoolean()
    }

    @Autowired
    private lateinit var cassandraProperties: CassandraProperties

    override fun getPort(): Int {
        return cassandraProperties.port
    }

    override fun getContactPoints(): String {
        return cassandraProperties.contactPoints.joinToString(",").trim()
    }

    override fun getKeyspaceName(): String {
        return cassandraProperties.keyspaceName
    }

    override fun getStartupScripts(): List<String> {
        val script = ("""
            CREATE KEYSPACE IF NOT EXISTS ${cassandraProperties.keyspaceName} WITH durable_writes = true AND replication = {'replication_factor' : 1,
'class' : 
'SimpleStrategy' }
        """.trimIndent())
        return listOf(script)
    }

*/
//}