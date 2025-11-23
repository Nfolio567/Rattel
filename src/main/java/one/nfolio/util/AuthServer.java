package one.nfolio.util;

import com.github.javakeyring.Keyring;
import com.github.javakeyring.PasswordAccessException;
import javafx.scene.control.Alert;
import okhttp3.*;
import one.nfolio.rattel.Launcher;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Properties;

public class AuthServer {
  public static void init() throws URISyntaxException, IOException, PasswordAccessException {
    Keyring keyring = SecureStorage.getKeyRing();
    try {
      keyring.getPassword("rattel", "jwt-init-pwd");
    } catch (PasswordAccessException e) {
      if (e.getMessage().contains("No stored credentials match rattel account")) {
        System.out.println("init");
        SecureRandom sRandom = new SecureRandom();
        byte[] secureBytes = new byte[32];
        sRandom.nextBytes(secureBytes);
        String key = Base64.getUrlEncoder().withoutPadding().encodeToString(secureBytes);

        keyring.setPassword("rattel", "jwt-init-pwd", key);

        Properties properties = new Properties();
        properties.load(new FileInputStream(
                Paths.get(Launcher.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().toString()
                        + "/UUID.properties"));
        String UUID = properties.get("UUID").toString();

        OkHttpClient client = new OkHttpClient();
        MediaType json = MediaType.get("application/json; charset=utf-8");
        String contents = String.format("{\"uuid\": %s, \"password\": %s}", UUID, keyring.getPassword("rattel", "jwt-init-pwd"));
        RequestBody body = RequestBody.create(contents, json);

        Request request = new Request.Builder().url("http://localhost:9005/register-pwd").post(body).build();
        try (Response response = client.newCall(request).execute()) {
          System.out.println(response.code());
        }
      } else {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Macの場合は、KeyChane\nWindowsの場合はCredential Lockerに対しての権限を確認してください");
        alert.showAndWait();
      }
    }
  }
}
