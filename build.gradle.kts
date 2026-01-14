plugins {
	java
    id("io.freefair.lombok") version "9.1.0"
	id("org.springframework.boot") version "4.0.0"
	id("io.spring.dependency-management") version "1.1.7"
    id("com.diffplug.spotless") version "8.1.0"
}

group = "io.kjm015"
version = "0.0.1-SNAPSHOT"
description = "Example Discord bot using Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-restclient")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("com.github.jagrosh:JLyrics:master-SNAPSHOT")
	testImplementation("org.springframework.boot:spring-boot-starter-restclient-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Discord API
    implementation("net.dv8tion:JDA:6.1.1")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

spotless {
    format("misc") {
        target("*.gradle", "*.md", ".gitignore")
        trimTrailingWhitespace()
        leadingSpacesToTabs()
        endWithNewline()
    }

    java {
        // apply a specific flavor of google-java-format
        googleJavaFormat("1.32.0").aosp().skipJavadocFormatting()
        // fix formatting of type annotations
        formatAnnotations()
    }
}
