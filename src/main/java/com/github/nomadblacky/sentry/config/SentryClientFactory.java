package com.github.nomadblacky.sentry.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueType;
import io.sentry.DefaultSentryClientFactory;
import io.sentry.SentryClient;
import io.sentry.buffer.Buffer;
import io.sentry.buffer.DiskBuffer;
import io.sentry.dsn.Dsn;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class SentryClientFactory extends DefaultSentryClientFactory {

  /** Entry point from sentry-java. */
  public SentryClientFactory() {}

  /**
   * Initialize by specified configuration.
   *
   * @param config configuration
   */
  public SentryClientFactory(Config config) {
    this.config = config;
  }

  // FIXME: 18/09/09 This variable is to only use for tests.
  String dsnString;

  private Config config;

  @Override
  public SentryClient createSentryClient(Dsn defaultDsn) {
    if (this.config == null) {
      config = ConfigFactory.load().getConfig("sentry");
    }

    // DSN
    Dsn dsn = defaultDsn;
    if (config.hasPath("dsn")) {
      dsnString = config.getString("dsn");
      dsn = new Dsn(dsnString);
    }

    SentryClient client = new SentryClient(createConnection(dsn), getContextManager(dsn));

    return configureSentryClient(client, defaultDsn);
  }

  @Override
  protected Collection<String> getInAppFrames(Dsn dsn) {
    ConfigValue configValue;
    if (config.hasPath(IN_APP_FRAMES_OPTION)) {
      configValue = config.getValue(IN_APP_FRAMES_OPTION);
    } else {
      return super.getInAppFrames(dsn);
    }

    if (configValue.valueType() == ConfigValueType.STRING) {
      String value = config.getString(IN_APP_FRAMES_OPTION);
      List<String> packages =
          Arrays.stream(value.split(","))
              .filter(pkg -> !pkg.trim().equals(""))
              .collect(Collectors.toList());
      return packages;
    } else if (configValue.valueType() == ConfigValueType.LIST) {
      List<String> pkgs = config.getStringList(IN_APP_FRAMES_OPTION);
      return pkgs.stream().filter(pkg -> !pkg.trim().equals("")).collect(Collectors.toList());
    }
    return super.getInAppFrames(dsn);
  }

  @Override
  protected boolean getAsyncEnabled(Dsn dsn) {
    return tryToGetBoolean("async.enabled").orElseGet(() -> super.getAsyncEnabled(dsn));
  }

  @Override
  protected long getBufferedConnectionShutdownTimeout(Dsn dsn) {
    return tryToGetLong(BUFFER_SHUTDOWN_TIMEOUT_OPTION)
        .orElseGet(() -> super.getBufferedConnectionShutdownTimeout(dsn));
  }

  @Override
  protected boolean getBufferedConnectionGracefulShutdownEnabled(Dsn dsn) {
    return tryToGetBoolean(BUFFER_GRACEFUL_SHUTDOWN_OPTION)
        .orElseGet(() -> super.getBufferedConnectionGracefulShutdownEnabled(dsn));
  }

  @Override
  protected long getBufferFlushtime(Dsn dsn) {
    return tryToGetLong(BUFFER_FLUSHTIME_OPTION).orElseGet(() -> super.getBufferFlushtime(dsn));
  }

  @Override
  protected long getAsyncShutdownTimeout(Dsn dsn) {
    return tryToGetLong(ASYNC_SHUTDOWN_TIMEOUT_OPTION)
        .orElseGet(() -> super.getAsyncShutdownTimeout(dsn));
  }

  @Override
  protected boolean getAsyncGracefulShutdownEnabled(Dsn dsn) {
    return tryToGetBoolean(ASYNC_GRACEFUL_SHUTDOWN_OPTION)
        .orElseGet(() -> super.getAsyncGracefulShutdownEnabled(dsn));
  }

  @Override
  protected int getAsyncQueueSize(Dsn dsn) {
    return tryToGetInteger(ASYNC_QUEUE_SIZE_OPTION).orElseGet(() -> super.getAsyncQueueSize(dsn));
  }

  @Override
  protected int getAsyncPriority(Dsn dsn) {
    return tryToGetInteger(ASYNC_PRIORITY_OPTION).orElseGet(() -> super.getAsyncPriority(dsn));
  }

  @Override
  protected int getAsyncThreads(Dsn dsn) {
    return tryToGetInteger(ASYNC_THREADS_OPTION).orElseGet(() -> super.getAsyncThreads(dsn));
  }

  @Override
  protected Double getSampleRate(Dsn dsn) {
    return tryToGetDouble(SAMPLE_RATE_OPTION).orElseGet(() -> super.getSampleRate(dsn));
  }

  @Override
  protected int getProxyPort(Dsn dsn) {
    return tryToGetInteger(HTTP_PROXY_PORT_OPTION).orElseGet(() -> super.getProxyPort(dsn));
  }

  @Override
  protected String getProxyHost(Dsn dsn) {
    return tryToGetNonEmptyString(HTTP_PROXY_HOST_OPTION).orElseGet(() -> super.getProxyHost(dsn));
  }

  @Override
  protected String getProxyUser(Dsn dsn) {
    return tryToGetNonEmptyString(HTTP_PROXY_USER_OPTION).orElseGet(() -> super.getProxyUser(dsn));
  }

  @Override
  protected String getProxyPass(Dsn dsn) {
    return tryToGetNonEmptyString(HTTP_PROXY_PASS_OPTION).orElseGet(() -> super.getProxyPass(dsn));
  }

  @Override
  protected String getRelease(Dsn dsn) {
    return tryToGetNonEmptyString(RELEASE_OPTION).orElseGet(() -> super.getRelease(dsn));
  }

  @Override
  protected String getDist(Dsn dsn) {
    return tryToGetNonEmptyString(DIST_OPTION).orElseGet(() -> super.getDist(dsn));
  }

  @Override
  protected String getEnvironment(Dsn dsn) {
    return tryToGetNonEmptyString(ENVIRONMENT_OPTION).orElseGet(() -> super.getEnvironment(dsn));
  }

  @Override
  protected String getServerName(Dsn dsn) {
    return tryToGetNonEmptyString(SERVERNAME_OPTION).orElseGet(() -> super.getServerName(dsn));
  }

  @Override
  protected Map<String, String> getTags(Dsn dsn) {
    return tryToGetMap(TAGS_OPTION).orElseGet(() -> super.getTags(dsn));
  }

  @Override
  protected Set<String> getMdcTags(Dsn dsn) {
    return tryToGetStringList(MDCTAGS_OPTION)
        .<Set<String>>map(HashSet::new)
        .orElseGet(() -> super.getMdcTags(dsn));
  }

  @Override
  protected Map<String, String> getExtra(Dsn dsn) {
    return tryToGetMap(EXTRA_OPTION).orElseGet(() -> super.getExtra(dsn));
  }

  @Override
  protected boolean getCompressionEnabled(Dsn dsn) {
    return tryToGetBoolean(COMPRESSION_OPTION).orElseGet(() -> super.getCompressionEnabled(dsn));
  }

  @Override
  protected boolean getHideCommonFramesEnabled(Dsn dsn) {
    return tryToGetBoolean(HIDE_COMMON_FRAMES_OPTION)
        .orElseGet(() -> super.getHideCommonFramesEnabled(dsn));
  }

  @Override
  protected int getMaxMessageLength(Dsn dsn) {
    return tryToGetInteger(MAX_MESSAGE_LENGTH_OPTION)
        .orElseGet(() -> super.getMaxMessageLength(dsn));
  }

  @Override
  protected int getTimeout(Dsn dsn) {
    return tryToGetInteger(TIMEOUT_OPTION).orElseGet(() -> super.getTimeout(dsn));
  }

  @Override
  protected boolean getBufferEnabled(Dsn dsn) {
    return tryToGetBoolean(BUFFER_ENABLED_OPTION).orElseGet(() -> super.getBufferEnabled(dsn));
  }

  @Override
  protected Buffer getBuffer(Dsn dsn) {
    return tryToGetNonEmptyString(BUFFER_DIR_OPTION)
        .<Buffer>map(s -> new DiskBuffer(new File(s), getBufferSize(dsn)))
        .orElseGet(() -> super.getBuffer(dsn));
  }

  @Override
  protected int getBufferSize(Dsn dsn) {
    return tryToGetInteger(BUFFER_SIZE_OPTION).orElseGet(() -> super.getBufferSize(dsn));
  }

  @Override
  protected boolean getUncaughtHandlerEnabled(Dsn dsn) {
    return tryToGetBoolean(UNCAUGHT_HANDLER_ENABLED_OPTION)
        .orElseGet(() -> super.getUncaughtHandlerEnabled(dsn));
  }

  private Optional<Integer> tryToGetInteger(String path) {
    if (config.hasPath(path)) {
      return Optional.of(config.getInt(path));
    }
    return Optional.empty();
  }

  private Optional<Long> tryToGetLong(String path) {
    if (config.hasPath(path)) {
      return Optional.of(config.getLong(path));
    }
    return Optional.empty();
  }

  private Optional<Double> tryToGetDouble(String path) {
    if (config.hasPath(path)) {
      return Optional.of(config.getDouble(path));
    }
    return Optional.empty();
  }

  private Optional<String> tryToGetNonEmptyString(String path) {
    if (config.hasPath(path)) {
      return Optional.of(config.getString(path)).filter(s -> !s.isEmpty());
    }
    return Optional.empty();
  }

  private Optional<Boolean> tryToGetBoolean(String path) {
    if (config.hasPath(path)) {
      return Optional.of(config.getBoolean(path));
    }
    return Optional.empty();
  }

  private Optional<List<String>> tryToGetStringList(String path) {
    if (config.hasPath(path)) {
      return Optional.of(config.getStringList(path));
    }
    return Optional.empty();
  }

  private Optional<Map<String, String>> tryToGetMap(String path) {
    if (config.hasPath(path)) {
      return Optional.of(config.getConfig(path)).map(SentryClientFactory::configToMap);
    }
    return Optional.empty();
  }

  static Map<String, String> configToMap(Config config) {
    return config
        .entrySet()
        .stream()
        .collect(
            Collectors.toMap(
                e -> e.getKey(),
                e -> {
                  if (e.getValue().valueType() == ConfigValueType.STRING) {
                    return config.getString(e.getKey());
                  } else {
                    throw new IllegalArgumentException(
                        String.format(
                            "Invalid configuration type (%s) of \"%s\" in %s L%d. Please set with string.",
                            e.getValue().valueType().name(),
                            e.getKey(),
                            config.origin().filename(),
                            e.getValue().origin().lineNumber()));
                  }
                }));
  }
}
