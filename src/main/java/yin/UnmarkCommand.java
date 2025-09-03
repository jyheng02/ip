package yin;

/**
 * A command that marks a task as "not done" again.
 */
public class UnmarkCommand extends Command {
    private final int index0;

    /**
     * Creates a new UnmarkCommand for the given task index.
     *
     * @param index0 the zero-based index of the task to unmark
     */
    public UnmarkCommand(int index0) {
        this.index0 = index0;
    }

    /**
     * Runs the unmark command.
     * It finds the task at the given index, sets it to "not done", tells the user,
     * and updates storage.
     *
     * @param tasks the task list
     * @param ui the ui for showing messages
     * @param storage the storage to save changes
     * @throws YinException if the index is out of range
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YinException {
        if (index0 < 0 || index0 >= tasks.size()) {
            throw new YinException("Invalid index to unmark");
        }
        Task task = tasks.unmark(index0);
        ui.showUnmarked(task);
        storage.save(tasks.asList());
    }
}
