package cuie.Schaellmaa.presentationmodel;

import cuie.Schaellmaa.regex.TimeRegex;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;

/**
 * the timer event handler. It contains the required logic for the countdown functionality
 */
public class TimerActionEvent {

  private Timeline animation;
  private final StringProperty leftTime = new SimpleStringProperty();
  private final BooleanProperty running = new SimpleBooleanProperty();
  private final BooleanProperty timeOver = new SimpleBooleanProperty();

  public TimerActionEvent() {
    animation = new Timeline(new KeyFrame(Duration.seconds(1), e -> countDown()));
    animation.setCycleCount(Timeline.INDEFINITE);
  }

  /**
   * reduces current time by one second
   */
  private void countDown() {
    setLeftTime(TimeRegex.ifWrongHoursConvertTo24HourTimeString(leftTime.get()));

    if (TimeRegex.matchOptions(leftTime.get()).matches()) {
      setLeftTime(TimeRegex.convertOption(leftTime.get()));
    }
    // the desired time format to be applied in the LocalTime instance
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeRegex.FORMATTED_TIME_PATTERN);
    LocalTime localTime = LocalTime.parse(TimeRegex.parseLeftTimeValues(leftTime.get()), formatter);

    long diffSec =
        localTime.getHour() * 3600 + localTime.getMinute() * 60 + localTime.getSecond() - 1;
    int hours = (int) diffSec / 3600;
    int minutes = (int) (diffSec / 60) % 60;
    int seconds = (int) diffSec % 60;
    String output = LocalTime.of(hours, minutes, seconds).format(formatter);
    setLeftTime(output);

    if (hours == 0 && minutes == 0 & seconds == 0) {
      timeOver.set(true);
      stop();
    }
  }

  /**
   * starts the countdown
   */
  public void start() {
    setRunning(true);
    timeOver.set(false);
    animation.play();
  }

  /**
   * stops the countdown
   */
  public void stop() {
    setRunning(false);
    animation.stop();
  }

  public String getLeftTime() {
    return leftTime.get();
  }

  public StringProperty leftTimeProperty() {
    return leftTime;
  }

  public void setLeftTime(String leftTime) {
    this.leftTime.set(leftTime);
  }

  public boolean isRunning() {
    return running.get();
  }

  public BooleanProperty runningProperty() {
    return running;
  }

  public void setRunning(boolean running) {
    this.running.set(running);
  }

  public boolean isTimeOver() {
    return timeOver.get();
  }

  public BooleanProperty timeOverProperty() {
    return timeOver;
  }
}
