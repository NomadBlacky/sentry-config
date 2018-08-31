# WIP: sentry-config

Configure [sentry-java](https://github.com/getsentry/sentry-java) by [typesafe-config](https://github.com/lightbend/config).

## Installation

Using Maven:

```xml
<dependency>
    <groupId>org.nomadblacky</groupId>
    <artifactId>sentry-config</artifactId>
    <version>0.1.0</version>
</dependency>
```

Using Gradle:

```groovy
compile 'org.nomadblacky:sentry-config:0.1.0'
```

Using SBT:

```scala
libraryDependencies += "org.nomadblacky" % "sentry-config" % "0.1.0"
```

## Usage

### Set configurations in `application.conf`

```
sentry {
  dsn = "https://public:private@host:port/1"
  environment = "production"
}
```

### Apply the `sentry.factory` property

Using JVM options:

```
$ java -Dsentry.factory=org.nomadblacky.sentry.config.SentryClientFactory
```

Using `sentry.properties`:

```
factory=org.nomadblacky.sentry.config.SentryClientFactory
```
