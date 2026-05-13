plugins {
    id("java")
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    id("org.openjfx.javafxplugin") version "0.1.0"
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

javafx {
    version = "21"
    modules("javafx.controls", "javafx.fxml")
}

val liquibaseRuntime by configurations.creating

dependencies {
    implementation("org.liquibase:liquibase-core:4.24.0")
    implementation("com.h2database:h2:2.2.224")

    "liquibaseRuntime"("org.liquibase:liquibase-core:4.24.0")
    "liquibaseRuntime"("com.h2database:h2:2.2.224")
    "liquibaseRuntime"("org.slf4j:slf4j-simple:2.0.9")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine")

    implementation("org.xerial:sqlite-jdbc:3.45.3.0")
    implementation("org.hibernate.orm:hibernate-community-dialects:6.5.2.Final")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.register<JavaExec>("dbUpdate") {
    group = "database"
    description = "Runs Liquibase migrations"

    classpath = liquibaseRuntime
    mainClass.set("liquibase.integration.commandline.Main")

    args(
        "--changelogFile=src/main/resources/db/changelog-master.xml",
        "--url=jdbc:h2:./testdb;DB_CLOSE_DELAY=-1",
        "--username=sa",
        "--password=",
        "--driver=org.h2.Driver",
        "update"
    )
}