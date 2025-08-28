package yin;

import java.time.LocalDate;

/**
 * Represents a general task with a description and completion status.
 *
 * <p>This is the base class for concrete task types such as {@link Todo},
 * {@link Deadline}, and {@link Event}. It provides common fields and
 * operations like marking/unmarking, checking status, and rendering as a string.</p>
 */
public class Task implements Schedulable {
    /** Short description of the task. */
    protected String description;

    /** Whether the task has been marked as done. */
    protected boolean isDone;

    /**
     * Creates a new task with the given description, initially not done.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Marks this task as done. */
    public void mark() {
        this.isDone = true;
    }

    /** Marks this task as not done. */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns the status icon representing done/undone state.
     *
     * @return {@code "X"} if done, or a space otherwise
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns the task description.
     *
     * @return the description text
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether the task is marked as done.
     *
     * @return {@code true} if done, {@code false} otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Indicates whether this task occurs on a specific date.
     *
     * <p>By default (for tasks without dates, e.g. {@link Todo}),
     * this returns {@code false}. Subclasses that are date-based
     * may override this method.</p>
     *
     * @param date the date to check
     * @return {@code true} if the task occurs on that date
     */
    @Override
    public boolean occursOn(LocalDate date) {
        return false;
    }

    /**
     * Returns a string representation of the task, showing its status
     * and description.
     *
     * @return formatted string
     */
    @Override
    public String toString() {
        return ("[" + getStatusIcon() + "] " + description);
    }
}
