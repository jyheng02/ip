package yin;

/**
 * Represents a command that lists all tasks in the task list.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command by showing all tasks to the user.
     *
     * @param tasks The task list containing all tasks.
     * @param ui The UI for displaying messages.
     * @param storage The storage (not modified by this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks.asList());
    }
}
