// https://github.com/spring-projects/spring-petclinic
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.3.3.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
}
val grpcVersion = "1.30.0"
val grpcKotlinVersion = "0.1.5" // CURRENT_GRPC_KOTLIN_VERSION
group = "com.clinic.pet"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
//    api(project(":protos"))
//    implementation("org.springframework.boot:spring-boot-starter-actuator")
//    implementation("org.springframework.boot:spring-boot-starter-validation")
//	implementation("org.springframework.boot:spring-boot-starter-data-cassandra-reactive")
//    implementation("org.springframework.boot:spring-boot-starter-webflux")
//    implementation("io.grpc:grpc-stub:$grpcVersion")
//    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")

    // implementation("com.google.cloud:google-cloud-firestore:1.35.2")
//
//    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
//
//    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
//
//    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.3.8")
//
//    implementation("org.jetbrains.kotlin:kotlin-reflect")
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
//
//    developmentOnly("org.springframework.boot:spring-boot-devtools")
//
//    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
//
//    testImplementation("org.springframework.boot:spring-boot-starter-test") {
//        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
//    }
//    testImplementation("io.projectreactor:reactor-test")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-cassandra-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.3.8")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("io.projectreactor:reactor-test")
}

tasks.getByName<BootJar>("bootJar") {
    layered()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
