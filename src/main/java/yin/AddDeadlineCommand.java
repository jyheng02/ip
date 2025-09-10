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
     * Executes this command: validates input, parses the due datetime, creates the deadline task,
     * adds it to the task list, displays a confirmation, and saves to storage.
     *
     * @param tasks the task list to add the deadline into
     * @param ui the user interface to display feedback
     * @param storage the storage to persist the updated task list
     * @throws YinException if the input is invalid or parsing fails
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YinException {
        validateInput();

        LocalDateTime by = parseBy(byRaw);
        Task task = tasks.addDeadline(normalise(description), by);

        ui.showAdded(task, tasks.size());
        storage.save(tasks.asList());
    }

    /**
     * Validates that both the description and datetime string are provided.
     *
     * @throws YinException if description or datetime is missing
     */
    private void validateInput() throws YinException {
        boolean missingDescription = (description == null) || description.isBlank();
        boolean missingBy = (byRaw == null) || byRaw.isBlank();
        if (missingDescription || missingBy) {
            throw new YinException("Give me a proper input please..."
                    + "\n    Deadline format: deadline <desc> /by <when>");
        }
    }

    /**
     * Parses the raw datetime string into a LocalDateTime.
     *
     * @param raw the raw datetime string
     * @return the parsed LocalDateTime
     * @throws YinException if the datetime string cannot be parsed
     */
    private LocalDateTime parseBy(String raw) throws YinException {
        try {
            return DateTimes.parseFlexible(raw);
        } catch (Exception e) {
            throw new YinException("I couldn't parse the date/time :("
                    + "\n    Try formats like 2019-10-15 or 2/12/2019 1800.");
        }
    }

    /**
     * Normalises whitespace in a string by trimming and collapsing spaces.
     *
     * @param s the input string
     * @return the normalised string
     */
    private static String normalise(String s) {
        return s.trim().replaceAll("\\s+", " ");
    }
}
