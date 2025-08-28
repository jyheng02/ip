package yin;

/**
 * A {@link Command} that adds a new {@link Todo} task to the {@link TaskList}.
 *
 * <p>The todo requires a non-empty description. If none is provided,
 * a {@link YinException} is thrown with a helpful message.</p>
 */
public class AddTodoCommand extends Command {
    /** The description of the todo task. */
    private final String description;

    /**
     * Creates a new command to add a todo.
     *
     * @param description the description of the todo task
     */
    public AddTodoCommand(String description) {
        this.description = description;
    }

    /**
     * Executes this command: validates the description, creates the todo task,
     * adds it to the task list, displays a confirmation, and saves to storage.
     *
     * @param tasks   the task list to add the todo into
     * @param ui      the user interface to display feedback
     * @param storage the storage to persist the updated task list
     * @throws YinException if the description is missing or blank
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YinException {
        if (description == null || description.isBlank()) {
            throw new YinException("todo needs a description!" +
                    "\n    e.g.\"todo borrow book\"");
        }
        Task task = tasks.addTodo(description.trim().replaceAll("\\s+", " "));
        ui.showAdded(task, tasks.size());
        storage.save(tasks.asList());
    }
}
