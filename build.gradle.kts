plugins {
    // Apply the java-library plugin for API and implementation separation.
    java
}

group = "com.pebloop"
version = "0.0.1"

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation(project(":lib"))
}
