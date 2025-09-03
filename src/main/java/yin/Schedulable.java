package yin;

import java.time.LocalDate;

/**
 * Represents an entity that can be scheduled on specific dates.
 * Implementing classes define how to determine whether the entity
 * occurs on a given LocalDate.
 */
public interface Schedulable {

    /**
     * Checks if this entity occurs on the specified date.
     *
     * @param date the date to check
     * @return true if the entity occurs on the given date, false otherwise
     */
    boolean occursOn(LocalDate date);
}
