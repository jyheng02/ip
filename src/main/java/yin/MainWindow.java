package yin;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI window.
 * Handles interactions between the user input,
 * the backend logic, and the dialog container
 * that displays both user messages and Yin's replies.
 */
public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private AppCore appCore;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image yinImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /**
     * Initialises the main window.
     * Ensures the scroll pane automatically moves to the bottom
     * whenever the dialog container grows.
     */
    @FXML
    public void initialize() {
        // Auto-scroll to the bottom when new dialog is added
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Links the backend logic to this window.
     * Displays the initial welcome message from Yin.
     *
     * @param appCore Backend adapter that processes user input
     */
    public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
        String hello = appCore.getWelcome();
        dialogContainer.getChildren().add(
                DialogBox.getYinDialog(hello, yinImage)
        );
    }

    /**
     * Handles user input from the text field or send button.
     * Creates a dialog box for the user’s message and another for Yin’s reply,
     * then clears the input field.
     * If the user exits, the application closes after a short delay
     * so the goodbye message remains visible.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.isBlank()) {
            return;
        }

        String response = appCore.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getYinDialog(response, yinImage)
        );
        userInput.clear();

        if (appCore.hasExited()) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }
}
