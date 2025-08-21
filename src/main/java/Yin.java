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

    private static void addTask(String task) {
        tasks.add(new Task(task));
        printLine();
        System.out.println("    added: " + task);
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
            } else if (input.equals("list")) {
                printTasks();
            } else if (input.startsWith("mark ")) {
                int index = parseIndex(input, "mark");
                if (index == -1) {
                    printLine();
                    System.out.println("    Invalid index to mark");
                    printLine();
                } else {
                    doMark(index);
                }
            } else if (input.startsWith("unmark ")) {
                int index = parseIndex(input, "unmark");
                if (index == -1) {
                    printLine();
                    System.out.println("    Invalid index to unmark");
                    printLine();
                }  else {
                    doUnmark(index);
                }
            } else {
                addTask(input);
            }
        }
    }
}
