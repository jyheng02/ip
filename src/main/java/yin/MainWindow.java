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
     * Initialises the main window after FXML is loaded.
     * Configures scroll and wrapping behaviour.
     * Scrolls to the bottom when new content is added.
     * Keeps dialog bubbles constrained to readable widths.
     */
    @FXML
    public void initialize() {
        scrollPane.setFitToWidth(true);
        dialogContainer.setFillWidth(true);
        dialogContainer.heightProperty().addListener((
                obs, oldH, newH) -> {
            if (newH.doubleValue() > oldH.doubleValue()) {
                scrollPane.setVvalue(1.0);
            }
        });
        dialogContainer.widthProperty().addListener((
                obs, oldW, newW) -> {
            double max = newW.doubleValue() - 100;
            dialogContainer.getChildren().forEach(node -> {
                if (node instanceof javafx.scene.layout.HBox h) {
                    h.getChildren().stream()
                            .filter(n -> n instanceof javafx.scene.control.Label)
                            .map(n -> (javafx.scene.control.Label) n)
                            .forEach(lbl -> lbl.setMaxWidth(max));
                }
            });
        });
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
        var helloDb = DialogBox.getYinDialog(hello, yinImage);
        helloDb.bindBubbleWidthTo(dialogContainer, 140);
        dialogContainer.getChildren().add(helloDb);
    }

    /**
     * Handles user input from the text field or send button.
     * Creates dialog bubbles for the user’s input and Yin’s response,
     * applies error styling if needed, and clears the text field.
     * Exits the app after a short delay if the exit command was given.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.isBlank()) {
            return;
        }

        String response = appCore.getResponse(input);
        boolean isError = response.startsWith("ERROR:");
        String clean = isError ? response.substring("ERROR:".length()).trim() : response;

        var userDb = DialogBox.getUserDialog(input, userImage);
        userDb.bindBubbleWidthTo(dialogContainer, 180);

        var yinDb = isError
                ? DialogBox.getErrorDialog(clean, yinImage)
                : DialogBox.getYinDialog(clean, yinImage);
        yinDb.bindBubbleWidthTo(dialogContainer, 180);

        dialogContainer.getChildren().addAll(userDb, yinDb);
        userInput.clear();

        if (appCore.hasExited()) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }
}
