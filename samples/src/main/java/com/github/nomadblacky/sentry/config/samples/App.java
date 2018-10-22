package com.github.nomadblacky.sentry.config.samples;

import io.sentry.Sentry;
import io.sentry.event.Event;
import io.sentry.event.EventBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class App {

  private static void manualUsage() {
    EventBuilder eventBuilder =
        new EventBuilder().withMessage("This is a test!").withLevel(Event.Level.INFO);

    Sentry.capture(eventBuilder);
  }

  private static void withLogback() {
    Logger logger = LoggerFactory.getLogger(App.class);

    try (MDC.MDCCloseable closeable = MDC.putCloseable("sampleMdcTag", "Hey!")) {
      logger.warn("WARN from Logback!");
    }
  }

  private static void uncaughtException() {
    throw new RuntimeException("This is an uncaught exception!");
  }

  public static void main(String[] args) {
    manualUsage();
    withLogback();
    uncaughtException();
  }
}
