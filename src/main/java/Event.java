import java.time.LocalDate;
import java.time.LocalDateTime;

public class Event extends Task {
    protected final LocalDateTime from;
    protected final LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return !(to.toLocalDate().isBefore(date) || from.toLocalDate().isAfter(date));
    }

    @Override
    public String toString() {
        return ("[E]" + super.toString()
                + " (from: " + DateTimes.formatDisplay(from)
                + " to: " + DateTimes.formatDisplay(to) + ")");
    }
}
