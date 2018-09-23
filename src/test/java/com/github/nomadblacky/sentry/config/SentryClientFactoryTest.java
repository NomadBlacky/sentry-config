package com.github.nomadblacky.sentry.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.sentry.SentryClient;
import io.sentry.jvmti.FrameCache;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

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

    @Test
    void testToInitializeInApplicationStackFrames() throws Exception {
        Field field = FrameCache.class.getDeclaredField("appPackages");
        field.setAccessible(true);
        Set<String> appPackages = (Set<String>) field.get(null);
        assertThat(appPackages).containsOnly("com.github.nomadblacky", "sample.foo");
    }

    @Test
    void testConfigToMapThrowAnErrorIfSetInvalidConfigurationType() {
        Config config = ConfigFactory.parseString("hoge = { invalid = 1234.56 }");
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> SentryClientFactory.configToMap(config))
                .withMessageContaining("Invalid configuration type (NUMBER) of \"hoge.invalid\"");
    }
}
