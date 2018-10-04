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
        tryToConfigreStringValue(RELEASE_OPTION, client::setRelease);

        // Distribution
        tryToConfigreStringValue(DIST_OPTION, client::setDist);

        // Environment
        tryToConfigreStringValue(ENVIRONMENT_OPTION, client::setEnvironment);

        // Tags
        tryToConfigMapValue(TAGS_OPTION, client::setTags);

        // MDC Tags
        tryToConfigureStringListValue(MDCTAGS_OPTION, mdctags -> client.setMdcTags(new HashSet<>(mdctags)));

        // Extra Data
        tryToConfigMapValue(EXTRA_OPTION, map -> {
            client.setExtra(map.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())));
        });

        // "In Application" Stack Frames
        tryToConfigreStringValue(IN_APP_FRAMES_OPTION, settingSentryProperty(IN_APP_FRAMES_OPTION));

        // Same Frame as Enclosing Exception
        tryToConfigureBooleanValue(HIDE_COMMON_FRAMES_OPTION, settingSentryProperty(HIDE_COMMON_FRAMES_OPTION));

        // Event Sampling
        tryToConfigureDoubleValue(SAMPLE_RATE_OPTION, settingSentryProperty(SAMPLE_RATE_OPTION));

        // Uncaught Exception Handler
        tryToConfigureBooleanValue(UNCAUGHT_HANDLER_ENABLED_OPTION, settingSentryProperty(UNCAUGHT_HANDLER_ENABLED_OPTION));

        // Buffering
        tryToConfigureBooleanValue(BUFFER_ENABLED_OPTION, settingSentryProperty(BUFFER_ENABLED_OPTION));
        tryToConfigreStringValue(BUFFER_DIR_OPTION, settingSentryProperty(BUFFER_DIR_OPTION));
        tryToConfigureIntValue(BUFFER_SIZE_OPTION, settingSentryProperty(BUFFER_SIZE_OPTION));
        tryToConfigureLongValue(BUFFER_FLUSHTIME_OPTION, settingSentryProperty(BUFFER_FLUSHTIME_OPTION));
        tryToConfigureLongValue(BUFFER_SHUTDOWN_TIMEOUT_OPTION, settingSentryProperty(BUFFER_SHUTDOWN_TIMEOUT_OPTION));
        tryToConfigureBooleanValue(BUFFER_GRACEFUL_SHUTDOWN_OPTION, settingSentryProperty(BUFFER_GRACEFUL_SHUTDOWN_OPTION));

        // Async Connection
        tryToConfigureBooleanValue(ASYNC_OPTION, settingSentryProperty(ASYNC_OPTION));

        return configureSentryClient(client, defaultDsn);
    }

    private void tryToConfigreStringValue(String path, Consumer<String> configProc) {
        tryToConfigure(path, configProc, () -> config.getString(path));
    }

    private void tryToConfigureStringListValue(String path, Consumer<List<String>> configProc) {
        tryToConfigure(path, configProc, () -> config.getStringList(path));
    }

    private void tryToConfigureIntValue(String path, Consumer<Integer> configProc) {
        tryToConfigure(path, configProc, () -> config.getInt(path));
    }

    private void tryToConfigureLongValue(String path, Consumer<Long> configProc) {
        tryToConfigure(path, configProc, () -> config.getLong(path));
    }

    private void tryToConfigureDoubleValue(String path, Consumer<Double> configProc) {
        tryToConfigure(path, configProc, () -> config.getDouble(path));
    }

    private void tryToConfigureBooleanValue(String path, Consumer<Boolean> configProc) {
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

    private static final String SENTRY_PROPERTY_PREFIX = "sentry.";

    private static <T> Consumer<T> settingSentryProperty(String suffix) {
        return obj -> System.setProperty(SENTRY_PROPERTY_PREFIX + suffix, obj.toString());
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
