apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")

version = "24.09.03"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:${rootProject.extra.get("springBootVersion")}")
    implementation("org.springframework.boot:spring-boot-starter-validation:${rootProject.extra.get("springBootVersion")}")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${rootProject.extra.get("springBootVersion")}")
    implementation("org.springframework.boot:spring-boot-starter-security:${rootProject.extra.get("springBootVersion")}")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${rootProject.extra.get("springDocVersion")}")

    implementation("org.liquibase:liquibase-core:${rootProject.extra.get("liquibaseVersion")}")
    implementation("io.hypersistence:hypersistence-utils-hibernate-62:${rootProject.extra.get("hypersistenceVersion")}")

    implementation("com.opencsv:opencsv:${rootProject.extra.get("opencsvVersion")}")

    runtimeOnly("org.postgresql:postgresql:${rootProject.extra.get("postgresVersion")}")

    implementation("io.jsonwebtoken:jjwt-api:${rootProject.extra.get("jjwtVersion")}")
    implementation("io.jsonwebtoken:jjwt-impl:${rootProject.extra.get("jjwtVersion")}")
    implementation("io.jsonwebtoken:jjwt-jackson:${rootProject.extra.get("jjwtVersion")}")
    implementation("org.springframework:spring-websocket:${rootProject.extra.get("springWsVersion")}")
    implementation("org.springframework:spring-messaging:${rootProject.extra.get("springWsVersion")}")

    testImplementation("org.springframework.boot:spring-boot-starter-test:${rootProject.extra.get("springBootVersion")}")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}


tasks.test {
    useJUnitPlatform()
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    archiveFileName = "main-server.jar"
}