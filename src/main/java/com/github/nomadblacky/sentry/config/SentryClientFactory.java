package com.github.nomadblacky.sentry.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.sentry.DefaultSentryClientFactory;
import io.sentry.SentryClient;
import io.sentry.dsn.Dsn;

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

        return configureSentryClient(client, defaultDsn);
    }
}
