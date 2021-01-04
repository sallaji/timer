package cuie.Schaellmaa.skin;

import cuie.Schaellmaa.regex.TimeRegex;
import cuie.Schaellmaa.utils.TimerBackgroundPane;
import cuie.Schaellmaa.utils.ImageUtils;
import cuie.Schaellmaa.view.custom_control.TimerControl;
import cuie.Schaellmaa.view.custom_elements.dropdown.DropDownButton;
import cuie.Schaellmaa.view.custom_elements.restart.RestartButton;
import cuie.Schaellmaa.view.custom_elements.startpause.StartPauseButton;
import cuie.Schaellmaa.view.custom_control.DropDownChooser;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;

/**
 * the skin of the timer. It contains the controls of the UI that is displayed by default
 */
public class TimerSkin extends SkinBase<TimerControl> {

  StackPane pane;
  private TimerBackgroundPane timerBackground;
  private TextField timeTextField;
  private String lastTimeGiven;
  private HBox buttonContainer;
  private StartPauseButton startPauseButton;
  private RestartButton restartButton;
 // private Text captionLabel;
  private Popup popup;
  private Pane dropDownChooser;
  private DropDownButton dropDownButton;

  /**
   * Constructor for all SkinBase instances.
   *
   * @param control The control for which this Skin should attach to.
   */
  protected TimerSkin(TimerControl control) {
    super(control);
    initializeSelf();
    initializeParts();
    layoutParts();
    layoutButtonContainers();
    setupEventHandlers();
    setupValueChangeListeners();
    setupBindings();
  }

  /**
   * initializes the fonts and styles used in the skin
   */
  private void initializeSelf() {
    getSkinnable().addStylesheetFiles("../../styles/style.css");
    getSkinnable().loadFonts("/fonts/ds_digital/DS-DIGII.ttf", "/fonts/ds_digital/DS-DIGI.ttf");
  }

  /**
   * initialize the skin controls
   */
  private void initializeParts() {

    timerBackground = new TimerBackgroundPane(
        ImageUtils.getSVGImageAsImageView("../sketch/background.svg"), 600, 400);
    pane = new StackPane();
    timerBackground.getStyleClass().addAll("timer-background");
    startPauseButton = new StartPauseButton();
    restartButton = new RestartButton();
    buttonContainer = new HBox();
    buttonContainer.setAlignment(Pos.CENTER);
    buttonContainer.getStyleClass().addAll("button-container");
    timeTextField = new TextField();
    timeTextField.getStyleClass().add("time-text-field");
    dropDownButton = new DropDownButton();
    dropDownChooser = new DropDownChooser(getSkinnable());
    popup = new Popup();
    popup.getContent().addAll(dropDownChooser);
    renderAll();
  }

  /**
   * add the children to each of the available containers
   */
  private void layoutParts() {
    pane.getChildren().addAll(timerBackground, timeTextField, buttonContainer);
    getChildren().addAll(pane);
  }

  /**
   * add the buttons to their button container
   */
  public void layoutButtonContainers() {
    buttonContainer.getChildren().addAll(startPauseButton, restartButton, dropDownButton);
  }

  /**
   * the listeners for reacting to value changes
   */
  private void setupValueChangeListeners() {
    timeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        if (TimeRegex.matchWrongInputs(newValue).matches()) {
          timeTextField.setText(oldValue);
        }
        if (TimeRegex.matchMinDigits(newValue).matches()) {
          if (TimeRegex.matchExceededDigits(newValue).matches()) {
            timeTextField.setText(oldValue);
          }
          timeTextField.setText(TimeRegex.timeColons(newValue));
        }
        if (!TimeRegex.timeIsOver(newValue)){
          startPauseButton.setDisable(false);
        }
      }
    });
    ChangeListener<Number> sizeListener = (observable, oldValue, newValue) -> {
      renderAll();
    };

    getSkinnable().widthProperty().addListener(sizeListener);
    getSkinnable().heightProperty().addListener(sizeListener);

    timeTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        startPauseButton.setActive(false);
        getSkinnable().getTimerMediaPlayer().stop();
        getSkinnable().setTimeOver(false);
        if (TimeRegex.timeIsOver(timeTextField.textProperty().get())) {
          startPauseButton.setDisable(true);
        }else {
          startPauseButton.setDisable(false);
        }
        Platform.runLater(() -> {
          if (timeTextField.isFocused() && !timeTextField.getText().isEmpty()) {
            timeTextField.selectAll();
          }
        });

      }

    });
    getSkinnable().timeOverProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        startPauseButton.setDisable(true);
        restartButton.setActive(true);
      } else {
        startPauseButton.setDisable(false);
        restartButton.setActive(false);
      }
    });

  }

  public void resizePopUp() {

    Bounds bounds = dropDownButton.getBoundsInLocal();
    Bounds screenBounds = dropDownButton.localToScreen(bounds);
    if (screenBounds != null) {
      double centerX = screenBounds.getCenterX();
      double centerY = screenBounds.getCenterY();
      popup.setX(centerX + centerX * 0.01);
      popup.setY(centerY);
    }
  }

  /**
   *
   */
  private void setupEventHandlers() {
    dropDownButton.setOnAction(event -> {
      if (popup.isShowing()) {
        popup.hide();
      } else {
        popup.show(timeTextField.getScene().getWindow());
      }
    });

    popup.setOnShown(event -> {
      resizePopUp();
    });

    timeTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      int difference = 0;
      switch (event.getCode()) {
        case UP:
          difference = 5;
          getSkinnable().increaseMinute(difference);
          event.consume();
          break;
        case DOWN:
          difference = -5;
          getSkinnable().increaseMinute(difference);
          event.consume();
          break;
      }
    });

    timeTextField.setOnAction(event -> {
      getSkinnable().convert(timeTextField.textProperty().getValue());
    });

    startPauseButton.setOnMouseClicked(e -> {
      boolean isActive = !startPauseButton.isActive();
      startPauseButton.setActive(isActive);
    });
    restartButton.setOnMouseClicked(e -> {
      //TODO Nice to have: Replace with last time given ;)
      getSkinnable().restartTimer("00:45:00");
    });
  }

  private void setupBindings() {
    timeTextField.textProperty().bindBidirectional(getSkinnable().timeAsStringProperty());
 //   captionLabel.textProperty().bind(getSkinnable().captionProperty());
    startPauseButton.activeProperty().bindBidirectional(getSkinnable().activeProperty());
  }

  /**
   * rerenders the text field according to the resizing of the window
   *
   * @param timerBackgroundBounds the boundaries of the timer background image
   */
  public void resizeTimeTextField(Bounds timerBackgroundBounds) {
    timeTextField.setMaxHeight(timerBackgroundBounds.getHeight() * .4);
    timeTextField.setMaxWidth(timerBackgroundBounds.getWidth() * .8);
    Font font = Font.font(timeTextField.getFont().getFamily(), FontWeight.THIN,
        0.12 * timerBackgroundBounds.getHeight());
    timeTextField.setFont(font);
  }

  /**
   * re-renders the button according to the current window size
   *
   * @param timerBackgroundBounds the boundaries of the timer background image
   * @param button                the button to re-render
   * @return
   */
  public Object resizeIconButton(Bounds timerBackgroundBounds, Button button) {
    double newIconButtonSize
        = ((timerBackgroundBounds.getWidth() + timerBackgroundBounds.getHeight()) / 2) * 0.05;
    button.setMinSize(newIconButtonSize, newIconButtonSize);
    button.setMaxSize(newIconButtonSize, newIconButtonSize);
    return button;
  }

  /**
   * renders the button container according to the current size of the window
   *
   * @param timerBackgroundBounds the boundaries of the timer background image
   */
  public void redrawButtonContainers(Bounds timerBackgroundBounds) {
    double newSpacing
        = ((timerBackgroundBounds.getWidth() + timerBackgroundBounds.getHeight()) / 2);
    buttonContainer.setSpacing(newSpacing * 0.04);
    buttonContainer.setTranslateY(timerBackgroundBounds.getHeight() * .2);
    buttonContainer.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
  }

  /**
   * re-renders all buttons and text fields according to the current window size
   */
  private void renderAll() {
    Bounds timerBackgroundBounds = timerBackground.getImageView().getLayoutBounds();
    redrawButtonContainers(timerBackgroundBounds);
    startPauseButton = (StartPauseButton) resizeIconButton(timerBackgroundBounds, startPauseButton);
    restartButton = (RestartButton) resizeIconButton(timerBackgroundBounds, restartButton);
    dropDownButton = (DropDownButton) resizeIconButton(timerBackgroundBounds, dropDownButton);
    resizeTimeTextField(timerBackgroundBounds);
    Font font = Font.font("DS-Digital", FontWeight.BOLD, 0.2 * timerBackgroundBounds.getHeight());
    timeTextField.setFont(font);
    resizePopUp();
  }
}
