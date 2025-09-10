package yin;

import java.time.LocalDateTime;

/**
 * A Command that adds a new Event task to the TaskList.
 * The event requires a description, a start datetime, and an end datetime.
 * If any of these are missing or cannot be parsed, a YinException is thrown with a helpful message.
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
     * @param fromRaw the raw string representing the start datetime
     * @param toRaw the raw string representing the end datetime
     */
    public AddEventCommand(String description, String fromRaw, String toRaw) {
        this.description = description;
        this.fromRaw = fromRaw;
        this.toRaw = toRaw;
    }

    /**
     * Executes this command: validates inputs, parses datetimes, creates the event task,
     * adds it to the task list, displays a confirmation, and saves to storage.
     *
     * @param tasks the task list to add the event into
     * @param ui the user interface to display feedback
     * @param storage the storage to persist the updated task list
     * @throws YinException if the input is invalid or parsing fails
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YinException {
        validateInput();

        LocalDateTime from = parseDate(fromRaw, "start");
        LocalDateTime to = parseDate(toRaw, "end");
        if (to.isBefore(from)) {
            throw new YinException("The end date/time cannot be before start date/time!");
        }

        Task task = tasks.addEvent(normalise(description), from, to);
        ui.showAdded(task, tasks.size());
        storage.save(tasks.asList());
    }

    /**
     * Validates that description, start, and end inputs are present.
     *
     * @throws YinException if any field is missing or blank
     */
    private void validateInput() throws YinException {
        boolean badDescription = (description == null) || description.isBlank();
        boolean badFrom = (fromRaw == null) || fromRaw.isBlank();
        boolean badTo = (toRaw == null) || toRaw.isBlank();
        if (badDescription || badFrom || badTo) {
            throw new YinException("Please feed me a proper input man..."
                    + "\n    Event format: event <desc> /from <start> /to <end>");
        }
    }

    /**
     * Parses a single datetime string with a friendly error if parsing fails.
     *
     * @param raw the raw datetime string
     * @param which label indicating which datetime is being parsed (e.g., \"start\" or \"end\")
     * @return the parsed LocalDateTime
     * @throws YinException if parsing fails
     */
    private LocalDateTime parseDate(String raw, String which) throws YinException {
        try {
            return DateTimes.parseFlexible(raw);
        } catch (Exception e) {
            throw new YinException("I couldn't parse the " + which + " date/time :("
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
