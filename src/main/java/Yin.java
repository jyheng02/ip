import java.util.Scanner;

public class Yin {
    private static final Storage storage = new Storage("data/Yin.txt");
    private static final Ui ui = new Ui();


    private static TaskList tasks = new TaskList();

    public static void printTasks() {
        ui.showList(tasks.asList());
    }

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

                Parser.Parsed p = Parser.parse(command);
                // save after every mutation of tasklist
                switch (p.type) {
                case BYE:
                    ui.showExit();
                    return;

                case LIST:
                    printTasks();
                    break;

                case TODO: {
                    Task t = tasks.addTodo(p.desc);
                    ui.showAdded(t, tasks.size());
                    storage.save(tasks.asList());
                    break;
                }
                case DEADLINE: {
                    try {
                        Task t = tasks.addDeadline(p.desc, DateTimes.parseFlexible(p.by));
                        ui.showAdded(t, tasks.size());
                        storage.save(tasks.asList());
                    } catch (Exception e) {
                        throw new YinException("I couldn't parse the date/time :(" +
                                "\n    Try formats like 2019-10-15 or 2/12/2019 1800.");
                    }
                    break;
                }
                case EVENT: {
                    try {
                        Task t = tasks.addEvent(
                                p.desc,
                                DateTimes.parseFlexible(p.from),
                                DateTimes.parseFlexible(p.to)
                        );
                        ui.showAdded(t, tasks.size());
                        storage.save(tasks.asList());
                    } catch (Exception e) {
                        throw new YinException("I couldn't parse one of the dates/times :(" +
                                "\n    Try formats like 2019-10-15 or 2/12/2019 1800.");
                    }
                    break;
                }
                case MARK: {
                    if (p.index0 < 0 || p.index0 >= tasks.size()) {
                        throw new YinException("Invalid index to mark");
                    }
                    Task t = tasks.mark(p.index0);
                    ui.showMarked(t);
                    storage.save(tasks.asList());
                    break;
                }
                case UNMARK: {
                    if (p.index0 < 0 || p.index0 >= tasks.size()) {
                        throw new YinException("Invalid index to unmark");
                    }
                    Task t = tasks.unmark(p.index0);
                    ui.showUnmarked(t);
                    storage.save(tasks.asList());
                    break;
                }
                case DELETE: {
                    if (p.index0 < 0 || p.index0 >= tasks.size()) {
                        throw new YinException("Invalid index to delete");
                    }
                    Task removed = tasks.delete(p.index0);
                    ui.showRemoved(removed, tasks.size());
                    storage.save(tasks.asList());
                    break;
                }
                }
            } catch (YinException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}
