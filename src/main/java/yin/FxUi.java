package yin;

import java.util.List;

/**
 * A GUI version of Ui that stores messages in a buffer instead of printing them to the console.
 * The buffer can be flushed to obtain the collected output as a string.
 */
public class FxUi extends Ui {
    private final StringBuilder buffer = new StringBuilder();

    /**
     * Adds a line of text to the buffer.
     *
     * @param s Line of text to add
     */
    private void addLine(String s) {
        buffer.append(s).append("\n");
    }

    /**
     * Returns all buffered output and clears the buffer.
     *
     * @return Accumulated output as a single string
     */
    public String flush() {
        String out = buffer.toString();
        buffer.setLength(0); // clear
        return out.trim();
    }

    /**
     * Shows the welcome message when the application starts.
     */
    @Override
    public void showWelcome() {
        addLine("Hello! I'm Yin");
        addLine("How can I be of assistance?");
    }

    /**
     * Shows the exit message when the application ends.
     */
    @Override
    public void showExit() {
        addLine("See you later alligator.");
    }

    /**
     * Shows an error message.
     *
     * @param msg Error message text
     */
    @Override
    public void showError(String msg) {
        addLine(msg);
    }

    /**
     * Shows a confirmation when a task is added.
     *
     * @param t Task that was added
     * @param size New number of tasks in the list
     */
    @Override
    public void showAdded(Task t, int size) {
        assert t != null : "Added task should not be null";
        addLine("Added: " + t);
        addLine("Now you have " + size + " tasks.");
    }

    /**
     * Shows a confirmation when a task is removed.
     *
     * @param t Task that was removed
     * @param size New number of tasks in the list
     */
    @Override
    public void showRemoved(Task t, int size) {
        assert t != null : "Removed task should not be null";
        addLine("Removed: " + t);
        addLine("Now you have " + size + " tasks.");
    }

    /**
     * Shows the list of all tasks.
     *
     * @param tasks List of tasks
     */
    @Override
    public void showList(List<Task> tasks) {
        assert tasks != null : "List of tasks should not be null";
        addLine("Here are your tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            addLine((i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Shows a confirmation when a task is marked as done.
     *
     * @param t Task that was marked
     */
    @Override
    public void showMarked(Task t) {
        assert t != null : "Marked task should not be null";
        addLine("Marked done: " + t);
    }

    /**
     * Shows a confirmation when a task is unmarked.
     *
     * @param t Task that was unmarked
     */
    @Override
    public void showUnmarked(Task t) {
        assert t != null : "Unmarked task should not be null";
        addLine("Unmarked: " + t);
    }

    /**
     * Shows tasks that match a search keyword.
     *
     * @param matches List of matching tasks
     */
    @Override
    public void showMatches(List<Task> matches) {
        assert matches != null : "Match list should not be null";
        if (matches.isEmpty()) {
            addLine("No matches found.");
            return;
        }
        addLine("Matching tasks:");
        for (int i = 0; i < matches.size(); i++) {
            addLine((i + 1) + ". " + matches.get(i));
        }
    }
}
