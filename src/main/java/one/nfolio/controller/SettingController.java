package one.nfolio.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class SettingController {
  @FXML
  private AnchorPane superRoot;

  public void initialize() {
    Platform.runLater(() -> {
      AnchorPane parentNode = (AnchorPane) superRoot.getParent();
      superRoot.setPrefWidth(parentNode.getWidth());
      superRoot.setPrefHeight(parentNode.getHeight());
    });
  }
}
