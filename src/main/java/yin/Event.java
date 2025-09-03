package yin;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents an event task that occurs over a period of time,
 * starting at from and ending at to.
 */
public class Event extends Task {
    /** The start datetime of the event. */
    protected final LocalDateTime from;

    /** The end datetime of the event. */
    protected final LocalDateTime to;

    /**
     * Creates a new event task with the given description and time range.
     *
     * @param description the description of the event
     * @param from the start datetime
     * @param to the end datetime
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start datetime of this event.
     *
     * @return start datetime
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end datetime of this event.
     *
     * @return end datetime
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Checks if this event occurs on the given date.
     * Returns true if the given date is within the inclusive range
     * from start datetime (from) to end datetime (to).
     *
     * @param date the date to check
     * @return true if the event occurs on that date
     */
    @Override
    public boolean occursOn(LocalDate date) {
        return !(to.toLocalDate().isBefore(date) || from.toLocalDate().isAfter(date));
    }

    /**
     * Returns a string representation of the event, including its status,
     * description, start, and end datetimes.
     *
     * @return formatted string
     */
    @Override
    public String toString() {
        return ("[E]" + super.toString()
                + " (from: " + DateTimes.formatDisplay(from)
                + " to: " + DateTimes.formatDisplay(to) + ")");
    }
}
