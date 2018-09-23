package com.github.nomadblacky.sentry.config;

import io.sentry.SentryClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
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

    @Test
    void testToInitializeEnvironment() {
        assertThat(CLIENT.getEnvironment()).isEqualTo("development");
    }

    @Test
    void testToInitializeTags() {
        assertThat(CLIENT.getTags()).containsOnly(
                entry("key1", "value1"),
                entry("key2", "value2")
        );
    }

    @Test
    @Disabled
    void testToThrowAnExceptionWhenInitializeTags() {
        // TODO: 18/09/17 Implement this!
        // If implement it now, will fail all tests.
        // Because @BeforeAll will throw an exception when initializing invalid configurations.
    }

    @Test
    void testToInitializeMdcTags() {
        assertThat(CLIENT.getMdcTags()).containsOnly("mdcTag1", "mdcTag2");
    }

    @Test
    void testToInitializeExtraData() {
        assertThat(CLIENT.getExtra()).containsOnly(
                entry("ex1", "exValue1"),
                entry("ex2", "exValue2")
        );
    }
}
