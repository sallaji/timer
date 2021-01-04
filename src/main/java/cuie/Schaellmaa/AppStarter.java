package cuie.Schaellmaa;


import cuie.Schaellmaa.presentationmodel.PresentationModel;
import cuie.Schaellmaa.view.MainPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class AppStarter extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    PresentationModel presentationModel = new PresentationModel();
    Region rootPanel = new MainPane(presentationModel);
    primaryStage.setTitle("Timer");
    Scene scene = new Scene(rootPanel);
    primaryStage.setResizable(true);
    primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue)
        primaryStage.setMaximized(false);
    });
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  public static void main(String[] args) {
    launch(args);
  }
}