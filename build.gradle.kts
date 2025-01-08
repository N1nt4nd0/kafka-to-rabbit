plugins {
    java
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("jacoco")
}

group = "org.kafka.practice.kafkademo.domain"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Web dependencies
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Apache Velocity
    implementation("org.apache.velocity:velocity-engine-core:2.4.1")
    implementation("org.apache.velocity.tools:velocity-tools-generic:3.1")

    // OpenAPI
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")
    implementation("org.springdoc:springdoc-openapi-ui:1.8.0")

    // Database dependencies
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    // Messaging dependencies
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.kafka:spring-kafka")

    // Utility dependencies
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("net.datafaker:datafaker:2.4.2")

    // Test dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.amqp:spring-rabbit-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

jacoco {
    toolVersion = "0.8.10"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
    val excludedClasses = listOf(
        "**/domain/config/**",
        "**/domain/dev/**",
        "**/domain/entities/**",
    )
    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude(excludedClasses)
            }
        })
    )
}