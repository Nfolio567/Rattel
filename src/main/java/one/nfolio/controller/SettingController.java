package one.nfolio.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import one.nfolio.util.ConfigResolver;

import javax.sound.sampled.FloatControl;
import java.io.IOException;

public class SettingController {
  @FXML
  private AnchorPane superRoot;

  @FXML
  private Separator separator;

  @FXML
  private Slider bgmVolume;

  @FXML
  private Slider micVolume;


  public void initialize() {
    superRoot.setOpacity(0);
    Platform.runLater(() -> {
      AnchorPane parentNode = (AnchorPane) superRoot.getParent();
      superRoot.setPrefWidth(parentNode.getWidth() / 2);
      superRoot.setPrefHeight(parentNode.getHeight() / 2);
      superRoot.setLayoutX((parentNode.getWidth()-superRoot.getWidth()) / 2);
      superRoot.setLayoutY((parentNode.getHeight()-superRoot.getHeight()) / 2);
      separator.setPrefWidth(superRoot.getPrefWidth());

      bgmVolume.setPrefWidth(superRoot.getPrefWidth() * 0.8);
      bgmVolume.setLayoutX((superRoot.getWidth()-bgmVolume.getWidth()) / 2);
      micVolume.setPrefWidth(superRoot.getWidth() * 0.8);
      micVolume.setLayoutX((superRoot.getWidth()-micVolume.getWidth()) / 2);

      superRoot.setOpacity(1);
    });

    bgmVolume.valueProperty().addListener((_, _, newValue) -> {
      FloatControl gainControl = HelloController.getInstance().getGainControl();
      try {
        float lineValue = ConfigResolver.getGain();
        gainControl.setValue((float) (20 * Math.log10(lineValue == 0 ? 0.00000000001 : lineValue)) + gainControl.getMaximum());
      } catch (IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(e.getClass().getSimpleName());
        alert.setContentText(e.toString());
        alert.showAndWait();
      }

    });
  }
}
