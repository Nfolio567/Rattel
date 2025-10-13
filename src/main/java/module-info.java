module one.nfolio.rattel {
  requires javafx.controls;
  requires javafx.fxml;

  opens one.nfolio.rattel to javafx.fxml;
  exports one.nfolio.rattel;
}