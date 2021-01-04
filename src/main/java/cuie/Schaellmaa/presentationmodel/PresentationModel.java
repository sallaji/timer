package cuie.Schaellmaa.presentationmodel;

import javafx.beans.property.*;

import java.time.LocalTime;

public class PresentationModel {

  private final ObjectProperty<LocalTime> startTime = new SimpleObjectProperty<>(
      LocalTime.of(0, 45, 0));

  private final BooleanProperty readOnly = new SimpleBooleanProperty(false);

  public LocalTime getStartTime() {
    return startTime.get();
  }

  public ObjectProperty<LocalTime> startTimeProperty() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime.set(startTime);
  }

  public boolean isReadOnly() {
    return readOnly.get();
  }

  public BooleanProperty readOnlyProperty() {
    return readOnly;
  }

  public void setReadOnly(boolean readOnly) {
    this.readOnly.set(readOnly);
  }
}
