package com.github.nomadblacky.sentry.config;

import com.typesafe.config.ConfigFactory;
import io.sentry.marshaller.json.JsonMarshaller;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import static io.sentry.DefaultSentryClientFactory.*;

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
        assertThat(FACTORY.getBufferedConnectionShutdownTimeout(null)).isEqualTo(BUFFER_SHUTDOWN_TIMEOUT_DEFAULT);
    }

    @Test
    void getBufferedConnectionGracefulShutdownEnabled() {
        assertThat(FACTORY.getBufferedConnectionGracefulShutdownEnabled(null)).isTrue();
    }

    @Test
    void getBufferFlushtime() {
        assertThat(FACTORY.getBufferFlushtime(null)).isEqualTo(BUFFER_FLUSHTIME_DEFAULT);
    }

    @Test
    void getAsyncShutdownTimeout() {
        assertThat(FACTORY.getAsyncShutdownTimeout(null)).isEqualTo(ASYNC_SHUTDOWN_TIMEOUT_DEFAULT);
    }

    @Test
    void getAsyncGracefulShutdownEnabled() {
        assertThat(FACTORY.getAsyncGracefulShutdownEnabled(null)).isTrue();
    }

    @Test
    void getAsyncQueueSize() {
        assertThat(FACTORY.getAsyncQueueSize(null)).isEqualTo(QUEUE_SIZE_DEFAULT);
    }

    @Test
    void getAsyncPriority() {
        assertThat(FACTORY.getAsyncPriority(null)).isEqualTo(Thread.MIN_PRIORITY);
    }

    @Test
    void getAsyncThreads() {
        assertThat(FACTORY.getAsyncThreads(null)).isEqualTo(Runtime.getRuntime().availableProcessors());
    }

    @Test
    void getBypassSecurityEnabled() {
        // FIXME: 2018/10/15 Implement this!
    }

    @Test
    void getSampleRate() {
        assertThat(FACTORY.getSampleRate(null)).isEqualTo(null);
    }

    @Test
    void getProxyPort() {
        assertThat(FACTORY.getProxyPort(null)).isEqualTo(HTTP_PROXY_PORT_DEFAULT);
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
        assertThat(FACTORY.getMaxMessageLength(null)).isEqualTo(JsonMarshaller.DEFAULT_MAX_MESSAGE_LENGTH);
    }

    @Test
    void getTimeout() {
        assertThat(FACTORY.getTimeout(null)).isEqualTo(TIMEOUT_DEFAULT);
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
        assertThat(FACTORY.getBufferSize(null)).isEqualTo(BUFFER_SIZE_DEFAULT);
    }

    @Test
    void getUncaughtHandlerEnabled() {
        assertThat(FACTORY.getUncaughtHandlerEnabled(null)).isTrue();
    }
}