plugins {
  java
  application
  id("org.javamodularity.moduleplugin") version "2.0.0"
  id("org.openjfx.javafxplugin") version "0.1.0"
  id("org.beryx.jlink") version "3.1.4-rc"
}

group = "one.nfolio"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

val junitVersion = "5.12.1"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(25)
  }
}

tasks.withType<JavaCompile> {
  options.encoding = "UTF-8"
}

application {
  mainModule.set("one.nfolio.rattel")
  mainClass.set("one.nfolio.rattel.HelloApplication")
  applicationDefaultJvmArgs = listOf(
    "--enable-native-access=javafx.graphics"
  )
}

javafx {
  version = "25.0.1"
  modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
  // https://mvnrepository.com/artifact/org.tensorflow/tensorflow-lite
  //runtimeOnly("org.tensorflow:tensorflow-lite:2.17.0")
  // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
  implementation("com.fasterxml.jackson.core:jackson-databind:2.20.1")
  implementation("com.fasterxml.jackson.core:jackson-annotations:2.20")
}

tasks.withType<Test> {
  useJUnitPlatform()
}

jlink {
  imageZip.set(layout.buildDirectory.file("/distributions/app-${javafx.platform.classifier}.zip"))
  options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
  launcher {
    name = "app"
  }
}
