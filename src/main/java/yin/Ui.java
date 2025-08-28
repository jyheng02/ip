package yin;

import java.util.Scanner;
import java.util.List;

public class Ui {
    private static final String INDENT = "  ";
    private static final String LINE =
            "____________________________________________________________";
    private final Scanner sc = new Scanner(System.in);

    public void showLine() {
        System.out.println(INDENT + LINE);
    }

    public void showWelcome() {
        showLine();
        System.out.println("    Hello! I'm Yin\n    How can I be of assistance?");
        showLine();
    }

    public void showExit() {
        showLine();
        System.out.println("    See you later alligator.");
        showLine();
    }

    public void showError(String msg) {
        showLine();
        System.out.println("    " + msg);
        showLine();
    }

    // helper messages (same wording as your current app)
    public void showAdded(Task t, int size) {
        showLine();
        System.out.println("    Say less. I've added this task:");
        System.out.println("      " + t);
        System.out.println("    Now you have " + size + " tasks in the list.");
        showLine();
    }

    public void showRemoved(Task t, int size) {
        showLine();
        System.out.println("    Say less. I've removed this task:");
        System.out.println("      " + t);
        System.out.println("    Now you have " + size + " tasks in the list.");
        showLine();
    }

    public void showList(java.util.List<Task> tasks) {
        showLine();
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + "." + tasks.get(i));
        }
        showLine();
    }

    public void showMarked(Task t) {
        showLine();
        System.out.println("    Solid, I've marked this task as done:");
        System.out.println("      " + t);
        showLine();
    }

    public void showUnmarked(Task t) {
        showLine();
        System.out.println("    Skill issue, I've marked this task as not done yet:");
        System.out.println("      " + t);
        showLine();
    }

    /**
     * Prints the list of tasks that matched a find query.
     *
     * @param matches tasks that matched (may be empty)
     */
    public void showMatches(List<Task> matches) {
        showLine();
        if (matches.isEmpty()) {
            System.out.println("    No matches found.");
            showLine();
            return;
        }
        System.out.println("    Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println("    " + (i + 1) + "." + matches.get(i));
        }
        showLine();
    }
}
