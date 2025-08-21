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

    static ArrayList<String> tasks = new ArrayList<>();

    private static void addTask(String task) {
        tasks.add(task);
        printLine();
        System.out.println("    added: " + task);
        printLine();
    }

    public static void printTasks() {
        printLine();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + "." + tasks.get(i));
        }
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
            } else {
                addTask(input);
            }
        }
    }
}
