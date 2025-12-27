plugins {
    java
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.leadrocket"
version = "1.0.0"
description = "LeadRocket Backend"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {

    /* ================= CORE ================= */
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    /* ================= SECURITY ================= */
    implementation("org.springframework.boot:spring-boot-starter-security")

    /* ================= DATABASE ================= */
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    /* ================= JWT ================= */
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    /* ================= JSON ================= */
    implementation("com.fasterxml.jackson.core:jackson-databind")

    /* ================= UTILITIES ================= */
    implementation("org.apache.commons:commons-lang3:3.14.0")

    /* ================= MONITORING ================= */
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")

    /* ================= TESTING ================= */
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // MongoDB testing
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mongodb")

    // Embedded Mongo (fast local tests, optional)
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:4.11.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
