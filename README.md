# WIP: sentry-config

Configure [sentry-java](https://github.com/getsentry/sentry-java) by [typesafe-config](https://github.com/lightbend/config).

## Installation

Using Maven:

```xml
<dependency>
    <groupId>com.github.nomadblacky</groupId>
    <artifactId>sentry-config</artifactId>
    <version>0.1.0</version>
</dependency>
```

Using Gradle:

```groovy
compile 'com.github.nomadblacky:sentry-config:0.1.0'
```

Using SBT:

```scala
libraryDependencies += "com.github.nomadblacky" % "sentry-config" % "0.1.0"
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
$ java -Dsentry.factory=com.github.nomadblacky.sentry.config.SentryClientFactory
```

Using `sentry.properties`:

```
factory=com.github.nomadblacky.sentry.config.SentryClientFactory
```

## Supported [configurations](https://docs.sentry.io/clients/java/config/)

|Key                              |Supported|
|:--------------------------------|:-------:|
|DSN                              |true     |
|Release                          |false    |
|Distribution                     |false    |
|Environment                      |false    |
|Server Name                      |false    |
|Tags                             |false    |
|MDC Tags                         |false    |
|Extra Data                       |false    |
|"In Application" Stack Frames    |false    |
|Same Frame as Enclosing Exception|false    |
|Event Sampling                   |false    |
|Uncaught Exception Handler       |false    |
|Buffering Events to Disk         |false    |
|Graceful Shutdown of Buffering   |false    |
|Async Connection                 |false    |
|Graceful Shutdown of Async       |false    |
|Async Queue Size                 |false    |
|Async Threads Count              |false    |
|Async Threads Priority           |false    |
|Max Message Size                 |false    |
|Timeout                          |false    |
|Using a Proxy                    |false    |
