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

or

Using `sentry.properties`:

```
factory=com.github.nomadblacky.sentry.config.SentryClientFactory
```

## Supported [configurations](https://docs.sentry.io/clients/java/config/)

[See the examples in `src/test/resources/application.conf`.](src/test/resources/application.conf)

## Custom client factories

### Implementation

```java
package sample;

import com.github.nomadblacky.sentry.config.DefaultTypesafeConfigSentryClientFactory;

public class MyCustomSentryClientFactory extends DefaultTypesafeConfigSentryClientFactory {
    @Override
    public SentryClient createSentryClient(Dsn dsn) {
        // Initialize a SentryClient with typesafe-config.
        SentryClient client = super.createSentryClient(dsn);
        client.addBuilderHelper(new CustomEventBuilderHelper());
        return client;
    }
}

public class CustomEventBuilderHelper implements EventBuilderHelper { 
    @Override
    public void helpBuildingEvent(EventBuilder eventBuilder) {
        // Helping to build events!
    }
}
```

### Usage

```
$ java -Dsentry.factory=sample.MyCustomSentryClientFactory
```

or

```
factory=sample.MyCustomSentryClientFactory
```
