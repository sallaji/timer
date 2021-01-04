package cuie.Schaellmaa.utils;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

public class TimerBackgroundPane extends Region {

  private ObjectProperty<ImageView> imageViewProperty = new SimpleObjectProperty<ImageView>();

  public ObjectProperty<ImageView> imageViewProperty() {
    return imageViewProperty;
  }

  public ImageView getImageView() {
    return imageViewProperty.get();
  }

  public void setImageView(ImageView imageView) {
    this.imageViewProperty.set(imageView);
  }

  public TimerBackgroundPane() {
    this(new ImageView());
  }

  @Override
  protected void layoutChildren() {
    ImageView imageView = imageViewProperty.get();
    if (imageView != null) {
      imageView.setFitWidth(getWidth());
      imageView.setFitHeight(getHeight());
      imageView.setPreserveRatio(true);
      imageView.setSmooth(true);
      imageView.setCache(true);
      layoutInArea(imageView, 0, 0, getWidth(), getHeight(), 0, HPos.CENTER, VPos.CENTER);
    }
    super.layoutChildren();
  }

  public TimerBackgroundPane(ImageView imageView, double fitWidth, double fitHeight) {
    imageView.setFitWidth(fitWidth);
    imageView.setFitHeight(fitHeight);
    setUpEventListeners();
    this.imageViewProperty.set(imageView);
  }

  public TimerBackgroundPane(ImageView imageView) {
    setUpEventListeners();
    this.imageViewProperty.set(imageView);
  }

  private void setUpEventListeners() {
    imageViewProperty.addListener((observable, oldValue, newValue) -> {
      if (oldValue != null) {
        getChildren().remove(oldValue);
      }
      if (newValue != null) {
        getChildren().add(newValue);
      }
    });
  }
}
