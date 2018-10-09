# Sample application of sentry-config

## Usages

1. Set `sentry.dsn` in [`src/main/resources/application.conf`](src/main/resources/application.conf).

```
sentry {
  dsn = "<<Please set your project DSN>>"
  // ...
}
```

2. Run `./gradlew run`.

```shell-session
$ ./gradlew run
```

3. Check events in your project.

![sentry-events](https://github.com/NomadBlacky/sentry-config/blob/images/sentry-events.pnd)
