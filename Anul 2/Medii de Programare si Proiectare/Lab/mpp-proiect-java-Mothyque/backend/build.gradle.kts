plugins {
    id("java")
    id("com.google.protobuf") version "0.9.4" apply false
}

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}