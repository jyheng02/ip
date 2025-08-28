package yin;

public class MarkCommand extends Command {
    private final int index0;

    public MarkCommand(int index0) {
        this.index0 = index0;
    }

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
