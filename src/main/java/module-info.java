module one.nfolio.rattel {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.media;
  requires java.desktop;

  opens one.nfolio.rattel to javafx.fxml;
  exports one.nfolio.rattel;
  exports one.nfolio.controller;
  opens one.nfolio.controller to javafx.fxml;
}