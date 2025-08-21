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
        System.out.println("    Hello! I'm yin\n    What can I do for you?");
        printLine();
    }

    private static void printExit() {
        printLine();
        System.out.println("    I zao first. seeya");
        printLine();
    }

    private static final ArrayList<Task> tasks = new ArrayList<>();

    private static void addTask(Task task) {
        tasks.add(task);
        printLine();
        System.out.println("    Got it. I've added this task:");
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
    private static int parseIndex(String input, String cmd) {
        // expect either "mark N" or "unmark N"
        try {
            String num = input.substring(cmd.length()).trim();
            // convert to 0 based index for task list
            int index = Integer.parseInt(num) - 1;
            // check if out of range
            if (index < 0 || index >= tasks.size()) {
                return -1;
            }
            return index;
        } catch (Exception e) {
            return -1;
        }
    }

    // function to mark task as done
    private static void doMark(int index) {
        Task task = tasks.get(index);
        task.mark();
        printLine();
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("      " + task);
        printLine();
    }

    // function to unmark task
    private static void doUnmark(int index) {
        Task task = tasks.get(index);
        task.unmark();
        printLine();
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("      " + task);
        printLine();
    }

    // function to handle to do inputs
    private static void handleTodo(String input) {
        String description = input.substring("todo".length()).trim();
        // if description empty give example input
        if (description.isEmpty()) {
            printLine();
            System.out.println("    todo needs a description, e.g. \"todo borrow book\"");
            printLine();
            return;
        }
        addTask(new Todo(description));
    }

    // function to handle deadline inputs
    private static void handleDeadline(String input) {
        // expected format: deadline <description> /by <when>
        String body = input.substring("deadline".length()).trim();
        int separator =  body.indexOf("/by ");
        if (separator == -1) {
            printLine();
            System.out.println("    Deadline format: deadline <desc> /by <when>");
            printLine();
            return;
        }
        String description = body.substring(0, separator).trim();
        // separator + 3 to skip "/by"
        String by = body.substring(separator + 3).trim();
        if (description.isEmpty() || by.isEmpty()) {
            printLine();
            System.out.println("    Deadline format: deadline <desc> /by <when>");
            printLine();
            return;
        }
        addTask(new Deadline(description, by));
    }

    // function to handle events
    private static void handleEvent(String input) {
        // expected format: event <description /from <start> /to <end>
        String body =  input.substring("event".length()).trim();
        int fromPosition = body.indexOf("/from");
        int toPosition = body.indexOf("/to");
        if (fromPosition == -1 || toPosition == -1 || toPosition < fromPosition) {
            printLine();
            System.out.println("    Event format: event <desc> /from <start> /to <end>");
            printLine();
            return;
        }
        String description = body.substring(0, fromPosition).trim();
        // skips "/from" and then "/to"
        String from = body.substring(fromPosition + 5).trim();
        String to = body.substring(toPosition + 3).trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            printLine();
            System.out.println("    Event format: event <desc> /from <start> /to <end>");
            printLine();
            return;
        }
        addTask(new Event(description, from, to));
    }

    public static void main(String[] args) {


        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        System.out.println(logo);
        printGreeting();

        Scanner scan = new Scanner(System.in);

        while (true) {
            String input = scan.nextLine();

            if (input.equals("bye")) {
                printExit();
                break;
            }
            // command to list tasks
            else if (input.equals("list")) {
                printTasks();
            }
            // command to mark
            else if (input.startsWith("mark ")) {
                int index = parseIndex(input, "mark");
                // invalid task index provided
                if (index == -1) {
                    printLine();
                    System.out.println("    Invalid index to mark");
                    printLine();
                } else {
                    doMark(index);
                }
            }
            // command to unmark
            else if (input.startsWith("unmark ")) {
                int index = parseIndex(input, "unmark");
                if (index == -1) {
                    printLine();
                    System.out.println("    Invalid index to unmark");
                    printLine();
                }  else {
                    doUnmark(index);
                }
            }
            // command for todo
            else if (input.startsWith("todo ")) {
                handleTodo(input);
            }
            // command for deadline
            else if (input.startsWith("deadline ")) {
                handleDeadline(input);
            }
            // command for event
            else if (input.startsWith("event ")) {
                handleEvent(input);
            } else {
                addTask(new Todo(input));
            }
        }
    }
}
