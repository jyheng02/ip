import java.util.ArrayList;
import java.util.Scanner;

public class Yin {
    private static final Storage storage = new Storage("data/Yin.txt");
    private static final Ui ui = new Ui();


    private static final ArrayList<Task> tasks = new ArrayList<>();

    private static void addTask(Task task) {
        tasks.add(task);
        ui.showAdded(task, tasks.size());
        storage.save(tasks);
    }

    public static void printTasks() {
        ui.showList(tasks);
    }

    // function to delete tasks
    private static void deleteTask(int index) {
        Task removed = tasks.remove(index);
        ui.showRemoved(removed, tasks.size());
        storage.save(tasks);
    }

    // function to collapse whitespaces to a single space
    private static String collapseSpaces(String string) {
        return string.trim().replaceAll("\\s+", " ");
    }

    // function to check if input is out of range within task list
    private static int parseIndex(String input, String commandWord) throws YinException {
        String num = input.substring(commandWord.length()).trim();
        if (num.isEmpty()) {
            throw new YinException("Give me task number, e.g. \"" + commandWord + " 2\"");
        }
        int index;
        try {
            // convert to 0 based index for task list
            index = Integer.parseInt(num) - 1;
        } catch (NumberFormatException e) {
            throw new YinException("Task number must be integer! e.g. \"" + commandWord + " 2\"");
        }
            // check if out of range
            if (index < 0 || index >= tasks.size()) {
                throw new YinException("Invalid index to " + commandWord);
            }
            return index;
    }

    // function to mark task as done
    private static void doMark(int index) {
        Task task = tasks.get(index);
        task.mark();
        ui.showMarked(task);
        storage.save(tasks);
    }

    // function to unmark task
    private static void doUnmark(int index) {
        Task task = tasks.get(index);
        task.unmark();
        ui.showUnmarked(task);
        storage.save(tasks);
    }

    // function to handle to do inputs
    private static void handleTodo(String input) throws YinException {
        String description = input.substring("todo".length()).trim();
        // if description empty give example input
        if (description.isEmpty()) {
            throw new YinException("todo needs a description fam, I'm not bright enough to read minds," +
                    " e.g. \"todo borrow book\"");
        }
        addTask(new Todo(collapseSpaces(description)));
    }

    // function to handle deadline inputs
    private static void handleDeadline(String input) throws YinException {
        // expected format: deadline <description> /by <when>
        String body = input.substring("deadline".length()).trim();
        int separator =  body.indexOf("/by");
        if (separator == -1) {
            throw new YinException("Give me a proper input please..." +
                    " Deadline format: deadline <desc> /by <when>");
        }
        String description = body.substring(0, separator).trim();
        // separator + 3 to skip "/by"
        String byRaw = body.substring(separator + 3).trim();
        if (description.isEmpty() || byRaw.isEmpty()) {
            throw new YinException("Give me a proper input please..." +
                    " Deadline format: deadline <desc> /by <when>");
        }
        try {
            addTask(new Deadline(collapseSpaces(description), DateTimes.parseFlexible(byRaw)));
        } catch (Exception e)  {
            throw new YinException("I couldn't parse the date/time :(." +
                    "\n    Try formats like 2019-10-15 or 2/12/2019 1800.");
        }
    }

    // function to handle events
    private static void handleEvent(String input) throws YinException {
        // expected format: event <description /from <start> /to <end>
        // parsing index
        String body =  input.substring("event".length()).trim();
        int fromPosition = body.indexOf("/from");
        int toPosition = body.indexOf("/to", fromPosition + 5);
        if (fromPosition == -1 || toPosition == -1 || toPosition < fromPosition + 5) {
            throw new YinException("Please feed me a proper input man..." +
                    " Event format: event <desc> /from <start> /to <end>");
        }
        String description = body.substring(0, fromPosition).trim();
        // skips "/from" and then "/to"
        // parsing index
        String fromRaw = body.substring(fromPosition + 5, toPosition).trim();
        String toRaw = body.substring(toPosition + 3).trim();
        if (description.isEmpty() || fromRaw.isEmpty() || toRaw.isEmpty()) {
            throw new YinException("Please feed me some proper input man..." +
                    " Event format: event <desc> /from <start> /to <end>");
        }
        try {
            addTask(new Event(collapseSpaces(description),
                    DateTimes.parseFlexible(fromRaw),
                    DateTimes.parseFlexible(toRaw)));
        } catch (Exception e) {
            throw new YinException("I couldn't parse one of the dates/times :(." +
                    "\n    Try formats like 2019-10-15 or 2/12/2019 1800.");
        }
    }

    public static void main(String[] args) {

        // load tasks from disk (first run creates file/folder)
        tasks.clear();
        tasks.addAll(storage.load());

        ui.showWelcome();

        Scanner scan = new Scanner(System.in);

        while (true) {
            String input = scan.nextLine();
            String command = input.trim();

            try {
                // handle whitespace only input
                if (command.isEmpty()) {
                    throw new YinException("input is empty >:(. Give me something please.");
                }

                Command c = Command.getCommand(command);

                switch (c) {
                case BYE:
                    ui.showExit();
                    return;

                case LIST:
                    if (!command.equals("list")) {
                        throw new YinException("list alone is enough!");
                    }
                    printTasks();
                    break;

                case TODO:
                    if (command.equals("todo")) {
                        throw new YinException("todo needs a description," +
                                " I'm not bright enough to read minds, e.g. \"todo borrow book\"");
                    }
                    handleTodo(command);
                    break;

                case DEADLINE:
                    if (command.equals("deadline")) {
                        throw new YinException("Give me a proper input please..." +
                                " Deadline format: deadline <desc> /by <when>");
                    }
                    handleDeadline(command);
                    break;

                case EVENT:
                    if (command.equals("event")) {
                        throw new YinException("Please feed me some proper input..." +
                                " Event format: event <desc> /from <start> /to <end>");
                    }
                    handleEvent(command);
                    break;

                case MARK:
                    if (command.equals("mark")) {
                        throw new YinException("Give task number, e.g. \"mark 2\"");
                    }
                    doMark(parseIndex(command, "mark"));
                    break;

                case UNMARK:
                    if (command.equals("unmark")) {
                        throw new YinException("Give task number, e.g. \"unmark 2\"");
                    }
                    doUnmark(parseIndex(command, "unmark"));
                    break;

                case DELETE:
                    if (command.equals("delete")) {
                        throw new YinException("Give task number, e.g. \"delete 2\"");
                    }
                    deleteTask(parseIndex(command, "delete"));
                    break;

                case UNKNOWN:
                default:
                    throw new YinException("Give me a command first >:(." +
                            " Try: todo, deadline, event, list, mark, unmark, delete or bye.");
                }
            } catch (YinException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}
