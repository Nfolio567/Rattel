package one.nfolio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.github.javakeyring.Keyring;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import one.nfolio.util.ConfigResolver;
import one.nfolio.util.ExceptionAlert;
import one.nfolio.pojo.Setting;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class PrepareRoomController {
  @FXML
  private Pane superRoot;

  @FXML
  private Pane root;

  @FXML
  public void initialize() {
    superRoot.setOpacity(0);
    ObjectMapper mapper = new ObjectMapper();
    try {
      Setting setting = mapper.readValue(new File(Paths.get(String.valueOf(ConfigResolver.getConfigPath()), "setting.json").toUri()), Setting.class);
      if(setting.getUserName() == null) {
        superRoot.getChildren().remove(root);

        Text text = new Text("(新規)ユーザー名を入力");
        text.setLayoutX(32);
        text.setLayoutY(103);
        text.setStyle("-fx-font-size: 50px; -fx-fill: #fff;");

        TextField field = new TextField();
        field.setPromptText("ユーザー名");
        field.setLayoutX(105);
        field.setLayoutY(150);
        field.setPrefWidth(390);
        field.setPrefHeight(59);
        field.setStyle("-fx-font-size: 40px");

        Button done = new Button("確定");
        done.setLayoutX(204);
        done.setLayoutY(300);
        done.setPrefWidth(192);
        done.setPrefHeight(59);
        done.setOnAction(event -> {

        });

        superRoot.getChildren().add(text);
        superRoot.getChildren().add(field);
        superRoot.getChildren().add(done);
      }
    } catch (IOException e) {
      ExceptionAlert alert = new ExceptionAlert();
      alert.show(e);
    }

    Platform.runLater(() -> {
      Pane parent = (Pane) superRoot.getParent();
      superRoot.setLayoutX((parent.getPrefWidth() - superRoot.getPrefWidth()) / 2);
      superRoot.setLayoutY((parent.getPrefHeight() - superRoot.getPrefHeight()) / 2);
      superRoot.setOpacity(1);
    });
  }

  @FXML
  public void onClickCreateRoom() {
    superRoot.getChildren().remove(root);
    Text text = new Text("ルームを作成");
    text.setLayoutX(120);
    text.setLayoutY(103);
    text.setStyle("-fx-font-size: 60px; -fx-fill: #fff;");

    TextField field = new TextField();
    field.setPromptText("ルーム名");
    field.setLayoutX(105);
    field.setLayoutY(150);
    field.setPrefWidth(390);
    field.setPrefHeight(59);
    field.setStyle("-fx-font-size: 40px");

    Button button = new Button("作成");
    button.setLayoutX(204);
    button.setLayoutY(300);
    button.setPrefWidth(192);
    button.setPrefHeight(59);

    superRoot.getChildren().add(text);
    superRoot.getChildren().add(field);
    superRoot.getChildren().add(button);
  }
}
