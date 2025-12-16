plugins {
    java
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.leadrocket"
version = "1.0.0"
description = "Lead Rocket Backend"

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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j")

    /* Redis cache */
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    /* H2 for tests */
    testImplementation("com.h2database:h2:2.2.220")

    /* Embedded Mongo for tests */
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:4.8.2")

    /* ================= JSON ================= */
    implementation("com.fasterxml.jackson.core:jackson-databind")

    /* ================= UTILITIES ================= */
    implementation("org.apache.commons:commons-lang3:3.14.0")

    /* ================= JWT ================= */
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    /* ================= ACTUATOR / METRICS ================= */
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")

    /* ================= TESTING ================= */
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mongodb")
    testImplementation("org.testcontainers:mysql")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
