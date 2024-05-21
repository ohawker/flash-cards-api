import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val kotlinVersion = "1.9.23"
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	id("com.diffplug.spotless") version "6.25.0"
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	kotlin("plugin.jpa") version kotlinVersion
}

group = "com.hawklogica"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	listOf(
		"org.springframework.boot:spring-boot-starter-webflux",
		"org.springframework.boot:spring-boot-starter-data-jpa",
		"org.springframework.boot:spring-boot-starter-actuator",

		"com.fasterxml.jackson.module:jackson-module-kotlin",
		"io.projectreactor.kotlin:reactor-kotlin-extensions",
		"org.jetbrains.kotlin:kotlin-reflect",
		"org.jetbrains.kotlinx:kotlinx-coroutines-reactor"
	).forEach { implementation(it) }

	runtimeOnly("org.postgresql:postgresql")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	listOf(
		"io.rest-assured:rest-assured:5.4.0",
		"org.springframework.boot:spring-boot-starter-test",
		"org.springframework.boot:spring-boot-testcontainers",
		"org.testcontainers:testcontainers:1.19.7",
		"org.testcontainers:junit-jupiter:1.19.7",
		"org.testcontainers:postgresql:1.19.7"

	).forEach { testImplementation(it) }
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

spotless {
	lineEndings = com.diffplug.spotless.LineEnding.UNIX
	kotlin {
		ktlint("0.48.0")
	}
}
