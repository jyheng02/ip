public abstract class Command {
    // execute command against current state
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws YinException;

    // whether commadn should exit program after execution or not
    public boolean isExit() {
        return false;
    }
}
