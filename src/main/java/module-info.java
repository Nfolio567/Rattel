module one.nfolio.rattel {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.desktop;

  opens one.nfolio.rattel to javafx.fxml;
  exports one.nfolio.rattel;
}