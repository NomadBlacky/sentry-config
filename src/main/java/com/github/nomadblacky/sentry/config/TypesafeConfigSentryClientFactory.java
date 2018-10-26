package com.github.nomadblacky.sentry.config;

import com.typesafe.config.Config;

public class TypesafeConfigSentryClientFactory extends DefaultTypesafeConfigSentryClientFactory {

  /**
   * Entry point from sentry-java.
   *
   * @throws com.typesafe.config.ConfigException.Missing if "sentry" path is absent or null in
   *     default configuration
   * @throws com.typesafe.config.ConfigException.WrongType if "sentry" path is not convertible to a
   *     Config in default configuration
   */
  public TypesafeConfigSentryClientFactory() {
    super();
  }

  /**
   * Initialize by specified configuration.
   *
   * @param config configuration
   */
  public TypesafeConfigSentryClientFactory(Config config) {
    super(config);
  }
}
