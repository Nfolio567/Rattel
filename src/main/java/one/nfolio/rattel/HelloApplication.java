package one.nfolio.rattel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
  private Scene scene;
  private Stage stage;
  private static HelloApplication instance;

  public HelloApplication() {
    instance = this;
  }

  @Override
  public void start(Stage stage) throws IOException {
    this.stage = stage;
    Font killFont = Font.loadFont(
      getClass().getResourceAsStream("/fonts/GN-KillGothic-U-KanaO.ttf"), 16
    );
    Font shipporiFont = Font.loadFont(
            getClass().getResourceAsStream("/fonts/ShipporiGothicB2-Bold.ttf"), 16
    );
    Font manropeFont = Font.loadFont(
            getClass().getResourceAsStream("/fonts/Manrope-SemiBold.ttf"), 16
    );
    System.out.printf("killFont: %s\n", killFont);
    System.out.printf("shipporiFont: %s\n", shipporiFont);
    System.out.printf("Manrope: %s\n", manropeFont);

    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
    this.scene = new Scene(fxmlLoader.load(), 1280, 720);
    this.stage.setTitle("ラッテル");
    this.stage.setScene(scene);
    this.stage.show();
  }

  public static HelloApplication getInstance() {
    return instance;
  }

  public Scene getScene() {
    return this.scene;
  }

  public Stage getStage() {
    return this.stage;
  }
}
