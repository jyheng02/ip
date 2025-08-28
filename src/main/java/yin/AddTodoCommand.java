package yin;

public class AddTodoCommand extends Command {
    private final String description;

    public AddTodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YinException {
        if (description == null || description.isBlank()) {
            throw new YinException("todo needs a description!" +
                    "\n    e.g.\"todo borrow book\"");        }
        Task task = tasks.addTodo(description.trim().replaceAll("\\s+", " "));
        ui.showAdded(task, tasks.size());
        storage.save(tasks.asList());
    }
}
