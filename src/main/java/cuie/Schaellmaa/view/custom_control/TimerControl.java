package cuie.Schaellmaa.view.custom_control;

import cuie.Schaellmaa.presentationmodel.TimerActionEvent;
import cuie.Schaellmaa.presentationmodel.TimerMediaPlayer;
import cuie.Schaellmaa.regex.TimeRegex;
import cuie.Schaellmaa.skin.SkinType;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.*;
import javafx.css.PseudoClass;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.text.Font;
import javafx.util.converter.LocalTimeStringConverter;
import java.time.LocalTime;


public class TimerControl extends Control {

  // active = true means that the timer is currently activated
  private static final PseudoClass ACTIVE_CLASS = PseudoClass.getPseudoClass("active");
  private static final PseudoClass INVALID_CLASS = PseudoClass.getPseudoClass("invalid");
  private static final PseudoClass CONVERTIBLE_CLASS = PseudoClass.getPseudoClass("convertible");
  private static final PseudoClass TIME_OVER_CLASS = PseudoClass.getPseudoClass("time-over");
  private final TimerActionEvent timerActionEvent = new TimerActionEvent();
  private final TimerMediaPlayer timerMediaPlayer = new TimerMediaPlayer();
  private final SkinType skinType;

  //all properties
  private final ObjectProperty<LocalTime> timeValue = new SimpleObjectProperty<>();
  private final StringProperty timeAsString = new SimpleStringProperty();
  private final StringProperty caption = new SimpleStringProperty();

  private final BooleanProperty active = new SimpleBooleanProperty() {
    @Override
    protected void invalidated() {
      pseudoClassStateChanged(ACTIVE_CLASS, get());
    }
  };

  private final BooleanProperty invalid = new SimpleBooleanProperty() {
    @Override
    protected void invalidated() {
      pseudoClassStateChanged(INVALID_CLASS, get());
    }
  };

  private final BooleanProperty convertible = new SimpleBooleanProperty() {
    @Override
    protected void invalidated() {
      pseudoClassStateChanged(CONVERTIBLE_CLASS, get());
    }
  };

  private final BooleanProperty timeOver = new SimpleBooleanProperty() {
    @Override
    protected void invalidated() {
      pseudoClassStateChanged(TIME_OVER_CLASS, get());
    }
  };

  private final BooleanProperty editable = new SimpleBooleanProperty();

  public TimerControl(SkinType skinType) {
    this.skinType = skinType;
    initializeSelf();
    setupValueChangeListeners();
  }

  private void setupValueChangeListeners() {
    activeProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        timerActionEvent.start();
      } else {
        timerActionEvent.stop();
      }
    });
    timerActionEvent.timeOverProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        timerMediaPlayer.start();
        setTimeOver(true);
      } else {
        timerMediaPlayer.stop();
        setTimeOver(false);
      }
    });
    timeValueProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.getSecond() + newValue.getMinute() + newValue.getHour() > 0) {
        timerMediaPlayer.stop();
        setTimeOver(false);
      }
    });
  }

  public void restartTimer(String lastInputType) {
    setActive(false);
    setTimeOver(false);
    timerMediaPlayer.stop();
    setTimeAsString(lastInputType);
  }

  private void initializeSelf() {
    getStyleClass().add("timer-control");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeRegex.FORMATTED_TIME_PATTERN);
    timeAsStringProperty().bindBidirectional(timeValueProperty(),
        new LocalTimeStringConverter(formatter, formatter) {
          @Override
          public LocalTime fromString(String value) {
            try {
              if (TimeRegex.matchOptions(value).matches()) {
                setConvertible(true);
              } else {
                setConvertible(false);
              }
              if (TimeRegex.timeIsOver(value)) {
                setTimeOver(true);
              } else {
                setTimeOver(false);
              }
              return super.fromString(value);
            } catch (Exception e) {
              return getTimeValue();
            }
          }
        });
    timerActionEvent.leftTimeProperty()
        .bindBidirectional(timeAsStringProperty());
    timerActionEvent.runningProperty().bindBidirectional(activeProperty());
  }

  @Override
  protected Skin<?> createDefaultSkin() {
    return skinType.getFactory().apply(this);
  }

  public void addStylesheetFiles(String... stylesheetFile) {
    for (String file : stylesheetFile) {
      String stylesheet = getClass().getResource(file).toExternalForm();
      getStylesheets().add(stylesheet);
    }
  }

  public void loadFonts(String... font) {
    for (String f : font) {
      Font.loadFont(getClass().getResourceAsStream(f), 0);
    }
  }

  public void convert(String value) {
    if (isConvertible()) {
      value = TimeRegex.ifWrongHoursConvertTo24HourTimeString(value);
      LocalTime l = LocalTime.parse(TimeRegex.convertOption(value));
      setTimeValue(l);
      setActive(true);
    }
  }


  public void increaseMinute(int difference) {
    setTimeValue(getTimeValue().plusMinutes(difference));
  }

  public void decreaseMinute() {
    setTimeValue(getTimeValue().plusMinutes(-5));
  }

  public LocalTime getTimeValue() {
    return timeValue.get();
  }

  public ObjectProperty<LocalTime> timeValueProperty() {
    return timeValue;
  }

  public void setTimeValue(LocalTime timeValue) {
    this.timeValue.set(timeValue);
  }

  public String getCaption() {
    return caption.get();
  }

  public StringProperty captionProperty() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption.set(caption);
  }

  public boolean isEditable() {
    return editable.get();
  }

  public BooleanProperty editableProperty() {
    return editable;
  }

  public void setEditable(boolean editable) {
    this.editable.set(editable);
  }

  public String getTimeAsString() {
    return timeAsString.get();
  }

  public StringProperty timeAsStringProperty() {
    return timeAsString;
  }

  public void setTimeAsString(String timeAsString) {
    this.timeAsString.set(timeAsString);
  }

  public boolean isInvalid() {
    return invalid.get();
  }

  public BooleanProperty invalidProperty() {
    return invalid;
  }

  public void setInvalid(boolean invalid) {
    this.invalid.set(invalid);
  }

  public boolean isConvertible() {
    return convertible.get();
  }

  public BooleanProperty convertibleProperty() {
    return convertible;
  }

  public void setConvertible(boolean convertible) {
    this.convertible.set(convertible);
  }

  public boolean getActive() {
    return active.get();
  }

  public BooleanProperty activeProperty() {
    return active;
  }

  public void setActive(boolean active) {
    this.active.set(active);
  }

  public boolean isTimeOver() {
    return timeOver.get();
  }

  public BooleanProperty timeOverProperty() {
    return timeOver;
  }

  public void setTimeOver(boolean timeOver) {
    this.timeOver.set(timeOver);
  }


  public TimerActionEvent getTimerActionEvent() {
    return timerActionEvent;
  }

  public TimerMediaPlayer getTimerMediaPlayer() {
    return timerMediaPlayer;
  }

}
