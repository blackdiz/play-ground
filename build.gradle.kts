import net.ltgt.gradle.errorprone.errorprone
import org.checkerframework.gradle.plugin.CheckerFrameworkExtension
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")
    id("io.freefair.lombok") version "6.5.0.2"
    // Checker Framework pluggable type-checking
    id("org.checkerframework") version "0.6.14"
    id("net.ltgt.errorprone") version "2.0.2"
    // AspectJ
    id("io.freefair.aspectj.post-compile-weaving") version "6.5.0.2"
}

apply(plugin = "org.checkerframework")

group = "com.example.demo"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

configurations.all {
    exclude(group = "org.hamcrest", module = "hamcrest")
    exclude(group = "org.hamcrest", module = "hamcrest-core")
}

// To configure spring-devtools
configurations.runtimeClasspath.get().extendsFrom(configurations.developmentOnly.get())

dependencies {
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")

    implementation("org.checkerframework:checker-util:3.23.0")
// https://mvnrepository.com/artifact/org.projectlombok/lombok-mapstruct-binding
    implementation("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    // Data mapping
    val mapstructVersion = "1.5.2.Final"
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")


    implementation("org.aspectj:aspectjrt:1.9.9.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    errorprone("com.google.errorprone:error_prone_core:2.13.1")
    // https://mvnrepository.com/artifact/com.github.java-json-tools/json-patch
    implementation("com.github.java-json-tools:json-patch:1.13")

}

tasks.compileJava {
    options.compilerArgs.addAll(
        arrayListOf(
            "-Amapstruct.defaultComponentModel=spring",
            "-Amapstruct.defaultInjectionStrategy=constructor",
            "-AconservativeUninferredTypeArguments",
            // Make checker framework don't check the usages of these classes' methods
            "-AskipUses=org.apache.commons.lang3.StringUtils|java.awt.",
            // Make checker framework don't check these classes
            "-AskipDefs=.*.mapper.|.*.persistentobject.|.*.document.",
            // Make checker framework don't check keys of a map
            "-AassumeKeyFor"
        )
    )
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// Checker Framework
configure<CheckerFrameworkExtension> {
    checkers = listOf(
        "org.checkerframework.checker.nullness.NullnessChecker"
    )
//    excludeTests = true
}

tasks.withType<JavaCompile>().configureEach {
    options.errorprone.disableWarningsInGeneratedCode.set(true)
    options.errorprone.disable("MissingSummary", "EmptyBlockTag", "UnrecognisedJavadocTag", "UnescapedEntity")
}
