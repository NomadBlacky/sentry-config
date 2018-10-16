package com.github.nomadblacky.sentry.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueType;
import io.sentry.DefaultSentryClientFactory;
import io.sentry.SentryClient;
import io.sentry.buffer.Buffer;
import io.sentry.context.ContextManager;
import io.sentry.dsn.Dsn;

import java.util.*;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SentryClientFactory extends DefaultSentryClientFactory {

    /**
     * Entry point from sentry-java.
     */
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

        // Tags
        tryToConfigMapValue(TAGS_OPTION, client::setTags);

        // MDC Tags
        tryToConfigureStringListValue(MDCTAGS_OPTION, mdctags -> client.setMdcTags(new HashSet<>(mdctags)));

        // Extra Data
        tryToConfigMapValue(EXTRA_OPTION, map -> {
            client.setExtra(map.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())));
        });

        // Same Frame as Enclosing Exception
        tryToConfigureBooleanValue(HIDE_COMMON_FRAMES_OPTION, settingSentryProperty(HIDE_COMMON_FRAMES_OPTION));

        // Event Sampling
        tryToConfigureDoubleValue(SAMPLE_RATE_OPTION, settingSentryProperty(SAMPLE_RATE_OPTION));

        // Uncaught Exception Handler
        tryToConfigureBooleanValue(UNCAUGHT_HANDLER_ENABLED_OPTION, settingSentryProperty(UNCAUGHT_HANDLER_ENABLED_OPTION));

        // Buffering
        tryToConfigureBooleanValue(BUFFER_ENABLED_OPTION, settingSentryProperty(BUFFER_ENABLED_OPTION));
        tryToConfigreStringValue(BUFFER_DIR_OPTION, settingSentryProperty(BUFFER_DIR_OPTION));
        tryToConfigureIntValue(BUFFER_SIZE_OPTION, settingSentryProperty(BUFFER_SIZE_OPTION));
        tryToConfigureLongValue(BUFFER_FLUSHTIME_OPTION, settingSentryProperty(BUFFER_FLUSHTIME_OPTION));
        tryToConfigureLongValue(BUFFER_SHUTDOWN_TIMEOUT_OPTION, settingSentryProperty(BUFFER_SHUTDOWN_TIMEOUT_OPTION));
        tryToConfigureBooleanValue(BUFFER_GRACEFUL_SHUTDOWN_OPTION, settingSentryProperty(BUFFER_GRACEFUL_SHUTDOWN_OPTION));

        // Async Connection
        tryToConfigureBooleanValue("async.enabled", settingSentryProperty(ASYNC_OPTION));
        tryToConfigureLongValue(ASYNC_SHUTDOWN_TIMEOUT_OPTION, settingSentryProperty(ASYNC_SHUTDOWN_TIMEOUT_OPTION));

        return configureSentryClient(client, defaultDsn);
    }

    @Override
    protected ContextManager getContextManager(Dsn dsn) {
        return super.getContextManager(dsn);
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
            List<String> packages = Arrays
                    .stream(value.split(","))
                    .filter(pkg -> !pkg.trim().equals(""))
                    .collect(Collectors.toList());
            return packages;
        } else if (configValue.valueType() == ConfigValueType.LIST) {
            List<String> pkgs = config.getStringList(IN_APP_FRAMES_OPTION);
            return pkgs
                    .stream()
                    .filter(pkg -> !pkg.trim().equals(""))
                    .collect(Collectors.toList());
        }
        return super.getInAppFrames(dsn);
    }

    @Override
    protected boolean getAsyncEnabled(Dsn dsn) {
        return super.getAsyncEnabled(dsn);
    }

    @Override
    protected RejectedExecutionHandler getRejectedExecutionHandler(Dsn dsn) {
        return super.getRejectedExecutionHandler(dsn);
    }

    @Override
    protected long getBufferedConnectionShutdownTimeout(Dsn dsn) {
        return super.getBufferedConnectionShutdownTimeout(dsn);
    }

    @Override
    protected boolean getBufferedConnectionGracefulShutdownEnabled(Dsn dsn) {
        return super.getBufferedConnectionGracefulShutdownEnabled(dsn);
    }

    @Override
    protected long getBufferFlushtime(Dsn dsn) {
        return super.getBufferFlushtime(dsn);
    }

    @Override
    protected long getAsyncShutdownTimeout(Dsn dsn) {
        return super.getAsyncShutdownTimeout(dsn);
    }

    @Override
    protected boolean getAsyncGracefulShutdownEnabled(Dsn dsn) {
        return super.getAsyncGracefulShutdownEnabled(dsn);
    }

    @Override
    protected int getAsyncQueueSize(Dsn dsn) {
        return super.getAsyncQueueSize(dsn);
    }

    @Override
    protected int getAsyncPriority(Dsn dsn) {
        return super.getAsyncPriority(dsn);
    }

    @Override
    protected int getAsyncThreads(Dsn dsn) {
        return super.getAsyncThreads(dsn);
    }

    @Override
    protected boolean getBypassSecurityEnabled(Dsn dsn) {
        return super.getBypassSecurityEnabled(dsn);
    }

    @Override
    protected Double getSampleRate(Dsn dsn) {
        return super.getSampleRate(dsn);
    }

    @Override
    protected int getProxyPort(Dsn dsn) {
        return super.getProxyPort(dsn);
    }

    @Override
    protected String getProxyHost(Dsn dsn) {
        return tryToGetString(HTTP_PROXY_HOST_OPTION).orElseGet(() -> super.getProxyHost(dsn));
    }

    @Override
    protected String getProxyUser(Dsn dsn) {
        return tryToGetString(HTTP_PROXY_USER_OPTION).orElseGet(() -> super.getProxyUser(dsn));
    }

    @Override
    protected String getProxyPass(Dsn dsn) {
        return tryToGetString(HTTP_PROXY_PASS_OPTION).orElseGet(() -> super.getProxyPass(dsn));
    }

    @Override
    protected String getRelease(Dsn dsn) {
        return tryToGetString(RELEASE_OPTION).orElseGet(() -> super.getRelease(dsn));
    }

    @Override
    protected String getDist(Dsn dsn) {
        return tryToGetString(DIST_OPTION).orElseGet(() -> super.getDist(dsn));
    }

    @Override
    protected String getEnvironment(Dsn dsn) {
        return tryToGetString(ENVIRONMENT_OPTION).orElseGet(() -> super.getEnvironment(dsn));
    }

    @Override
    protected String getServerName(Dsn dsn) {
        return tryToGetString(SERVERNAME_OPTION).orElseGet(() -> super.getServerName(dsn));
    }

    @Override
    protected Map<String, String> getTags(Dsn dsn) {
        return super.getTags(dsn);
    }

    @Override
    protected Set<String> getExtraTags(Dsn dsn) {
        return super.getExtraTags(dsn);
    }

    @Override
    protected Set<String> getMdcTags(Dsn dsn) {
        return super.getMdcTags(dsn);
    }

    @Override
    protected Map<String, String> getExtra(Dsn dsn) {
        return super.getExtra(dsn);
    }

    @Override
    protected boolean getCompressionEnabled(Dsn dsn) {
        return super.getCompressionEnabled(dsn);
    }

    @Override
    protected boolean getHideCommonFramesEnabled(Dsn dsn) {
        return super.getHideCommonFramesEnabled(dsn);
    }

    @Override
    protected int getMaxMessageLength(Dsn dsn) {
        return super.getMaxMessageLength(dsn);
    }

    @Override
    protected int getTimeout(Dsn dsn) {
        return super.getTimeout(dsn);
    }

    @Override
    protected boolean getBufferEnabled(Dsn dsn) {
        return super.getBufferEnabled(dsn);
    }

    @Override
    protected Buffer getBuffer(Dsn dsn) {
        return super.getBuffer(dsn);
    }

    @Override
    protected int getBufferSize(Dsn dsn) {
        return super.getBufferSize(dsn);
    }

    @Override
    protected boolean getUncaughtHandlerEnabled(Dsn dsn) {
        return super.getUncaughtHandlerEnabled(dsn);
    }

    private Optional<String> tryToGetString(String path) {
        if (config.hasPath(path)) {
            return Optional.of(config.getString(path));
        }
        return Optional.empty();
    }

    private void tryToConfigreStringValue(String path, Consumer<String> configProc) {
        tryToConfigure(path, configProc, () -> config.getString(path));
    }

    private void tryToConfigureStringListValue(String path, Consumer<List<String>> configProc) {
        tryToConfigure(path, configProc, () -> config.getStringList(path));
    }

    private void tryToConfigureIntValue(String path, Consumer<Integer> configProc) {
        tryToConfigure(path, configProc, () -> config.getInt(path));
    }

    private void tryToConfigureLongValue(String path, Consumer<Long> configProc) {
        tryToConfigure(path, configProc, () -> config.getLong(path));
    }

    private void tryToConfigureDoubleValue(String path, Consumer<Double> configProc) {
        tryToConfigure(path, configProc, () -> config.getDouble(path));
    }

    private void tryToConfigureBooleanValue(String path, Consumer<Boolean> configProc) {
        tryToConfigure(path, configProc, () -> config.getBoolean(path));
    }

    private void tryToConfigMapValue(String path, Consumer<Map<String, String>> configProc) {
        tryToConfigure(path, configProc, () -> configToMap(config.getConfig(path)));
    }

    private <T> void tryToConfigure(String path, Consumer<T> configProc, Supplier<T> valueSupplier) {
        if (config.hasPath(path)) {
            configProc.accept(valueSupplier.get());
        }
    }

    private static final String SENTRY_PROPERTY_PREFIX = "sentry.";

    private static <T> Consumer<T> settingSentryProperty(String suffix) {
        return obj -> System.setProperty(SENTRY_PROPERTY_PREFIX + suffix, obj.toString());
    }

    static Map<String, String> configToMap(Config config) {
        return config.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> {
            if (e.getValue().valueType() == ConfigValueType.STRING) {
                return config.getString(e.getKey());
            } else {
                throw new IllegalArgumentException(String.format(
                        "Invalid configuration type (%s) of \"%s\" in %s L%d. Please set with string.",
                        e.getValue().valueType().name(),
                        e.getKey(),
                        config.origin().filename(),
                        e.getValue().origin().lineNumber()
                ));
            }
        }));
    }
}
