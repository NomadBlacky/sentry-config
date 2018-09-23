package com.github.nomadblacky.sentry.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueType;
import io.sentry.DefaultSentryClientFactory;
import io.sentry.SentryClient;
import io.sentry.dsn.Dsn;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SentryClientFactory extends DefaultSentryClientFactory {

    // FIXME: 18/09/09 This variable is to only use for tests.
    String dsnString;

    private Config config;

    @Override
    public SentryClient createSentryClient(Dsn defaultDsn) {
        config = ConfigFactory.load().getConfig("sentry");

        // DSN
        Dsn dsn = defaultDsn;
        if (config.hasPath("dsn")) {
            dsnString = config.getString("dsn");
            dsn = new Dsn(dsnString);
        }

        SentryClient client = new SentryClient(createConnection(dsn), getContextManager(dsn));

        // Release
        tryToConfigreStringValue("release", client::setRelease);

        // Distribution
        tryToConfigreStringValue("distribution", client::setDist);

        // Environment
        tryToConfigreStringValue("environment", client::setEnvironment);

        // Tags
        tryToConfigMapValue("tags", client::setTags);

        // MDC Tags
        tryToConfigureStringListValue("mdctags", mdctags -> client.setMdcTags(new HashSet<>(mdctags)));

        // Extra Data
        tryToConfigMapValue("extra", map -> {
            client.setExtra(map.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())));
        });

        // "In Application" Stack Frames
        tryToConfigreStringValue(
                "stacktrace.app.packages",
                packages -> System.setProperty("sentry.stacktrace.app.packages", packages)
        );

        // Same Frame as Enclosing Exception
        tryToConfigreBooleanValue(
                "stacktrace.hidecommon",
                hideCommon -> System.setProperty("sentry.stacktrace.hidecommon", hideCommon.toString())
        );

        return configureSentryClient(client, defaultDsn);
    }

    private void tryToConfigreStringValue(String path, Consumer<String> configProc) {
        tryToConfigure(path, configProc, () -> config.getString(path));
    }

    private void tryToConfigureStringListValue(String path, Consumer<List<String>> configProc) {
        tryToConfigure(path, configProc, () -> config.getStringList(path));
    }

    private void tryToConfigreBooleanValue(String path, Consumer<Boolean> configProc) {
        tryToConfigure(path, configProc, () -> config.getBoolean(path));
    }

    private void tryToConfigMapValue(String path, Consumer<Map<String, String>> configProc) {
        tryToConfigure(path, configProc, () -> configToMap(config.getConfig(path)));
    }

    private <T> void tryToConfigure(String path, Consumer<T> configProc, Supplier<T> valueSupplier) {
        if (config.hasPath(path)) {
            configProc.accept(valueSupplier.get());
        }
    }

    static Map<String, String> configToMap(Config config) {
        return config.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> {
            if (e.getValue().valueType() == ConfigValueType.STRING) {
                return config.getString(e.getKey());
            } else {
                throw new IllegalArgumentException(String.format(
                        "Invalid configuration type (%s) of \"%s\" in %s L%d. Please set with string.",
                        e.getValue().valueType().name(),
                        e.getKey(),
                        config.origin().filename(),
                        e.getValue().origin().lineNumber()
                ));
            }
        }));
    }
}
