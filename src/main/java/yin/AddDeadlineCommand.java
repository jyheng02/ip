package yin;

import java.time.LocalDateTime;

/**
 * A Command that adds a new Deadline task to the TaskList.
 * The deadline requires a description and a due datetime.
 * If either is missing, or cannot be parsed, a YinException is thrown with a message.
 */
public class AddDeadlineCommand extends Command {
    /** The description of the deadline task. */
    private final String description;

    /** The raw user input for the due datetime. */
    private final String byRaw;

    /**
     * Creates a new command to add a deadline.
     *
     * @param description the description of the deadline task
     * @param byRaw the raw string representing the due datetime
     */
    public AddDeadlineCommand(String description, String byRaw) {
        this.description = description;
        this.byRaw = byRaw;
    }

    /**
     * Executes this command: parses the due datetime, creates the deadline task,
     * adds it to the task list, displays a confirmation, and saves to storage.
     *
     * @param tasks the task list to add the deadline into
     * @param ui the user interface to display feedback
     * @param storage the storage to persist the updated task list
     * @throws YinException if the input is invalid or parsing fails
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YinException {
        if (description == null || description.isBlank() || byRaw == null || byRaw.isBlank()) {
            throw new YinException("Give me a proper input please..."
                    + "\n    Deadline format: deadline <desc> /by <when>");
        }
        try {
            LocalDateTime by = DateTimes.parseFlexible(byRaw);
            Task task = tasks.addDeadline(description.trim().replaceAll("\\s+", " "), by);
            ui.showAdded(task, tasks.size());
            storage.save(tasks.asList());
        } catch (Exception e) {
            throw new YinException("I couldn't parse the date/time :("
                   + "\n    Try formats like 2019-10-15 or 2/12/2019 1800.");
        }
    }
}
