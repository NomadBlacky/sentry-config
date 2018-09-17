package com.github.nomadblacky.sentry.config;

import io.sentry.SentryClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SentryClientFactoryTest {

    private static SentryClientFactory FACTORY;
    private static SentryClient CLIENT;

    @BeforeAll
    static void beforeAll() {
        FACTORY = new SentryClientFactory();
        CLIENT = FACTORY.createSentryClient(null);
    }

    @Test
    void testToInitializeDsn() {
        assertThat(FACTORY.dsnString).isEqualTo("noop://localhost/1");
    }

    @Test
    void testToInitializeRelease() {
        assertThat(CLIENT.getRelease()).isEqualTo("0.1.0");
    }

    @Test
    void testToInitializeDistribution() {
        assertThat(CLIENT.getDist()).isEqualTo("x86");
    }
}
