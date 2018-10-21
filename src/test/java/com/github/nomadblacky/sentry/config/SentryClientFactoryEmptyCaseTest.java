package com.github.nomadblacky.sentry.config;

import com.typesafe.config.ConfigFactory;
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
    void getRejectedExecutionHandler() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getBufferedConnectionShutdownTimeout() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getBufferedConnectionGracefulShutdownEnabled() {
        assertThat(FACTORY.getBufferedConnectionGracefulShutdownEnabled(null)).isTrue();
    }

    @Test
    void getBufferFlushtime() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getAsyncShutdownTimeout() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getAsyncGracefulShutdownEnabled() {
        assertThat(FACTORY.getAsyncGracefulShutdownEnabled(null)).isTrue();
    }

    @Test
    void getAsyncQueueSize() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getAsyncPriority() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getAsyncThreads() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getBypassSecurityEnabled() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getSampleRate() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
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
    void getTags() {
        assertThat(FACTORY.getTags(null)).isEmpty();
    }

    @Test
    void getExtraTags() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getMdcTags() {
        assertThat(FACTORY.getMdcTags(null)).isEmpty();
    }

    @Test
    void getExtra() {
        assertThat(FACTORY.getExtra(null)).isEmpty();
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
    void getMaxMessageLength() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getTimeout() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getBufferEnabled() {
        assertThat(FACTORY.getBufferEnabled(null)).isTrue();
    }

    @Test
    void getBuffer() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getBufferSize() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getUncaughtHandlerEnabled() {
        assertThat(FACTORY.getUncaughtHandlerEnabled(null)).isTrue();
    }
}