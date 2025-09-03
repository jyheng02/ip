package yin;

/**
 * Represents a user command that can be executed against the current application state.
 * Subclasses implement specific commands (e.g., list, todo, deadline),
 * defining how they manipulate the TaskList, interact with the Ui,
 * and persist changes via the Storage.
 */
public abstract class Command {
    /**
     * Executes the command against the current state.
     *
     * @param tasks the task list to operate on
     * @param ui the user interface for displaying output
     * @param storage the storage handler for persistence
     * @throws YinException if execution fails for any reason
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws YinException;

    /**
     * Indicates whether executing this command should terminate the program.
     *
     * @return true if the program should exit after execution, false otherwise
     */
    public boolean isExit() {
        return false;
    }
}
