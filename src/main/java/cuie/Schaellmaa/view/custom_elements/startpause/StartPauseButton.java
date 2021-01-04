package cuie.Schaellmaa.view.custom_elements.startpause;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.Button;

public class StartPauseButton extends Button {

  private static final String STYLE_CSS = "startPauseButtonStyles.css";
  private static final PseudoClass ACTIVE_CLASS = PseudoClass.getPseudoClass("active");

  private final BooleanProperty active = new SimpleBooleanProperty() {
    @Override
    protected void invalidated() {
      pseudoClassStateChanged(ACTIVE_CLASS, get());
    }
  };

  public StartPauseButton() {
    initialiseSelf();
    setupEventHandlers();
    setupValueChangedListeners();
  }

  private void initialiseSelf() {
    String stylesheet = getClass().getResource(STYLE_CSS).toExternalForm();
    getStylesheets().add(stylesheet);
    getStyleClass().add("start-pause-button");
  }

  private void setupEventHandlers() {
    setOnMouseClicked(event -> setActive(!isActive()));
  }

  private void setupValueChangedListeners() {
  }

  public boolean isActive() {
    return active.get();
  }

  public BooleanProperty activeProperty() {
    return active;
  }

  public void setActive(boolean active) {
    this.active.set(active);
  }
}
