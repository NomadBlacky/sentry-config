plugins {
    `java-library`
}

repositories {
    jcenter()
}

dependencies {
    api("io.sentry", "sentry", "1.7.7")
    api("com.typesafe", "config", "1.3.3")
    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.3.0")
    testRuntime("org.junit.jupiter", "junit-jupiter-engine", "5.3.0")
}
