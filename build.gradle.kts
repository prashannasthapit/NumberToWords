plugins {
    id("java")
}

group = "org.august"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // SLF4J API
    implementation("org.slf4j:slf4j-api:1.7.32")

    // Logback (an implementation of SLF4J)
    implementation("ch.qos.logback:logback-classic:1.2.9")
}

tasks.test {
    useJUnitPlatform()
}