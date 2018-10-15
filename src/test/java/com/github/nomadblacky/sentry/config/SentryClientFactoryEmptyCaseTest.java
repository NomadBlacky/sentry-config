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
    @Ignore
    void getAsyncEnabled() {
        // FIXME: 2018/10/15 Implement this!
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
    @Ignore
    void getBufferedConnectionGracefulShutdownEnabled() {
        // FIXME: 2018/10/15 Implement this!
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
    @Ignore
    void getAsyncGracefulShutdownEnabled() {
        // FIXME: 2018/10/15 Implement this!
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
    @Ignore
    void getProxyHost() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getProxyUser() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getProxyPass() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getRelease() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getDist() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getEnvironment() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getServerName() {
        // FIXME: 2018/10/15 Implement this!
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
    @Ignore
    void getCompressionEnabled() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    @Ignore
    void getHideCommonFramesEnabled() {
        // FIXME: 2018/10/15 Implement this!
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
    @Ignore
    void getBufferEnabled() {
        // FIXME: 2018/10/15 Implement this!
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
    @Ignore
    void getUncaughtHandlerEnabled() {
        // FIXME: 2018/10/15 Implement this!
    }
}