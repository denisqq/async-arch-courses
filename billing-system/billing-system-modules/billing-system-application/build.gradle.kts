plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
}

group = "ru.denisqq"

repositories {
    maven {
        setUrl("https://packages.confluent.io/maven/")
    }
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")

    implementation("org.apache.avro:avro:1.11.3")
    implementation("io.confluent:kafka-avro-serializer:7.6.0")
    implementation("io.confluent:kafka-streams-avro-serde:7.6.0")
    implementation("io.confluent:kafka-schema-registry-client:7.6.0")

    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    runtimeOnly("org.postgresql:postgresql")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")


}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

tasks.generateAvroJava {
    source("../../../avro-schemas/billing","../../../avro-schemas/tasks", "../../../avro-schemas/users")
}