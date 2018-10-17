package com.github.nomadblacky.sentry.config;

import com.typesafe.config.ConfigFactory;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SentryClientFactoryEmptyCaseTest {

    private static SentryClientFactory FACTORY;

    @BeforeAll
    static void beforeAll() {
        FACTORY = new SentryClientFactory(ConfigFactory.empty());
    }

    @Test
    @Ignore
    void getContextManager() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getInAppFrames() {
        assertThat(FACTORY.getInAppFrames(null)).isEmpty();
    }

    @Test
    void getAsyncEnabled() {
        assertThat(FACTORY.getAsyncEnabled(null)).isTrue();
    }

    @Test
    @Ignore
    void getRejectedExecutionHandler() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getBufferedConnectionShutdownTimeout() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getBufferedConnectionGracefulShutdownEnabled() {
        assertThat(FACTORY.getBufferedConnectionGracefulShutdownEnabled(null)).isTrue();
    }

    @Test
    @Ignore
    void getBufferFlushtime() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getAsyncShutdownTimeout() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getAsyncGracefulShutdownEnabled() {
        assertThat(FACTORY.getAsyncGracefulShutdownEnabled(null)).isTrue();
    }

    @Test
    @Ignore
    void getAsyncQueueSize() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getAsyncPriority() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getAsyncThreads() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getBypassSecurityEnabled() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getSampleRate() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getProxyPort() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getProxyHost() {
        assertThat(FACTORY.getProxyHost(null)).isNull();
    }

    @Test
    void getProxyUser() {
        assertThat(FACTORY.getProxyUser(null)).isNull();
    }

    @Test
    void getProxyPass() {
        assertThat(FACTORY.getProxyPass(null)).isNull();
    }

    @Test
    void getRelease() {
        assertThat(FACTORY.getRelease(null)).isNull();
    }

    @Test
    void getDist() {
        assertThat(FACTORY.getDist(null)).isNull();
    }

    @Test
    void getEnvironment() {
        assertThat(FACTORY.getEnvironment(null)).isNull();
    }

    @Test
    void getServerName() {
        assertThat(FACTORY.getServerName(null)).isNull();
    }

    @Test
    @Ignore
    void getTags() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getExtraTags() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getMdcTags() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getExtra() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getCompressionEnabled() {
        assertThat(FACTORY.getCompressionEnabled(null)).isTrue();
    }

    @Test
    void getHideCommonFramesEnabled() {
        assertThat(FACTORY.getHideCommonFramesEnabled(null)).isTrue();
    }

    @Test
    @Ignore
    void getMaxMessageLength() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getTimeout() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getBufferEnabled() {
        assertThat(FACTORY.getBufferEnabled(null)).isTrue();
    }

    @Test
    @Ignore
    void getBuffer() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getBufferSize() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getUncaughtHandlerEnabled() {
        assertThat(FACTORY.getUncaughtHandlerEnabled(null)).isTrue();
    }
}