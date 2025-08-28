import java.time.LocalDateTime;

public class AddDeadlineCommand extends Command {
    private final String description;
    private final String byRaw;

    public AddDeadlineCommand(String description, String byRaw) {
        this.description = description;
        this.byRaw = byRaw;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YinException {
        if (description == null || description.isBlank() || byRaw == null || byRaw.isBlank()) {
            throw new YinException("Give me a proper input please..." +
                    "\n    Deadline format: deadline <desc> /by <when>");
        }
        try {
            LocalDateTime by = DateTimes.parseFlexible(byRaw);
            Task task = tasks.addDeadline(description.trim().replaceAll("\\s+", " "), by);
            ui.showAdded(task, tasks.size());
            storage.save(tasks.asList());
        } catch (Exception e) {
            throw new YinException("I couldn't parse the date/time :(" +
                    "\n    Try formats like 2019-10-15 or 2/12/2019 1800.");
        }
    }
}
