package one.nfolio.rattel;

import com.github.javakeyring.PasswordAccessException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.Getter;

import one.nfolio.util.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class HelloApplication extends Application {
  private Stage stage;
  @Getter
  private static HelloApplication instance;

  public HelloApplication() {
    instance = this;
  }

  @Override
  public void start(Stage stage) throws IOException {
    MyLogger.init();
    SecureStorage.createKeyRing();

    try {
      AuthServer.init();
    } catch (PasswordAccessException | URISyntaxException e) {
      if(e instanceof PasswordAccessException) {
        MyLogger.logger().error(e.toString());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Macの場合は、KeyChane\nWindowsの場合はCredential Lockerに対しての権限を確認してください");
        alert.showAndWait();
      } else {
        ExceptionAlert alert = new ExceptionAlert();
        alert.show(e);
      }
    }

    this.stage = stage;
    Font.loadFont(
            getClass().getResourceAsStream("/fonts/GN-KillGothic-U-KanaO.ttf"), 16
    );
    Font.loadFont(
            getClass().getResourceAsStream("/fonts/ShipporiGothicB2-Bold.ttf"), 16
    );
    Font.loadFont(
            getClass().getResourceAsStream("/fonts/Manrope-SemiBold.ttf"), 16
    );

    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
    this.stage.setTitle("ラッテル");
    this.stage.setScene(scene);
    this.stage.show();
  }

  public Stage getStage() {
    return this.stage;
  }
}
