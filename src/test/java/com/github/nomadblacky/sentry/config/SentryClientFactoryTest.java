package com.github.nomadblacky.sentry.config;

import static org.assertj.core.api.Assertions.*;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.sentry.DefaultSentryClientFactory;
import io.sentry.SentryClient;
import io.sentry.buffer.Buffer;
import io.sentry.buffer.DiskBuffer;
import io.sentry.dsn.Dsn;
import io.sentry.jvmti.FrameCache;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
  void testToInitializeServerName() {
    assertThat(CLIENT.getServerName()).isEqualTo("dev-server");
  }

  @Test
  void testToInitializeTags() {
    assertThat(FACTORY.getTags(null))
        .containsOnly(entry("key1", "value1"), entry("key2", "value2"));
  }

  @Test
  void testToInitializeMdcTags() {
    assertThat(FACTORY.getMdcTags(null)).containsOnly("mdcTag1", "mdcTag2");
  }

  @Test
  void testToInitializeExtraData() {
    assertThat(FACTORY.getExtra(null))
        .containsOnly(entry("ex1", "exValue1"), entry("ex2", "exValue2"));
  }

  @Test
  void testToInitializeInApplicationStackFrames() throws Exception {
    Field field = FrameCache.class.getDeclaredField("appPackages");
    field.setAccessible(true);
    Set<String> appPackages = (Set<String>) field.get(null);
    assertThat(appPackages).containsOnly("com.github.nomadblacky", "sample.foo");
  }

  @Test
  void testToInitializeHideCommon() {
    assertThat(FACTORY.getHideCommonFramesEnabled(null)).isFalse();
  }

  @Test
  void testToInitializeEventSamplingRate() throws Exception {
    Method method = DefaultSentryClientFactory.class.getDeclaredMethod("getSampleRate", Dsn.class);
    method.setAccessible(true);
    Double rate = (Double) method.invoke(FACTORY, (Dsn) null);
    assertThat(rate).isEqualTo(0.75);
  }

  @Test
  void testToInitializeUncaughtExceptionHandlerEnabled() {
    assertThat(FACTORY.getUncaughtHandlerEnabled(null)).isFalse();
  }

  @Test
  void testToInitializeBufferEnabled() throws Exception {
    assertThat(FACTORY.getBufferEnabled(null)).isFalse();
  }

  @Test
  void testToInitializeBufferDir() {
    Buffer expect = new DiskBuffer(new File("./buffer"), 100);
    assertThat(FACTORY.getBuffer(null)).isEqualToComparingFieldByField(expect);
  }

  @Test
  void testToInitializeBufferSize() throws Exception {
    Method method = DefaultSentryClientFactory.class.getDeclaredMethod("getBufferSize", Dsn.class);
    method.setAccessible(true);
    int bufferSize = (int) method.invoke(FACTORY, (Dsn) null);
    assertThat(bufferSize).isEqualTo(100);
  }

  @Test
  void testToInitializeBufferFlushTime() throws Exception {
    Method method =
        DefaultSentryClientFactory.class.getDeclaredMethod("getBufferFlushtime", Dsn.class);
    method.setAccessible(true);
    long bufferFlushTime = (long) method.invoke(FACTORY, (Dsn) null);
    assertThat(bufferFlushTime).isEqualTo(200000);
  }

  @Test
  void testToInitializeBufferShutdownTimeout() throws Exception {
    Method method =
        DefaultSentryClientFactory.class.getDeclaredMethod(
            "getBufferedConnectionShutdownTimeout", Dsn.class);
    method.setAccessible(true);
    long bufferFlushTime = (long) method.invoke(FACTORY, (Dsn) null);
    assertThat(bufferFlushTime).isEqualTo(300000);
  }

  @Test
  void testToInitializeGracefulShutdownEnabled() {
    assertThat(FACTORY.getBufferedConnectionGracefulShutdownEnabled(null)).isFalse();
  }

  @Test
  void testToInitializeAsyncEnabled() {
    assertThat(FACTORY.getAsyncEnabled(null)).isFalse();
  }

  @Test
  void testToIntializeAsyncShutdownTimeout() throws Exception {
    Method method =
        DefaultSentryClientFactory.class.getDeclaredMethod("getAsyncShutdownTimeout", Dsn.class);
    method.setAccessible(true);
    long isEnabled = (long) method.invoke(FACTORY, (Dsn) null);
    assertThat(isEnabled).isEqualTo(500L);
  }

  @Test
  void testConfigToMapThrowAnErrorIfSetInvalidConfigurationType() {
    Config config = ConfigFactory.parseString("hoge = { invalid = 1234.56 }");
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> SentryClientFactory.configToMap(config))
        .withMessageContaining("Invalid configuration type (NUMBER) of \"hoge.invalid\"");
  }
}
