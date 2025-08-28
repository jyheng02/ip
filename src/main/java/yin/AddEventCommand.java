package yin;

import java.time.LocalDateTime;

/**
 * A {@link Command} that adds a new {@link Event} task to the {@link TaskList}.
 *
 * <p>The event requires a description, a start datetime, and an end datetime.
 * If any of these are missing or cannot be parsed, a {@link YinException}
 * is thrown with a helpful message.</p>
 */
public class AddEventCommand extends Command {
    /** The description of the event. */
    private final String description;

    /** The raw user input for the start datetime. */
    private final String fromRaw;

    /** The raw user input for the end datetime. */
    private final String toRaw;

    /**
     * Creates a new command to add an event.
     *
     * @param description the description of the event
     * @param fromRaw     the raw string representing the start datetime
     * @param toRaw       the raw string representing the end datetime
     */
    public AddEventCommand(String description, String fromRaw, String toRaw) {
        this.description = description;
        this.fromRaw = fromRaw;
        this.toRaw = toRaw;
    }

    /**
     * Executes this command: parses the datetimes, creates the event task,
     * adds it to the task list, displays a confirmation, and saves to storage.
     *
     * @param tasks   the task list to add the event into
     * @param ui      the user interface to display feedback
     * @param storage the storage to persist the updated task list
     * @throws YinException if the input is invalid or parsing fails
     */
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
