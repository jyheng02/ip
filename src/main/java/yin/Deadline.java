package yin;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a deadline task that must be completed by a specific datetime.
 */
public class Deadline extends Task {
    /** The due datetime of the deadline. */
    protected final LocalDateTime by;

    /**
     * Creates a new deadline task with the given description and due datetime.
     *
     * @param description the description of the deadline task
     * @param by          the due datetime
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the due datetime of this deadline.
     *
     * @return due datetime
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Checks if this deadline falls on the given date.
     *
     * @param date the date to check
     * @return {@code true} if the deadline is due on that date
     */
    @Override
    public boolean occursOn(LocalDate date) {
        return by.toLocalDate().equals(date);
    }

    /**
     * Returns a string representation of the deadline, including its status,
     * description, and due datetime.
     *
     * @return formatted string
     */
    @Override
    public String toString() {
        return ("[D]" + super.toString() + " (by: "
                + DateTimes.formatDisplay(by) + ")");
    }
}
