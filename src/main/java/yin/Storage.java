package yin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Persists and retrieves {@link Task} data from the local filesystem.
 *
 * <p>Tasks are stored one per line in a simple pipe-delimited format:</p>
 * <ul>
 *   <li>{@code T | 0|1 | description}</li>
 *   <li>{@code D | 0|1 | description | byDateTime}</li>
 *   <li>{@code E | 0|1 | description | fromDateTime | toDateTime}</li>
 * </ul>
 * <p>Date-time fields are serialized using {@link DateTimes#formatStorage(java.time.LocalDateTime)}
 * and parsed using {@link DateTimes#parseFlexible(String)}.</p>
 */
public class Storage {
    /** Path to the data file used for persistence. */
    private final Path file;

    /**
     * Creates a storage backed by the given relative file path.
     *
     * @param relativePath path to the data file, relative to the working directory
     */
    public Storage(String relativePath) {
        this.file = Paths.get(relativePath);
    }

    /**
     * Ensures the parent directory of the data file exists, creating it if necessary.
     *
     * @throws IOException if the directory cannot be created
     */
    private void ensureParentExists() throws IOException {
        Path parent = file.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
    }

    /**
     * Serializes a {@link Task} into the storage line format.
     *
     * @param t the task to serialize
     * @return a single-line, pipe-delimited representation of the task
     */
    private String serialise(Task t) {
        String done = t.isDone() ? "1" : "0";
        if (t instanceof Todo) {
            return String.join(" | ", "T", done, t.getDescription());
        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return String.join(" | ", "D", done,
                    d.getDescription(), DateTimes.formatStorage(d.getBy()));
        } else if (t instanceof Event) {
            Event e = (Event) t;
            return String.join(" | ", "E", done,
                    e.getDescription(),
                    DateTimes.formatStorage(e.getFrom()),
                    DateTimes.formatStorage(e.getTo()));
        }
        return String.join(" | ", "T", done, t.getDescription());
    }

    /**
     * Loads tasks from disk.
     *
     * <p>If the data file or its parent directories do not exist, they are created and
     * an empty list is returned (first run behavior). Malformed lines are skipped.
     * Tasks marked as done in storage are marked accordingly in memory.</p>
     *
     * @return a list of tasks loaded from the data file (possibly empty)
     */
    public List<Task> load() {
        List<Task> list = new ArrayList<>();
        try {
            ensureParentExists();
            if (!Files.exists(file)) {
                Files.createFile(file);
                return list;
            }
            for (String line : Files.readAllLines(file)) {
                String string = line.trim();
                if (string.isEmpty()) {
                    continue;
                }
                String[] p = string.split("\\s*\\|\\s*");
                if (p.length < 3) {
                    continue;
                }
                String type = p[0];
                boolean done = "1".equals(p[1]);
                String desc = p[2];

                Task t;
                switch (type) {
                case "T":
                    t = new Todo(desc);
                    break;
                case "D":
                    if (p.length < 4) {
                        continue;
                    }
                    LocalDateTime by = DateTimes.parseFlexible(p[3]);
                    t = new Deadline(desc, by);
                    break;
                case "E":
                    if (p.length < 5) {
                        continue;
                    }
                    LocalDateTime from = DateTimes.parseFlexible(p[3]);
                    LocalDateTime to = DateTimes.parseFlexible(p[4]);
                    t = new Event(desc, from, to);
                    break;
                default:
                    continue;
                }
                if (done) {
                    t.mark();
                }
                list.add(t);
            }
        } catch (IOException e) {
            System.err.println("Load failed: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Load parse failed: " + e.getMessage());
        }
        return list;
    }

    /**
     * Saves the given tasks to disk, overwriting the file contents.
     *
     * <p>Creates the parent directory and data file if they do not already exist.</p>
     *
     * @param tasks the tasks to persist
     */
    public void save(List<Task> tasks) {
        try {
            ensureParentExists();
            List<String> lines = new ArrayList<>();
            for (Task t : tasks) {
                lines.add(serialise(t));
            }
            Files.write(file, lines,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Save failed: " + e.getMessage());
        }
    }
}
