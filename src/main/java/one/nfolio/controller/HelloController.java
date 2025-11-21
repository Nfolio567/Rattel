package one.nfolio.controller;

import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.Getter;
import one.nfolio.rattel.HelloApplication;
import one.nfolio.util.ConfigResolver;
import one.nfolio.util.ExceptionAlert;
import one.nfolio.util.SoundConfig;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public class HelloController {
  @FXML
  private Pane superRoot;

  @FXML
  private AnchorPane root;

  @FXML
  private Text title;

  @FXML
  private Button startButton;

  @FXML
  private Button settingButton;

  @FXML
  private ImageView backgroundCard0;

  @FXML
  private ImageView backgroundCard1;

  @FXML
  private ImageView backgroundCard2;

  @FXML
  private ImageView backgroundCard3;

  private boolean isSettingShow = false;

  private Parent settingView;

  @FXML
  public void initialize() {
    root.setOpacity(0);
    title.setStyle("-fx-font-family:  \"GN-KMBFont-UB-OldstyleKana\";");
    startButton.setStyle("-fx-font-size: 5em;");
    settingButton.setStyle("-fx-font-size: 5em");

    Stage stage = HelloApplication.getInstance().getStage();
    final ImageView[] backgroundCards = {backgroundCard0, backgroundCard1, backgroundCard2, backgroundCard3,};
    final Image[] cards = {
            new Image(Objects.requireNonNull(getClass().getResource("/cards/doon/blueDoon5.png")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/cards/ank/yellowAnk2.png")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/cards/hatiron/pinkHatiron3.png")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/cards/yaar/greenYaar.png")).toExternalForm())
    }; // 初期画面の背景のカードの準備

    for (int i=0;i< cards.length;i++) {
      backgroundCards[i].setImage(cards[i]);
      backgroundCards[i].fitWidthProperty().bind(root.widthProperty().multiply(0.3359375));
      backgroundCards[i].fitHeightProperty().bind(root.heightProperty().multiply(0.7958333));
    } // 画像セット&仮想ウィンドウにカードサイズをいい感じに合わせるようにする
      // (マジックナンバーは、初期設定での仮想ウィンドウとカードのHとWの割合から算出したもの。みればの通り大体3割と8割。
      // 細かく指定しとかないと、案外不自然になっちゃう。あとは自己満⭐）

    stage.setOnShown(_ ->  {
      Screen screen = Screen.getPrimary();
      Rectangle2D bounds = screen.getBounds();
      double screenH = bounds.getHeight();
      double screenW = bounds.getWidth();

      if(screenW > screenH) {
        stage.setHeight(screen.getBounds().getHeight() * 0.8);
        stage.setWidth(stage.getHeight() * 15 / 9);
      } else if(screenH > screenW) {
        stage.setWidth(screen.getBounds().getWidth() * 0.8);
        stage.setHeight(stage.getWidth() * 9 / 15);
      } // ウィンドウサイズ最適化（何やってるかは見ればわかる）
      stage.setX(bounds.getMinX() + (screenW - stage.getWidth()) / 2);
      stage.setY(bounds.getMinY() + (screenH - stage.getHeight()) / 2); // 中心に持ってくる

      title.setX((root.getWidth() - title.getWrappingWidth()) / 2 + title.getFont().getSize() / 3); // 「ラッテル」を中央揃え

      Platform.runLater(() -> {
        root.setLayoutX((stage.getWidth() - root.getWidth()) / 2);
        double titleBar = stage.getHeight() - superRoot.getHeight();
        root.setLayoutY(((stage.getHeight() - titleBar) - root.getHeight()) / 2); // 仮想ウィンドウを中心へ

        SoundConfig.startBGM();

        // ボタンのサイズと位置の最適化
        DoubleBinding buttonWidth = root.widthProperty().multiply(0.253125);
        DoubleBinding buttonHeight = root.heightProperty().multiply(0.15972222);
        startButton.prefWidthProperty().bind(buttonWidth);
        startButton.maxWidthProperty().bind(buttonWidth);
        startButton.minWidthProperty().bind(buttonWidth);
        startButton.prefHeightProperty().bind(buttonHeight);
        startButton.maxHeightProperty().bind(buttonHeight);
        startButton.minHeightProperty().bind(buttonHeight);

        settingButton.prefWidthProperty().bind(buttonWidth);
        settingButton.maxWidthProperty().bind(buttonWidth);
        settingButton.minWidthProperty().bind(buttonWidth);
        settingButton.prefHeightProperty().bind(buttonHeight);
        settingButton.maxHeightProperty().bind(buttonHeight);
        settingButton.minHeightProperty().bind(buttonHeight);

        startButton.setLayoutX((root.getPrefWidth() - startButton.getPrefWidth()) / 2);
        settingButton.setLayoutX((root.getPrefWidth() - settingButton.getPrefWidth()) / 2);

        root.setOpacity(1);
      });
    });

    stage.widthProperty().addListener((_, _, newValue) -> {
      root.setLayoutX((newValue.doubleValue() - root.getWidth()) / 2); // どんな時でも仮想ウィンドウは中心へ
    });

    stage.heightProperty().addListener((_, _, newValue) -> {
      double titleBarHeight = newValue.doubleValue() - superRoot.getHeight();
      root.setLayoutY(((newValue.doubleValue() - titleBarHeight) - root.getHeight()) / 2); // 上記と同じ
    });

    root.layoutBoundsProperty().addListener((_, _, newValue) -> {
      stage.setMinHeight(newValue.getHeight());
      stage.setMinWidth(newValue.getWidth()); // 仮想ウィンドウよりも実ウィンドウがちっちゃくならないように
    });
  }

  @FXML
  public void onStart() throws IOException {
    SoundConfig.clickButton();
    Parent view = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("prepareRoom.fxml")));
    root.getChildren().add(view);
    root.setOnKeyPressed(event -> {
      if(event.getCode() == KeyCode.ESCAPE) {
        root.getChildren().remove(view);
        root.setOnKeyPressed(null);
      }
    });
  }

  @FXML
  public void goSetting() throws IOException {
    isSettingShow = !isSettingShow;
    if(isSettingShow) {
      settingView = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("setting.fxml")));
      root.getChildren().add(settingView);
      root.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.ESCAPE) {
          root.getChildren().remove(settingView);
          root.setOnKeyPressed(null);
          isSettingShow = false;
        }
      });
    } else {
      root.getChildren().remove(settingView);
      root.setOnKeyPressed(null);
    }
  }
}
