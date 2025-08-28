package yin;

public class UnmarkCommand extends Command {
    private final int index0;

    public UnmarkCommand(int index0) {
        this.index0 = index0;
    }

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
