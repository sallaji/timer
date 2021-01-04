package cuie.Schaellmaa.view;

import cuie.Schaellmaa.presentationmodel.PresentationModel;
import cuie.Schaellmaa.skin.SkinType;
import cuie.Schaellmaa.view.custom_control.TimerControl;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * @author Mauro Schällmann & Santiago Llano
 */
public class MainPane extends BorderPane {

  private final PresentationModel presentationModel;

  private TimerControl timerControl;

  public MainPane(PresentationModel presentationModel) {
    this.presentationModel = presentationModel;
    initializeControls();
    layoutControls();
    setupBindings();
  }

  private void initializeControls() {

    timerControl = new TimerControl(SkinType.EXPERIMENTAL);
  }

  private void layoutControls() {
    setCenter(timerControl);
  }


  private void setupBindings() {
    timerControl.timeValueProperty().bindBidirectional(presentationModel.startTimeProperty());
    timerControl.editableProperty().bind(presentationModel.readOnlyProperty().not());
  }
}
