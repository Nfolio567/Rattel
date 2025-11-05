package one.nfolio.rattel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
  private Stage stage;
  private static HelloApplication instance;

  public HelloApplication() {
    instance = this;
  }

  @Override
  public void start(Stage stage) throws IOException {
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

  public static HelloApplication getInstance() {
    return instance;
  }

  public Stage getStage() {
    return this.stage;
  }
}
