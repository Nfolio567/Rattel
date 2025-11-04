package one.nfolio.controller;

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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import one.nfolio.rattel.HelloApplication;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Objects;


public class HelloController {
  private MediaPlayer player;

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
  public void initialize() throws URISyntaxException, LineUnavailableException, UnsupportedAudioFileException, IOException {
    title.setStyle("-fx-font-family:  \"GN-KMBFont-UB-OldstyleKana\";");
    startButton.setStyle("-fx-font-family: \"Shippori Gothic B2 Bold\"; -fx-font-size: 70px;");
    settingButton.setStyle("-fx-font-family: \"Shippori Gothic B2 Bold\"; -fx-font-size: 70px");

    Stage stage = HelloApplication.getInstance().getStage();
    final ImageView[] backgroundCards = {backgroundCard0, backgroundCard1, backgroundCard2, backgroundCard3,};
    final Image[] cards = {
            new Image(Objects.requireNonNull(getClass().getResource("/cards/doon/blueDoon5.png")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/cards/ank/yellowAnk2.png")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/cards/hatiron/pinkHatiron3.png")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/cards/yaar/greenYaar.png")).toExternalForm())
    };

    for (int i=0;i< cards.length;i++) {
      backgroundCards[i].setImage(cards[i]);
      backgroundCards[i].fitWidthProperty().bind(root.widthProperty().multiply(0.3359375));
      backgroundCards[i].fitHeightProperty().bind(root.heightProperty().multiply(0.7958333));
    }

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
      }
      stage.setX(bounds.getMinX() + (screenW - stage.getWidth()) / 2);
      stage.setY(bounds.getMinY() + (screenH - stage.getHeight()) / 2);

      title.setX((root.getWidth() - title.getWrappingWidth()) / 2 + title.getFont().getSize() / 3);

      Platform.runLater(() -> {
        root.setLayoutX((stage.getWidth() - root.getWidth()) / 2);
        double titleBar = stage.getHeight() - superRoot.getHeight();
        root.setLayoutY(((stage.getHeight() - titleBar) - root.getHeight()) / 2);
      });
    });

    stage.widthProperty().addListener((_, _, newValue) -> {
      root.setLayoutX((newValue.doubleValue() - root.getWidth()) / 2);
    });

    stage.heightProperty().addListener((_, _, newValue) -> {
      double titleBarHeight = newValue.doubleValue() - superRoot.getHeight();
      root.setLayoutY(((newValue.doubleValue() - titleBarHeight) - root.getHeight()) / 2);
    });

    root.layoutBoundsProperty().addListener((_, _, newValue) -> {
      stage.setMinHeight(newValue.getHeight());
      stage.setMinWidth(newValue.getWidth());
    });


    Clip clip = AudioSystem.getClip();
    InputStream rawBGM = Objects.requireNonNull(getClass().getResourceAsStream("/media/bgm.wav"));
    clip.open(AudioSystem.getAudioInputStream(new BufferedInputStream(rawBGM)));
    clip.loop(Clip.LOOP_CONTINUOUSLY);
    clip.start();

    /*Media media = new Media(getClass().getResource("/media/bgm.wav").toURI().toString());
    player = new MediaPlayer(media);
    player.setCycleCount(MediaPlayer.INDEFINITE);
    player.play();
    player.setOnEndOfMedia(() -> System.out.println("media complete"));
    player.setOnError(() -> player.getError().printStackTrace());*/
  }

  @FXML
  public void onStartRecording() {

  }

  @FXML
  public void goSetting() throws IOException {
    Parent settingView = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("setting.fxml")));
    root.getChildren().add(settingView);
  }
}
