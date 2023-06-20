plugins {
    // Apply the java-library plugin for API and implementation separation.
    `java-library`
}

group = "com.pebloop"
version = "0.0.1"

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api("org.apache.commons:commons-math3:3.6.1")

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation("com.google.guava:guava:31.0.1-jre")

    implementation(files("lib/libs/ffdec/ffdec_lib.jar"))

}