package cuie.Schaellmaa.view.custom_elements.dropdown;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.Button;

public class DropDownButton extends Button {

  private static final String STYLE_CSS = "dropDownButtonStyles.css";
  private static final PseudoClass ACTIVE_CLASS = PseudoClass.getPseudoClass("active");
  private final BooleanProperty active = new SimpleBooleanProperty() {
    @Override
    protected void invalidated() {
      pseudoClassStateChanged(ACTIVE_CLASS, get());
    }
  };

  public DropDownButton() {
    initialiseSelf();
    setupEventHandlers();
    setupValueChangedListeners();
  }

  private void initialiseSelf() {
    String stylesheet = getClass().getResource(STYLE_CSS).toExternalForm();
    getStylesheets().add(stylesheet);
    getStyleClass().add("dropdown-button");
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
