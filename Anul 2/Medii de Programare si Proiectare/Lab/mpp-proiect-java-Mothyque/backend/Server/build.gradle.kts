plugins {
    id("java")
}

dependencies {
    implementation(project(":Common"))

    implementation("org.apache.logging.log4j:log4j-core:2.23.1")
    implementation("org.apache.logging.log4j:log4j-api:2.23.1")

    implementation("org.xerial:sqlite-jdbc:3.45.2.0")

    implementation("io.grpc:grpc-netty-shaded:1.62.2")
    implementation("io.grpc:grpc-protobuf:1.62.2")
    implementation("io.grpc:grpc-stub:1.62.2")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.hibernate.orm:hibernate-community-dialects:6.4.4.Final")
    implementation("org.hibernate.orm:hibernate-core:6.4.4.Final")

    implementation("org.springframework.boot:spring-boot-starter-web:3.2.4")

    implementation("org.xerial:sqlite-jdbc:3.45.1.0")

    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("org.springframework.boot:spring-boot-starter-security:3.2.4")
    implementation("org.springframework.boot:spring-boot-starter-websocket:3.2.4")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
}