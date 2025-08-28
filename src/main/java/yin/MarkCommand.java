package yin;

/**
 * A {@link Command} that marks a task in the {@link TaskList} as done.
 */
public class MarkCommand extends Command {
    /** Zero-based index of the task to mark. */
    private final int index0;

    /**
     * Creates a new command to mark the task at the given index as done.
     *
     * @param index0 zero-based index of the task to mark
     */
    public MarkCommand(int index0) {
        this.index0 = index0;
    }

    /**
     * Executes this command: validates the index, marks the task as done,
     * displays a confirmation, and saves to storage.
     *
     * @param tasks   the task list to update
     * @param ui      the user interface to display feedback
     * @param storage the storage to persist the updated task list
     * @throws YinException if the index is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YinException {
        if (index0 < 0 || index0 >= tasks.size()) {
            throw new YinException("Invalid index to mark");
        }
        Task task = tasks.mark(index0);
        ui.showMarked(task);
        storage.save(tasks.asList());
    }
}
