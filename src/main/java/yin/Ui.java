package yin;

import java.util.Scanner;

/**
 * Handles all user-facing messages and formatting (banners, lists, confirmations, errors).
 *
 * <p>This class centralizes console output so that presentation logic is kept
 * separate from command parsing/execution. It prints standardized lines,
 * headings, and task-related confirmations.</p>
 */
public class Ui {
    /** Two-space left indentation used for all console lines. */
    private static final String INDENT = "  ";
    /** Horizontal divider used to frame sections of output. */
    private static final String LINE =
            "____________________________________________________________";
    /** Scanner reading from standard input; owned by the UI. */
    private final Scanner sc = new Scanner(System.in);

    /**
     * Prints a horizontal divider line with indentation.
     */
    public void showLine() {
        System.out.println(INDENT + LINE);
    }

    /** Prints the welcome banner shown at application start. */
    public void showWelcome() {
        showLine();
        System.out.println("    Hello! I'm Yin\n    How can I be of assistance?");
        showLine();
    }

    /** Prints the exit message shown before terminating the app. */
    public void showExit() {
        showLine();
        System.out.println("    See you later alligator.");
        showLine();
    }

    /**
     * Prints a formatted error message.
     *
     * @param msg the error message to display
     */
    public void showError(String msg) {
        showLine();
        System.out.println("    " + msg);
        showLine();
    }

    /**
     * Prints a confirmation that a task has been added, and shows the new list size.
     *
     * @param t    the task that was added
     * @param size the total number of tasks after the addition
     */
    public void showAdded(Task t, int size) {
        showLine();
        System.out.println("    Say less. I've added this task:");
        System.out.println("      " + t);
        System.out.println("    Now you have " + size + " tasks in the list.");
        showLine();
    }

    /**
     * Prints a confirmation that a task has been removed, and shows the new list size.
     *
     * @param t    the task that was removed
     * @param size the total number of tasks after the removal
     */
    public void showRemoved(Task t, int size) {
        showLine();
        System.out.println("    Say less. I've removed this task:");
        System.out.println("      " + t);
        System.out.println("    Now you have " + size + " tasks in the list.");
        showLine();
    }

    /**
     * Prints the current list of tasks.
     *
     * @param tasks tasks to display, in display order
     */
    public void showList(java.util.List<Task> tasks) {
        showLine();
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + "." + tasks.get(i));
        }
        showLine();
    }

    /**
     * Prints a confirmation that a task has been marked as done.
     *
     * @param t the task that was marked
     */
    public void showMarked(Task t) {
        showLine();
        System.out.println("    Solid, I've marked this task as done:");
        System.out.println("      " + t);
        showLine();
    }

    /**
     * Prints a confirmation that a task has been unmarked (set to not done).
     *
     * @param t the task that was unmarked
     */
    public void showUnmarked(Task t) {
        showLine();
        System.out.println("    Skill issue, I've marked this task as not done yet:");
        System.out.println("      " + t);
        showLine();
    }
}
