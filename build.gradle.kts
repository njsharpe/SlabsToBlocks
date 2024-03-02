plugins {
    id("java")
    id("idea")
}

group = "net.njsharpe"
version = "1.0"

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")

    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.20.2-R0.1-SNAPSHOT")
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand("version" to project.version)
    }
}