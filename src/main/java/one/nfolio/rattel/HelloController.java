package one.nfolio.rattel;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.io.IOException;
import java.util.Objects;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class HelloController {
  @FXML
  private Pane superRoot;

  @FXML
  private AnchorPane root;

  @FXML
  private Text title;

  @FXML
  private ImageView backgroundCard1;

  @FXML
  private ImageView backgroundCard2;

  @FXML
  private ImageView backgroundCard3;

  @FXML
  private ImageView backgroundCard4;

  @FXML
  public void initialize() {
    Stage stage = HelloApplication.getInstance().getStage();
    final ImageView[] backgroundCards = {backgroundCard1, backgroundCard2, backgroundCard3, backgroundCard4};
    final Image[] cards = {
            new Image(Objects.requireNonNull(getClass().getResource("/cards/ank/yellowAnk2.png")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/cards/doon/blueDoon5.png")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/cards/hatiron/pinkHatiron3.png")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/cards/yaar/greenYaar.png")).toExternalForm())
    };

    for (int i=0;i<4;i++) {
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

      title.setX((stage.getWidth() - title.getWrappingWidth()) / 2 + title.getFont().getSize() / 3);

      root.setPrefWidth(stage.getWidth());
      root.setPrefHeight(stage.getHeight());
      //root.setManaged(false);
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
  }

  @FXML
  public void onStartRecording() {

  }
}
