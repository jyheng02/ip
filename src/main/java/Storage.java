import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path file;

    public Storage(String relativePath) {
        this.file = Paths.get(relativePath);
    }

    // create parent directory if missing
    private void ensureParentExists() throws IOException {
        Path parent = file.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
    }

    private String serialise(Task t) {
        String done = t.isDone() ? "1" : "0";
        if (t instanceof Todo) {
            return String.join(" | ", "T", done, t.getDescription());
        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return String.join(" | ", "D", done,
                    d.getDescription(), d.getBy());
        } else if (t instanceof Event) {
            Event e = (Event) t;
            return String.join(" | ", "E", done,
                    e.getDescription(), e.getFrom(), e.getTo());
        }
        return String.join(" | ", "T", done, t.getDescription());
    }

    // load tasks from disk, creates folders/file if missing
    // returns empty list on first run
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
                    String by = p.length > 3 ? p[3] : "";
                    t = new Deadline(desc, by);
                    break;
                case "E":
                    String from = p.length  > 3 ? p[3] : "";
                    String to = p.length > 4 ? p[4] : "";
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
        }
        return list;
    }

    // save tasks to disk (overwrites file), creates folders/file if missing
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
