package yin;

import java.util.Scanner;

/**
 * Entry point of the Yin CLI application.
 *
 * <p>It wires together the {@link Storage}, {@link TaskList}, and {@link Ui} components,
 * then runs a read–eval–print loop (REPL) that parses user input into {@link Command}
 * objects (via {@link Parser}) and executes them. The loop terminates when a command
 * signals exit (e.g., the {@code bye} command).</p>
 *
 * @see Parser
 * @see Command
 * @see Storage
 * @see TaskList
 * @see Ui
 */
public class Yin {
    /** Backing store for persisting tasks to disk. */
    private static final Storage storage = new Storage("data/Yin.txt");

    /** Console user interface for input/output. */
    private static final Ui ui = new Ui();

    /** In-memory list of tasks manipulated by commands. */
    private static TaskList tasks = new TaskList();

    /**
     * Starts the application by loading persisted tasks, greeting the user,
     * and running the REPL until an exit command is issued.
     *
     * <p>The REPL reads a line of input, trims it, rejects empty input,
     * parses it into a {@link Command} using {@link Parser#parse(String)},
     * and executes it against the current {@link TaskList}, {@link Ui}, and {@link Storage}.
     * Any {@link YinException} thrown by parsing or execution is caught and rendered
     * via {@link Ui#showError(String)}.</p>
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {

        // load tasks from disk (first run creates file/folder).
        tasks = new TaskList(storage.load());

        ui.showWelcome();

        Scanner scan = new Scanner(System.in);

        while (true) {
            String input = scan.nextLine();
            String command = input.trim();

            try {
                // handle whitespace-only input.
                if (command.isEmpty()) {
                    throw new YinException("input is empty >:(" +
                            "\n    Give me something please.");
                }

                // parse raw command into a command object.
                Command c = Parser.parse(command);

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
