package org.nomadblacky.sentry.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.sentry.DefaultSentryClientFactory;
import io.sentry.SentryClient;
import io.sentry.dsn.Dsn;

public class SentryClientFactory extends DefaultSentryClientFactory {

    @Override
    public SentryClient createSentryClient(Dsn defaultDsn) {
        Config config = ConfigFactory.load().getConfig("sentry");

        Dsn dsn = defaultDsn;
        if (config.hasPath("dsn")) {
            dsn = new Dsn(config.getString("dsn"));
        }

        SentryClient client = new SentryClient(createConnection(dsn), getContextManager(dsn));

        return configureSentryClient(client, defaultDsn);
    }
}
