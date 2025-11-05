package one.nfolio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import one.nfolio.rattel.HelloApplication;
import one.nfolio.util.ConfigResolver;
import one.nfolio.util.Setting;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Objects;


public class HelloController {
  private static HelloController instance;

  private static FloatControl gainControl;

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

  @FXML
  public void initialize() {
    instance = this;

    title.setStyle("-fx-font-family:  \"GN-KMBFont-UB-OldstyleKana\";");
    startButton.setStyle("-fx-font-size: 70px;");
    settingButton.setStyle("-fx-font-size: 70px");

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

        try { // 以下、bgm処理
          Clip clip = AudioSystem.getClip();
          InputStream rawBGM = Objects.requireNonNull(getClass().getResourceAsStream("/media/bgm.wav"));
          clip.open(AudioSystem.getAudioInputStream(new BufferedInputStream(rawBGM)));

          gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
          float lineValue = ConfigResolver.getGain();
          gainControl.setValue((float) (20 * Math.log10(lineValue == 0 ? 0.00000000001 : lineValue)) + gainControl.getMaximum());
          clip.loop(Clip.LOOP_CONTINUOUSLY);
          clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
          throw new RuntimeException(e);
        }
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
  public void onStartRecording() {

  }

  @FXML
  public void goSetting() throws IOException {
    Parent settingView = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("setting.fxml")));
    root.getChildren().add(settingView);
  }

  public static HelloController getInstance() {
    return instance;
  }

  public FloatControl getGainControl() {
    return gainControl;
  }
}
