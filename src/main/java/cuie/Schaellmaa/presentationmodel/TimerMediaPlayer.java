package cuie.Schaellmaa.presentationmodel;

import java.io.File;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class TimerMediaPlayer {
//
//  Media media;
//  MediaPlayer mediaPlayer;

  private final AudioClip alarmSound;
  String path = "/audio/alarm.mp3";
  public TimerMediaPlayer() {
    alarmSound = new AudioClip(getClass().getResource(path).toString());
    alarmSound.setCycleCount(AudioClip.INDEFINITE);
  }

  public void start() {
    alarmSound.play();
  }

  public void stop() {
    alarmSound.stop();

  }
}
