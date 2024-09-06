plugins {
    id("java-library")
    id("idea")
    id("maven-publish")
    id("org.springframework.boot") version "3.3.2" apply false
    id("io.spring.dependency-management") version "1.1.6" apply true
    id("com.diffplug.spotless") version "7.0.0.BETA2" apply true
}

group = "by.complitech"
version = "24.09.03"

subprojects {
    task("info") {
        doLast {
            println("project.version=${project.version}; project.group=${project.group}; project.name=${project.name}")
        }
    }

    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "idea")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "com.diffplug.spotless")

    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven {
            url = uri("https://repo.spring.io/milestone")
        }
    }

    configurations {
        forEach { it.exclude("commons-logging", "commons-logging") }
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks.getByName<Jar>("jar") {
        enabled = true
    }
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven {
            url = uri("https://repo.spring.io/milestone")
        }
    }

    rootProject.extra.set("springBootVersion", "3.3.3")
    rootProject.extra.set("springDocVersion", "2.6.0")
    rootProject.extra.set("jacksonVersion", "2.17.2")
    rootProject.extra.set("lombokVersion", "1.18.34")
    rootProject.extra.set("liquibaseVersion", "4.28.0")
    rootProject.extra.set("postgresVersion", "42.7.3")
    rootProject.extra.set("mapstructVersion", "1.6.0")
    rootProject.extra.set("hypersistenceVersion", "3.8.1")
    rootProject.extra.set("opencsvVersion", "5.9")

    dependencies {
        compileOnly("org.projectlombok:lombok:${rootProject.extra.get("lombokVersion")}")
        annotationProcessor("org.projectlombok:lombok:${rootProject.extra.get("lombokVersion")}")

        implementation("org.mapstruct:mapstruct:${rootProject.extra.get("mapstructVersion")}")
        annotationProcessor("org.mapstruct:mapstruct-processor:${rootProject.extra.get("mapstructVersion")}")
    }

    buildscript {
        repositories {
            mavenLocal()
            mavenCentral()
            google()
            maven {
                url = uri("https://repo.spring.io/milestone")
            }
        }
    }
}