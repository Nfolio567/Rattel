package one.nfolio.util;

import com.github.javakeyring.BackendNotSupportedException;
import com.github.javakeyring.Keyring;
import lombok.Getter;

public class SecureStorage {
  @Getter
  private static Keyring keyRing;

  public static void createKeyRing() {
    try {
      keyRing = Keyring.create();
    } catch (BackendNotSupportedException e) {
      ExceptionAlert alert = new ExceptionAlert();
      alert.show(e);
    }
  }
}
