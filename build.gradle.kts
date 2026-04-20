plugins {
	java
	id("org.springframework.boot") version "4.0.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
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
	implementation("org.springframework.boot:spring-boot-starter")
    compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	implementation("org.jsoup:jsoup:1.18.1")
	// 1. .env ファイルを読み込むためのライブラリ
    implementation("io.github.cdimascio:dotenv-java:3.0.0")
    // 2. Slack API を操作するための公式ライブラリ
    implementation("com.slack.api:slack-api-client:1.44.2")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
