package cuie.Schaellmaa.skin;

import cuie.Schaellmaa.view.custom_control.TimerControl;
import java.util.function.Function;
import javafx.scene.control.SkinBase;

public enum SkinType {
  EXPERIMENTAL(TimerSkin::new);
  private final Function<TimerControl, SkinBase<TimerControl>> factory;
  SkinType(Function<TimerControl, SkinBase<TimerControl>> factory) {
    this.factory = factory;
  }
  public Function<TimerControl, SkinBase<TimerControl>> getFactory() {
    return factory;
  }
}