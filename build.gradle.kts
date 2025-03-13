plugins {
    id("java")

    id("application")
}

val lombokVersion = "1.18.36"
val mockitoVersion = "5.16.0"
group = "ru.itmo.cs.kdot.lab2"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.forkOptions.jvmArgs?.add("-J-Duser.language=en")
}

application {
    mainClass = "${group}.Main"
    applicationDefaultJvmArgs = listOf("-Duser.language=en")
}

dependencies {
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")
    implementation("org.projectlombok:lombok:${lombokVersion}")
    implementation("ch.obermuhlner:big-math:2.3.2")
    implementation("org.apache.commons:commons-csv:1.13.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:${mockitoVersion}")
    testImplementation("org.mockito:mockito-junit-jupiter:${mockitoVersion}")
    testImplementation("org.jacoco:org.jacoco.agent:0.8.12")
}

tasks.test {
    useJUnitPlatform()
}