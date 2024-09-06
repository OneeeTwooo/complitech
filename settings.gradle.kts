rootProject.name = "complitech"

include("main-service")

pluginManagement {
    repositories{
        mavenLocal()
        mavenCentral()
        google()
        maven {
            url = uri("https://repo.spring.io/milestone")
        }
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories{
        mavenLocal()
        mavenCentral()
        google()
        maven {
            url = uri("https://repo.spring.io/milestone")
        }
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}
