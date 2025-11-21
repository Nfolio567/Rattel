module one.nfolio.rattel {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.desktop;
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.annotation;
  requires com.fasterxml.jackson.databind;
  requires static lombok;

  opens one.nfolio.rattel to javafx.fxml;
  opens one.nfolio.controller to javafx.fxml;
  opens one.nfolio.util to com.fasterxml.jackson.databind;
  exports one.nfolio.rattel;
  exports one.nfolio.controller;
  exports one.nfolio.util;
  exports one.nfolio.pojo;
  opens one.nfolio.pojo to com.fasterxml.jackson.databind;
}
