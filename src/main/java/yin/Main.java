package yin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main JavaFX application class for Yin.
 * Loads the main window layout from FXML, sets up the stage,
 * and injects the backend logic into the GUI controller.
 */
public class Main extends Application {

    private final AppCore appCore = new AppCore();

    /**
     * Starts the JavaFX application.
     * Loads MainWindow.fxml, attaches it to the stage,
     * and provides the controller with an AppCore instance.
     *
     * @param stage Primary stage provided by the JavaFX runtime
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinWidth(380);
            stage.setMinHeight(520);
            stage.setTitle("Yin");
            // Inject the AppCore into the controller
            fxmlLoader.<MainWindow>getController().setAppCore(appCore);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
