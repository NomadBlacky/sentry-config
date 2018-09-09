package org.nomadblacky.sentry.config;

import io.sentry.dsn.Dsn;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SentryClientFactoryTest {

    private static SentryClientFactory FACTORY;

    @BeforeAll
    static void beforeAll() {
        FACTORY = new SentryClientFactory();
    }

    @Test
    void testToInitializeDsn() {
        FACTORY.createSentryClient(null);
        assertThat(FACTORY.dsnString).isEqualTo("noop://localhost/1");
    }
}