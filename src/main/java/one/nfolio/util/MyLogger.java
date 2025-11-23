package one.nfolio.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLogger {
  private static Logger logger;

  public static void init() {
    logger = LoggerFactory.getLogger("Rattel");
  }

  public static Logger logger() {
    return logger;
  }
}
