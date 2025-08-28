package yin;

/**
 * Represents a user command that can be executed against the current application state.
 *
 * <p>Subclasses implement specific commands (e.g., {@code list}, {@code todo}, {@code deadline}),
 * defining how they manipulate the {@link TaskList}, interact with the {@link Ui}, and persist
 * changes via the {@link Storage}.</p>
 */
public abstract class Command {
    /**
     * Executes the command against the current state.
     *
     * @param tasks   the task list to operate on
     * @param ui      the user interface for displaying output
     * @param storage the storage handler for persistence
     * @throws YinException if execution fails for any reason
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws YinException;

    /**
     * Indicates whether executing this command should terminate the program.
     *
     * @return {@code true} if the program should exit after execution, {@code false} otherwise
     */
    public boolean isExit() {
        return false;
    }
}
