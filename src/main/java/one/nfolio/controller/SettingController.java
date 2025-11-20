package one.nfolio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

import one.nfolio.util.ConfigResolver;
import one.nfolio.util.ExceptionAlert;
import one.nfolio.util.Setting;

import javax.sound.sampled.FloatControl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

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
      superRoot.setPrefWidth(parentNode.getPrefWidth() / 2);
      superRoot.setPrefHeight(parentNode.getPrefHeight() / 2);
      superRoot.setLayoutX((parentNode.getWidth()-superRoot.getPrefWidth()) / 2);
      superRoot.setLayoutY((parentNode.getHeight()-superRoot.getPrefHeight()) / 2);
      separator.setPrefWidth(superRoot.getPrefWidth());

      bgmVolume.setPrefWidth(superRoot.getPrefWidth() * 0.8);
      bgmVolume.setLayoutX((superRoot.getPrefWidth()-bgmVolume.getWidth()) / 2);
      micVolume.setPrefWidth(superRoot.getPrefWidth() * 0.8);
      micVolume.setLayoutX((superRoot.getPrefWidth()-micVolume.getWidth()) / 2);

      try {
        bgmVolume.setValue(ConfigResolver.getGain());
      } catch (IOException e) {
        ExceptionAlert alert = new ExceptionAlert();
        alert.show(e);
      }

      superRoot.setOpacity(1);
      System.out.printf("ParentW: %f, ParentH: %f, rootW: %f, rootH: %f", parentNode.getWidth(), parentNode.getHeight(), superRoot.getWidth(), superRoot.getHeight());
      System.out.printf("X: %f, Y: %f\n", superRoot.getLayoutX(), superRoot.getLayoutY());
    });

    bgmVolume.valueProperty().addListener((_, _, newValue) -> {
      FloatControl gainControl = HelloController.getInstance().getGainControl();
      gainControl.setValue((float) (20 * Math.log10((double) newValue == 0 ? 0.0001 : (double) newValue)) + gainControl.getMaximum());
      ObjectMapper mapper = new ObjectMapper();
      try {
        Setting setting = mapper.readValue(new File(Paths.get(String.valueOf(ConfigResolver.getConfigPath()), "setting.json").toUri()), Setting.class);
        setting.getSound().setBgmVolume((double) newValue);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(Paths.get(String.valueOf(ConfigResolver.getConfigPath()), "setting.json").toUri()), setting);
      } catch (IOException e) {
        ExceptionAlert alert = new ExceptionAlert();
        alert.show(e);
      }

    });

  }
}
