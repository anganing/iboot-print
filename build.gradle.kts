plugins {
    java
}

val htmlunitVersion = "2.70.0"
val hutoolVersion = "5.8.37"
val lombokVersion = "1.18.30"
val solonVersion = "3.3.1-M3"

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/") }
}

group = "com.iboot"
version = "1.0"
description = "the Hiprint Server Render Engine"


java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(platform("org.noear:solon-parent:$solonVersion"))

    implementation("org.noear:solon-web")
    implementation("org.noear:solon-logging-logback")

    implementation("net.sourceforge.htmlunit:htmlunit:$htmlunitVersion")
    implementation("cn.hutool:hutool-all:$hutoolVersion")

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testImplementation("org.noear:solon-test")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

tasks.withType<Jar> {
    manifest {
        attributes.apply {
            set("Main-Class", "com.iboot.io.print.App")
        }
    }

    dependsOn(configurations.runtimeClasspath)

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map {
        if (it.isDirectory) it else zipTree(it)
    })

    from(sourceSets.main.get().output)
}