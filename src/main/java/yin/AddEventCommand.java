package yin;

import java.time.LocalDateTime;

public class AddEventCommand extends Command {
    private final String description;
    private final String fromRaw;
    private final String toRaw;

    public AddEventCommand(String description, String fromRaw, String toRaw) {
        this.description = description;
        this.fromRaw = fromRaw;
        this.toRaw = toRaw;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YinException {
        if (description == null || description.isBlank()
                || fromRaw == null || fromRaw.isBlank()
                || toRaw == null || toRaw.isBlank()) {
            throw new YinException("Please feed me a proper input man..." +
                    "\n    Event format: event <desc> /from <start> /to <end>");
        }
        try {
            LocalDateTime from = DateTimes.parseFlexible(fromRaw);
            LocalDateTime to   = DateTimes.parseFlexible(toRaw);
            Task task = tasks.addEvent(description.trim().replaceAll("\\s+", " "), from, to);
            ui.showAdded(task, tasks.size());
            storage.save(tasks.asList());
        } catch (Exception e) {
            throw new YinException("I couldn't parse one of the dates/times :(" +
                    "\n    Try formats like 2019-10-15 or 2/12/2019 1800.");
        }
    }
}
