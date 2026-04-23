plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("org.hibernate.orm:hibernate-core:6.3.1.Final")
    implementation("org.hibernate.orm:hibernate-hikaricp:6.3.1.Final")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

javafx {
    version = "17.0.6"
    modules("javafx.controls", "javafx.fxml")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("org.example.Main")
}