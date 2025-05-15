plugins {
    id("java")
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
    war // 💡 WAR 배포 시
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // Spring Web + MVC
    implementation("org.springframework.boot:spring-boot-starter-web")
    // Thymeleaf 템플릿 엔진
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    // JavaMail 이메일 발송
    implementation("org.springframework.boot:spring-boot-starter-mail")
    // Spring Data JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // Kotlin 관련 (기본 세팅)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // 테스트
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // Mysql connector
    runtimeOnly("com.mysql:mysql-connector-j:8.2.0")
    // thymeleaf
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    mainClass.set("com.newssubscribe.NewsSubscribeApplicationKt")
}

tasks.test {
    useJUnitPlatform()
}