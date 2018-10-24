package com.github.nomadblacky.sentry.config;

import static org.assertj.core.api.Assertions.*;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.sentry.buffer.Buffer;
import io.sentry.buffer.DiskBuffer;
import java.io.File;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SentryClientFactoryTest {

  private static SentryClientFactory FACTORY;

  @BeforeAll
  static void beforeAll() {
    FACTORY = new SentryClientFactory();
  }

  @Test
  void initializeDsn() {
    FACTORY.createSentryClient(null);
    assertThat(FACTORY.dsnString).isEqualTo("noop://localhost/1");
  }

  @Test
  void getInAppFrames() {
    assertThat(FACTORY.getInAppFrames(null)).containsOnly("com.github.nomadblacky", "sample.foo");
  }

  @Test
  void getAsyncEnabled() {
    assertThat(FACTORY.getAsyncEnabled(null)).isFalse();
  }

  @Test
  void getBufferedConnectionShutdownTimeout() {
    assertThat(FACTORY.getBufferedConnectionShutdownTimeout(null)).isEqualTo(300000L);
  }

  @Test
  void getBufferedConnectionGracefulShutdownEnabled() {
    assertThat(FACTORY.getBufferedConnectionGracefulShutdownEnabled(null)).isFalse();
  }

  @Test
  void getBufferFlushtime() {
    assertThat(FACTORY.getBufferFlushtime(null)).isEqualTo(200000L);
  }

  @Test
  void getAsyncShutdownTimeout() {
    assertThat(FACTORY.getAsyncShutdownTimeout(null)).isEqualTo(500L);
  }

  @Test
  void getAsyncGracefulShutdownEnabled() {
    assertThat(FACTORY.getAsyncGracefulShutdownEnabled(null)).isFalse();
  }

  @Test
  void getAsyncQueueSize() {
    assertThat(FACTORY.getAsyncQueueSize(null)).isEqualTo(200);
  }

  @Test
  void getAsyncPriority() {
    assertThat(FACTORY.getAsyncPriority(null)).isEqualTo(9);
  }

  @Test
  void getAsyncThreads() {
    assertThat(FACTORY.getAsyncThreads(null)).isEqualTo(3);
  }

  @Test
  void getSampleRate() {
    assertThat(FACTORY.getSampleRate(null)).isEqualTo(0.75);
  }

  @Test
  void getProxyPort() {
    assertThat(FACTORY.getProxyPort(null)).isEqualTo(8888);
  }

  @Test
  void getProxyHost() {
    assertThat(FACTORY.getProxyHost(null)).isEqualTo("proxy.org");
  }

  @Test
  void getProxyUser() {
    assertThat(FACTORY.getProxyUser(null)).isEqualTo("username");
  }

  @Test
  void getProxyPass() {
    assertThat(FACTORY.getProxyPass(null)).isEqualTo("pass");
  }

  @Test
  void getRelease() {
    assertThat(FACTORY.getRelease(null)).isEqualTo("0.1.0");
  }

  @Test
  void getDist() {
    assertThat(FACTORY.getDist(null)).isEqualTo("x86");
  }

  @Test
  void getEnvironment() {
    assertThat(FACTORY.getEnvironment(null)).isEqualTo("development");
  }

  @Test
  void getServerName() {
    assertThat(FACTORY.getServerName(null)).isEqualTo("dev-server");
  }

  @Test
  void getTags() {
    assertThat(FACTORY.getTags(null))
        .containsOnly(entry("key1", "value1"), entry("key2", "value2"));
  }

  @Test
  void getMdcTags() {
    assertThat(FACTORY.getMdcTags(null)).containsOnly("mdcTag1", "mdcTag2");
  }

  @Test
  void getExtra() {
    assertThat(FACTORY.getExtra(null))
        .containsOnly(entry("ex1", "exValue1"), entry("ex2", "exValue2"));
  }

  @Test
  void getCompressionEnabled() {
    assertThat(FACTORY.getCompressionEnabled(null)).isFalse();
  }

  @Test
  void getHideCommonFramesEnabled() {
    assertThat(FACTORY.getHideCommonFramesEnabled(null)).isFalse();
  }

  @Test
  void getMaxMessageLength() {
    assertThat(FACTORY.getMaxMessageLength(null)).isEqualTo(2000);
  }

  @Test
  void getTimeout() {
    assertThat(FACTORY.getTimeout(null)).isEqualTo(3000);
  }

  @Test
  void getBufferEnabled() {
    assertThat(FACTORY.getBufferEnabled(null)).isFalse();
  }

  @Test
  void getBuffer() {
    Buffer expect = new DiskBuffer(new File("./buffer"), 100);
    assertThat(FACTORY.getBuffer(null)).isEqualToComparingFieldByField(expect);
  }

  @Test
  void getBufferSize() {
    assertThat(FACTORY.getBufferSize(null)).isEqualTo(100);
  }

  @Test
  void getUncaughtHandlerEnabled() {
    assertThat(FACTORY.getUncaughtHandlerEnabled(null)).isFalse();
  }

  @Test
  void configToMap() {
    Config config = ConfigFactory.parseString("hoge = { invalid = 1234.56 }");
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> SentryClientFactory.configToMap(config))
        .withMessageContaining("Invalid configuration type (NUMBER) of \"hoge.invalid\"");
  }
}
