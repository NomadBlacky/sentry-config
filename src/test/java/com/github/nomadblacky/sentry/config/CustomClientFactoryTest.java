package com.github.nomadblacky.sentry.config;

import static org.assertj.core.api.Assertions.assertThat;

import io.sentry.DefaultSentryClientFactory;
import io.sentry.SentryClient;
import io.sentry.dsn.Dsn;
import io.sentry.event.EventBuilder;
import io.sentry.event.helper.EventBuilderHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CustomClientFactoryTest {

  private static class CustomSentryClientFactory extends DefaultTypesafeConfigSentryClientFactory {
    public CustomSentryClientFactory() {}

    @Override
    public SentryClient createSentryClient(Dsn defaultDsn) {
      SentryClient client = super.createSentryClient(defaultDsn);
      client.addBuilderHelper(new CustomEventBuilderHelper());
      return client;
    }
  }

  private static class CustomEventBuilderHelper implements EventBuilderHelper {

    static boolean executed = false;

    @Override
    public void helpBuildingEvent(EventBuilder eventBuilder) {
      executed = true;
    }
  }

  private static SentryClient CLIENT;

  @BeforeAll
  static void setup() {
    DefaultSentryClientFactory FACTORY = new CustomSentryClientFactory();
    CLIENT = FACTORY.createSentryClient(null);
  }

  @Test
  void shouldBeReadConfiguration() {
    assertThat(CLIENT.getEnvironment()).isEqualTo("development");
  }

  @Test
  void shouldBeExecuteHelpBuildingEvent() {
    CLIENT.sendMessage("!!!");
    assertThat(CustomEventBuilderHelper.executed).isTrue();
  }
}
