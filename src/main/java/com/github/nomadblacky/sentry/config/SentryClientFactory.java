package com.github.nomadblacky.sentry.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueType;
import io.sentry.DefaultSentryClientFactory;
import io.sentry.SentryClient;
import io.sentry.dsn.Dsn;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SentryClientFactory extends DefaultSentryClientFactory {

    // FIXME: 18/09/09 This variable is to only use for tests.
    String dsnString;

    @Override
    public SentryClient createSentryClient(Dsn defaultDsn) {
        Config config = ConfigFactory.load().getConfig("sentry");

        // DSN
        Dsn dsn = defaultDsn;
        if (config.hasPath("dsn")) {
            dsnString = config.getString("dsn");
            dsn = new Dsn(dsnString);
        }

        SentryClient client = new SentryClient(createConnection(dsn), getContextManager(dsn));

        // Release
        if (config.hasPath("release")) {
            client.setRelease(config.getString("release"));
        }

        // Distribution
        if (config.hasPath("distribution")) {
            client.setDist(config.getString("distribution"));
        }

        // Environment
        if (config.hasPath("environment")) {
            client.setEnvironment(config.getString("environment"));
        }

        // Tags
        if (config.hasPath("tags")) {
            Map<String, String> tags = configToMap(config.getConfig("tags"));
            client.setTags(tags);
        }

        // MDC Tags
        if (config.hasPath("mdctags")) {
            client.setMdcTags(new HashSet<>(config.getStringList("mdctags")));
        }

        // Extra Data
        if (config.hasPath("extra")) {
            Map<String, Object> extra = configToMap(config.getConfig("extra"))
                    .entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
            client.setExtra(extra);
        }

        return configureSentryClient(client, defaultDsn);
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
