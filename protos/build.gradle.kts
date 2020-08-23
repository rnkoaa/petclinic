import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

val grpcVersion = "1.30.0"
val grpcKotlinVersion = "0.1.5" // CURRENT_GRPC_KOTLIN_VERSION
val protobufVersion = "3.12.2"
val coroutinesVersion = "1.3.7"

plugins {
    application
    kotlin("jvm") version "1.3.72"
    id("com.google.protobuf") version "0.8.12"
    id("idea")
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

repositories {
    mavenLocal()
    google()
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("javax.annotation:javax.annotation-api:1.2")
    implementation("com.google.protobuf:protobuf-java-util:$protobufVersion")
    implementation("com.google.protobuf:protobuf-java:$protobufVersion")
    implementation("io.grpc:grpc-protobuf:$grpcVersion")
    implementation("io.grpc:grpc-stub:$grpcVersion")
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    runtimeOnly("io.grpc:grpc-netty-shaded:$grpcVersion")
}

sourceSets {
    main {
        proto {
            // In addition to the default 'src/main/proto'
            srcDir("src/main/protos")
        }
    }
}

protobuf {
    generatedFilesBaseDir = "$projectDir/build/generated"

    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8

}

idea {
    module {
        generatedSourceDirs.add(file("$projectDir/build/generated/main/java"))
        generatedSourceDirs.add(file("$projectDir/build/generated/main/grpckt"))
        generatedSourceDirs.add(file("$projectDir/build/generated/main/grpc"))
//        generatedSourceDirs += file("$projectDir/build/generated/main/java");
//        generatedSourceDirs += file("$projectDir/build/generated/main/main/grpckt");
//        // If you have additional sourceSets and/or codegen plugins, add all of them
//        generatedSourceDirs += file("$projectDir/build/generated/main/main/grpc");
    }
}
