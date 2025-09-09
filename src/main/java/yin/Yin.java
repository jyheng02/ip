package yin;

import java.util.List;
import java.util.Scanner;

/**
 * Entry point of the Yin CLI application.
 * It wires together the Storage, TaskList, and Ui components,
 * then runs a loop that parses user input into Command objects (via Parser) and executes them.
 * The loop terminates when a command signals exit (e.g. bye command)
 */
public class Yin {
    /** Backing store for persisting tasks to disk. */
    private static final Storage storage = new Storage("data/Yin.txt");

    /** Console user interface for input/output. */
    private static final Ui ui = new Ui();

    /** List of tasks manipulated by commands. */
    private static TaskList tasks = new TaskList();

    /**
     * Starts the application by loading persisted tasks, greeting the user,
     * and running the loop until an exit command is given.
     * The loop reads input, trims it, rejects empty input,
     * parses it into a Command using Parser.parse(String),
     * and executes it with the current TaskList, Ui, and Storage.
     * Any YinException thrown by parsing or execution is caught and shown through Ui.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {

        // load tasks from disk (first run creates file/folder).
        List<Task> loaded = storage.load();
        assert loaded != null : "Storage.load() should not return null";
        tasks = new TaskList(storage.load());

        ui.showWelcome();

        Scanner scan = new Scanner(System.in);

        while (true) {
            String input = scan.nextLine();
            String command = input.trim();

            try {
                // handle whitespace-only input.
                if (command.isEmpty()) {
                    throw new YinException("input is empty >:("
                           + "\n    Give me something please.");
                }

                // parse raw command into a command object.
                Command c = Parser.parse(command);
                assert c != null : "Parser should never return null command";

                // execute command against state; implementation may persist via storage.
                c.execute(tasks, ui, storage);

                if (c.isExit()) {
                    return;
                }
            } catch (YinException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}
