package one.nfolio.util;

import javafx.scene.control.Alert;

public class ExceptionAlert {
  public void show(Exception e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(e.getClass().getSimpleName());
    alert.setContentText(e.toString());
    alert.showAndWait();
  }
}
