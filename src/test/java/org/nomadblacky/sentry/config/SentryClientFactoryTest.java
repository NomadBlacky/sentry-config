package org.nomadblacky.sentry.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SentryClientFactoryTest {

    private static SentryClientFactory FACTORY;

    @BeforeAll
    static void beforeAll() {
        FACTORY = new SentryClientFactory();
    }

    @Test
    void testInitialize() {
        FACTORY.createSentryClient(null);
    }
}