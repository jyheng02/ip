package yin;

import javafx.application.Application;

/**
 * Entry point of the application.
 * This launcher class exists to avoid issues when running a JavaFX
 * application directly, by delegating startup to Main.
 */
public class Launcher {
    /**
     * Starts the application by launching the Main class.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
