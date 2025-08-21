import java.util.ArrayList;
import java.util.Scanner;

public class Yin {
    private static final String INDENT = "  ";
    private static final String LINE = "____________________________________________________________";

    private static void printLine() {
        System.out.println(INDENT + LINE);
    }

    private static void printGreeting() {
        printLine();
        System.out.println("    Hello! I'm Yin\n    How can I be of assistance?");
        printLine();
    }

    private static void printExit() {
        printLine();
        System.out.println("    See you later alligator.");
        printLine();
    }

    // error printer
    private static void printError(String message) {
        printLine();
        System.out.println("    " + message);
        printLine();
    }

    private static final ArrayList<Task> tasks = new ArrayList<>();

    private static void addTask(Task task) {
        tasks.add(task);
        printLine();
        System.out.println("    Say less. I've added this task:");
        System.out.println("      " + task);
        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    public static void printTasks() {
        printLine();
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + "." + tasks.get(i));
        }
        printLine();
    }

    // function to check if input is out of range within task list
    private static int parseIndex(String input, String cmdWord) throws YinException {
        String num = input.substring(cmdWord.length()).trim();
        if (num.isEmpty()) {
            throw new YinException("Give me task number, e.g. \"" + cmdWord + " 2\"");
        }
        int index;
        try {
            // convert to 0 based index for task list
            index = Integer.parseInt(num) - 1;
        } catch (NumberFormatException e) {
            throw new YinException("Task number must be integer! e.g. \"" + cmdWord + "\"");
        }
            // check if out of range
            if (index < 0 || index >= tasks.size()) {
                throw new YinException("Invalid index to " + cmdWord);
            }
            return index;
    }

    // function to mark task as done
    private static void doMark(int index) {
        Task task = tasks.get(index);
        task.mark();
        printLine();
        System.out.println("    Solid, I've marked this task as done:");
        System.out.println("      " + task);
        printLine();
    }

    // function to unmark task
    private static void doUnmark(int index) {
        Task task = tasks.get(index);
        task.unmark();
        printLine();
        System.out.println("    Skill issue, I've marked this task as not done yet:");
        System.out.println("      " + task);
        printLine();
    }

    // function to handle to do inputs
    private static void handleTodo(String input) throws YinException {
        String description = input.substring("todo".length()).trim();
        // if description empty give example input
        if (description.isEmpty()) {
            throw new YinException("todo needs a description fam, I'm not bright enough to read minds, e.g. \"todo borrow book\"");
        }
        addTask(new Todo(description));
    }

    // function to handle deadline inputs
    private static void handleDeadline(String input) throws YinException {
        // expected format: deadline <description> /by <when>
        String body = input.substring("deadline".length()).trim();
        int separator =  body.indexOf("/by");
        if (separator == -1) {
            throw new YinException("Give me a proper input please...Deadline format: deadline <desc> /by <when>");
        }
        String description = body.substring(0, separator).trim();
        // separator + 3 to skip "/by"
        String by = body.substring(separator + 3).trim();
        if (description.isEmpty() || by.isEmpty()) {
            throw new YinException("Give me a proper input please...Deadline format: deadline <desc> /by <when>");
        }
        addTask(new Deadline(description, by));
    }

    // function to handle events
    private static void handleEvent(String input) throws YinException {
        // expected format: event <description /from <start> /to <end>
        String body =  input.substring("event".length()).trim();
        int fromPosition = body.indexOf("/from");
        int toPosition = body.indexOf("/to", fromPosition + 5);
        if (fromPosition == -1 || toPosition == -1 || toPosition < fromPosition + 5) {
            throw new YinException("Please feed me a proper input man... Event format: event <desc> /from <start> /to <end>");
        }
        String description = body.substring(0, fromPosition).trim();
        // skips "/from" and then "/to"
        String from = body.substring(fromPosition + 5, toPosition).trim();
        String to = body.substring(toPosition + 3).trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new YinException("Please feed me some proper input man... Event format: event <desc> /from <start> /to <end>");
        }
        addTask(new Event(description, from, to));
    }

    public static void main(String[] args) {

        printGreeting();

        Scanner scan = new Scanner(System.in);

        while (true) {
            String input = scan.nextLine();
            String cmd = input.trim();

            try {
                // handle whitespace only input
                if (cmd.isEmpty()) {
                    throw new YinException("input is empty >:(. Give me something please.");
                }

                if (cmd.equals("bye")) {
                    printExit();
                    break;
                }
                // command to list tasks
                else if (cmd.equals("list")) {
                    printTasks();
                } else if (cmd.equals("todo")) {
                    throw new YinException("todo needs a description fam, I'm not bright enough to read minds, e.g. \"todo borrow book\"");
                } else if (cmd.equals("deadline")) {
                    throw new YinException("Give me a proper input please...Deadline format: deadline <desc> /by <when>");
                } else if (cmd.equals("event")) {
                    throw new YinException("Please feed me some proper input man... Event format: event <desc> /from <start> /to <end>");
                } else if (cmd.equals("mark")) {
                    throw new YinException("Give task number, e.g. \"mark 2\"");
                } else if (cmd.equals("unmark")) {
                    throw new YinException("Give task number, e.g. \"unmark 2\"");
                }
                // command to mark
                else if (cmd.startsWith("mark ")) {
                    int index = parseIndex(cmd, "mark");
                    doMark(index);
                }
                // command to unmark
                else if (cmd.startsWith("unmark ")) {
                    int index = parseIndex(cmd, "unmark");
                        doUnmark(index);
                }
                // command for todo
                else if (cmd.startsWith("todo ")) {
                    handleTodo(cmd);
                }
                // command for deadline
                else if (cmd.startsWith("deadline ")) {
                    handleDeadline(cmd);
                }
                // command for event
                else if (cmd.startsWith("event ")) {
                    handleEvent(cmd);
                } else {
                    throw new YinException("Why are you spouting nonsense >:(. Try: todo, deadline, event, list, mark, unmark, or bye.");
                }
            } catch (YinException e) {
                printError(e.getMessage());
            }
        }
    }
}
