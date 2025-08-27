import java.time.LocalDate;
import java.time.LocalDateTime;

public class Deadline extends Task {
    protected final LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public LocalDateTime getBy() {
        return by;
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return by.toLocalDate().equals(date);
    }

    @Override
    public String toString() {
        return ("[D]" + super.toString() + " (by: "
                + DateTimes.formatDisplay(by) + ")");
    }
}
