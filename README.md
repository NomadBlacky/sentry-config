# sentry-config

Configure [sentry-java](https://github.com/getsentry/sentry-java) by [typesafe-config](https://github.com/lightbend/config).

## Installation

Using Maven:

```xml
<dependency>
    <groupId>com.github.nomadblacky</groupId>
    <artifactId>sentry-config</artifactId>
    <version>0.4.0</version>
</dependency>
```

Using Gradle:

```groovy
compile 'com.github.nomadblacky:sentry-config:0.4.0'
```

Using SBT:

```scala
libraryDependencies += "com.github.nomadblacky" % "sentry-config" % "0.4.0"
```

## Usage

### Set configurations in `application.conf`

[See the examples in `src/test/resources/application.conf`.](src/test/resources/application.conf)

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

| Key                                | Supported   |
| :--------------------------------- | :---------: |
| ASYNC_GRACEFUL_SHUTDOWN_OPTION     |             |
| ASYNC_OPTION                       | true        |
| ASYNC_PRIORITY_OPTION              |             |
| ASYNC_QUEUE_DISCARDNEW             |             |
| ASYNC_QUEUE_DISCARDOLD             |             |
| ASYNC_QUEUE_DISCARDOLD             |             |
| ASYNC_QUEUE_OVERFLOW_OPTION        |             |
| ASYNC_QUEUE_SIZE_OPTION            |             |
| ASYNC_QUEUE_SYNC                   |             |
| ASYNC_SHUTDOWN_TIMEOUT_OPTION      | true        |
| ASYNC_THREADS_OPTION               |             |
| BUFFER_DIR_OPTION                  | true        |
| BUFFER_ENABLED_OPTION              | true        |
| BUFFER_FLUSHTIME_OPTION            | true        |
| BUFFER_GRACEFUL_SHUTDOWN_OPTION    | true        |
| BUFFER_SHUTDOWN_TIMEOUT_OPTION     | true        |
| BUFFER_SIZE_OPTION                 | true        |
| COMPRESSION_OPTION                 |             |
| DIST_OPTION                        | true        |
| DSN                                | true        |
| ENVIRONMENT_OPTION                 | true        |
| EXTRATAGS_OPTION (Deprecated)      |             |
| EXTRA_OPTION                       | true        |
| HIDE_COMMON_FRAMES_OPTION          | true        |
| HTTP_PROXY_HOST_OPTION             |             |
| HTTP_PROXY_PASS_OPTION             |             |
| HTTP_PROXY_PORT_OPTION             |             |
| HTTP_PROXY_USER_OPTION             |             |
| IN_APP_FRAMES_OPTION               | true        |
| MAX_MESSAGE_LENGTH_OPTION          |             |
| MDCTAGS_OPTION                     | true        |
| NAIVE_PROTOCOL                     |             |
| RELEASE_OPTION                     | true        |
| SAMPLE_RATE_OPTION                 | true        |
| SERVERNAME_OPTION                  | true        |
| TAGS_OPTION                        | true        |
| TIMEOUT_OPTION                     |             |
| UNCAUGHT_HANDLER_ENABLED_OPTION    | true        |
