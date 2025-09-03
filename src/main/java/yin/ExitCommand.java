package yin;

/**
 * Represents a command that exits the application.
 */
public class ExitCommand extends Command {

    /**
     * Executes the exit command by showing the exit message to the user.
     *
     * @param tasks The task list (not used here).
     * @param ui The UI for displaying messages.
     * @param storage The storage (not used here).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showExit();
    }

    /**
     * Indicates that this command will terminate the program.
     *
     * @return true, as this command exits the application.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
