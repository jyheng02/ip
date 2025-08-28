package yin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DateTimes {
    private DateTimes() {}

    // input formats to accept
    private static final List<DateTimeFormatter> INPUTS = List.of(
            DateTimeFormatter.ofPattern("yyy-MM-dd HHmm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
            DateTimeFormatter.ofPattern("d/M/yyyy")
    );

    // display "MMM dd yyyy", show time if present
    private static final DateTimeFormatter DISPLAY_DATE = DateTimeFormatter.ofPattern("MMM d yyyy");
    private static final DateTimeFormatter DISPLAY_DATETIME =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");

    // storage
    private static final DateTimeFormatter STORAGE = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // parse user string into LocalDateTime
    public static LocalDateTime parseFlexible(String text) throws DateTimeParseException {
        String string = text.trim();
        for (DateTimeFormatter formatter : INPUTS) {
            try {
                return LocalDateTime.parse(string, formatter);
            } catch (DateTimeParseException ignored) {
                // try date only -> use midnight
                try {
                    LocalDate d = LocalDate.parse(string, formatter);
                    return LocalDateTime.of(d, LocalTime.MIDNIGHT);
                } catch (DateTimeParseException ignored2) {
                    // continue
                }
            }
        }
        return LocalDateTime.parse(string);
    }

    // format for user display, include time only if !midnight
    public static String formatDisplay(LocalDateTime dt) {
        if (dt.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            return dt.toLocalDate().format(DISPLAY_DATE);
        }
        return dt.format(DISPLAY_DATETIME);
    }

    // format for storage
    public static String formatStorage(LocalDateTime dt) {
        return dt.format(STORAGE);
    }
}
