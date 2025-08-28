package yin;

import java.util.Scanner;

public class Yin {
    private static final Storage storage = new Storage("data/Yin.txt");
    private static final Ui ui = new Ui();


    private static TaskList tasks = new TaskList();

    public static void main(String[] args) {

        // load tasks from disk (first run creates file/folder)
        tasks = new TaskList(storage.load());

        ui.showWelcome();

        Scanner scan = new Scanner(System.in);

        while (true) {
            String input = scan.nextLine();
            String command = input.trim();

            try {
                // handle whitespace only input
                if (command.isEmpty()) {
                    throw new YinException("input is empty >:(" +
                            "\n    Give me something please.");
                }

                // parse raw command into command object
                Command c = Parser.parse(command);

                // execute command against state, call storage.save() if tasklist mutated
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
