package one.nfolio.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import one.nfolio.rattel.HelloApplication;

import java.io.IOException;
import java.util.Objects;

public class PrepareRoomController {
  @FXML
  private Pane superRoot;

  @FXML
  private Pane root;

  @FXML
  public void initialize() {
    superRoot.setOpacity(0);
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
    text.setLayoutY(78);
    text.setStyle("-fx-font-size: 60px; -fx-fill: #fff;");

    TextField roomNameField = new TextField();
    roomNameField.setPromptText("ルーム名");
    roomNameField.setLayoutX(105);
    roomNameField.setLayoutY(109);
    roomNameField.setPrefWidth(390);
    roomNameField.setPrefHeight(59);
    roomNameField.setStyle("-fx-font-size: 40px");

    TextField userNameField = new TextField();
    userNameField.setPromptText("ユーザー名");
    userNameField.setLayoutX(105);
    userNameField.setLayoutY(207);
    userNameField.setPrefWidth(390);
    userNameField.setPrefHeight(59);
    userNameField.setStyle("-fx-font-size: 40px");


    Button button = new Button("作成");
    button.setLayoutX(204);
    button.setLayoutY(310);
    button.setPrefWidth(192);
    button.setPrefHeight(59);

    superRoot.getChildren().add(text);
    superRoot.getChildren().add(roomNameField);
    superRoot.getChildren().add(userNameField);
    superRoot.getChildren().add(button);
  }
}
