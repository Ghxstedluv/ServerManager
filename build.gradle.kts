plugins {
    id("java")
}

group = "me.ghxst"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.register<Jar>("fatJar") {
    archiveBaseName.set("ServerManager")
    archiveVersion.set("1.0")
    manifest {
        attributes["Main-Class"] = "me.ghxst.Main" // your main class
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)

    // Include dependencies (if any)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    })
}