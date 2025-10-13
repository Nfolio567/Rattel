package one.nfolio.rattel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    Font font = Font.loadFont(
      getClass().getResourceAsStream("/fonts/GN-KillGothic-U-KanaO.ttf"), 16
    );
    System.out.printf("font: %s\n", font);
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
    stage.setTitle("Hello!");
    stage.setScene(scene);
    stage.show();
  }
}
