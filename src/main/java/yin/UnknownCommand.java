package yin;

/**
 * A command that represents something the program does not understand.
 * When run, it throws a YinException with a message.
 */
public class UnknownCommand extends Command {
    private final String message;

    /**
     * Creates a new UnknownCommand with the message to show.
     *
     * @param message the error message explaining what went wrong
     */
    public UnknownCommand(String message) {
        this.message = message;
    }

    /**
     * Executes this command. Since it is unknown, it always throws an exception.
     *
     * @param tasks the task list (not used here)
     * @param ui the UI for showing messages (not used here)
     * @param storage the storage (not used here)
     * @throws YinException always thrown with the error message
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YinException {
        throw new YinException(message);
    }
}
