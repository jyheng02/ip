import java.time.LocalDate;

public interface Schedulable {
    boolean occursOn(LocalDate date);
}
