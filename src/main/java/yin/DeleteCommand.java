package yin;

/**
 * Represents a command that deletes a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int index0;

    /**
     * Creates a DeleteCommand to delete a task at the given index.
     *
     * @param index0 Zero-based index of the task to delete
     */
    public DeleteCommand(int index0) {
        this.index0 = index0;
    }

    /**
     * Executes the delete command by removing the task at the specified index.
     * Updates storage and shows confirmation to the user.
     *
     * @param tasks The task list to operate on
     * @param ui The UI for displaying messages
     * @param storage The storage to update after deletion
     * @throws YinException If the index is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YinException {
        if (index0 < 0 || index0 >= tasks.size()) {
            throw new YinException("Invalid index to delete");
        }
        Task removed = tasks.delete(index0);
        ui.showRemoved(removed, tasks.size());
        storage.save(tasks.asList());
    }
}
