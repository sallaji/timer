package cuie.Schaellmaa.view.custom_control;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class DropDownChooser extends VBox {

  private static final String STYLE_CSS = "../../styles/style.css";

  private final TimerControl timeControl;

  private Button btnOne;
  private Button btnTwo;


  public DropDownChooser(TimerControl timeControl) {
    this.timeControl = timeControl;
    initializeSelf();
    initializeParts();
    layoutParts();
    setupEventHandlers();
    setupBindings();
  }

  private void initializeSelf() {
    String stylesheet = getClass().getResource(STYLE_CSS).toExternalForm();
    getStylesheets().add(stylesheet);
    getStyleClass().add("dropdown-chooser");
  }

  private void initializeParts() {
    btnOne = new Button("15 min");
    btnOne.getStyleClass().add("dropdown-button");
    btnTwo = new Button("30 min");
    btnTwo.getStyleClass().add("dropdown-button");
  }

  private void layoutParts() {
    getChildren().addAll(btnOne, btnTwo);
  }

  private void setupEventHandlers() {
    btnOne.setOnAction(event -> {
      timeControl.increaseMinute(15);
    });
    btnTwo.setOnAction(event -> {
      timeControl.increaseMinute(30);
    });
  }

  private void setupBindings() {
  }
}
