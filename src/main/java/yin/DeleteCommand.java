package yin;

public class DeleteCommand extends Command{
    private final int index0;

    public DeleteCommand(int index0) {
        this.index0 = index0;
    }

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
