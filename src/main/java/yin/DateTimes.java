package yin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Utility class for parsing and formatting LocalDateTime objects used in tasks.
 * Supports multiple flexible input formats for parsing,
 * and provides distinct formatters for user display and storage serialisation.
 */
public class DateTimes {

    /** Accepted input formats for parsing user-provided date/time strings. */
    private static final List<DateTimeFormatter> INPUTS = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
            DateTimeFormatter.ofPattern("d/M/yyyy")
    );

    /** Formatter for display: "MMM d yyyy" (no time). */
    private static final DateTimeFormatter DISPLAY_DATE = DateTimeFormatter.ofPattern("MMM d yyyy");

    /** Formatter for display: "MMM d yyyy, h:mma" (with time). */
    private static final DateTimeFormatter DISPLAY_DATETIME =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");

    /** Formatter for storage (ISO local date-time). */
    private static final DateTimeFormatter STORAGE = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /** Hidden constructor; this class should not be instantiated. */
    private DateTimes() {}

    /**
     * Parses a user-provided string into a LocalDateTime.
     * Tries each of the accepted input formats. If only a date is provided,
     * it defaults the time to midnight.
     * If none of the formats match, it falls back to LocalDateTime.parse(string).
     *
     * @param text the input string
     * @return parsed date-time
     * @throws DateTimeParseException if parsing fails
     */
    public static LocalDateTime parseFlexible(String text) throws DateTimeParseException {
        String s = text.trim();

        for (DateTimeFormatter f : INPUTS) {
            try {
                return LocalDateTime.parse(s, f);
            } catch (DateTimeParseException ignored) {
                // try as date-only with midnight
            }
            try {
                LocalDate d = LocalDate.parse(s, f);
                return LocalDateTime.of(d, LocalTime.MIDNIGHT);
            } catch (DateTimeParseException ignored) {
                // try next formatter
            }
        }

        // Final fallback: JDK default parsing (e.g., ISO)
        return LocalDateTime.parse(s);
    }

    /**
     * Formats a date-time for user display.
     * If the time is exactly midnight, only the date is shown.
     * Otherwise, both date and time are shown.
     *
     * @param dt the date-time to format
     * @return formatted string suitable for display
     */
    public static String formatDisplay(LocalDateTime dt) {
        if (dt.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            return dt.toLocalDate().format(DISPLAY_DATE);
        }
        return dt.format(DISPLAY_DATETIME);
    }

    /**
     * Formats a date-time for persistent storage.
     *
     * @param dt the date-time to format
     * @return ISO-8601 formatted string
     */
    public static String formatStorage(LocalDateTime dt) {
        return dt.format(STORAGE);
    }
}
