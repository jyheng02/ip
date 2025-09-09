package yin;

import java.util.List;

/**
 * Core class that connects the GUI to the backend logic.
 * Manages the task list and storage, and processes user input
 * into responses using the command parser.
 */
public class AppCore {
    private final Storage storage;
    private final TaskList tasks;
    private boolean isExited = false;

    /**
     * Creates a new AppCore with default storage path.
     * Loads tasks from "data/Yin.txt".
     */
    public AppCore() {
        this.storage = new Storage("data/Yin.txt");
        List<Task> loaded = storage.load();
        assert loaded != null : "Storage.load() should not return null";
        this.tasks = new TaskList(storage.load());
    }

    /**
     * Returns whether the application has received an exit command.
     *
     * @return true if exit command has been given, false otherwise
     */
    public boolean hasExited() {
        return isExited;
    }

    /**
     * Processes a single user input string and returns Yin's response.
     * Updates the exit status if an exit command is given.
     *
     * @param input User input string
     * @return Response message from Yin
     */
    public String getResponse(String input) {
        FxUi fxUi = new FxUi(); // GUI-friendly Ui that buffers output
        try {
            Command c = Parser.parse(input);
            assert c != null : "Parser should never return null command";
            c.execute(tasks, fxUi, storage);
            isExited = c.isExit();
        } catch (YinException e) {
            fxUi.showError(e.getMessage());
        }
        return fxUi.flush();
    }

    /**
     * Returns the welcome message shown when the application starts.
     *
     * @return Welcome message text
     */
    public String getWelcome() {
        FxUi fxUi = new FxUi();
        fxUi.showWelcome();
        String welcome = fxUi.flush();
        assert welcome != null && !welcome.isBlank()
                : "Welcome message should not be null or blank";
        return welcome;
    }
}
