public class UnknownCommand extends Command {
    private final String message;

    public UnknownCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YinException {
        throw new YinException(message);
    }
}
