import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final ArrayList<Task> list;

    public TaskList() {
        this.list = new ArrayList<>();
    }

    public TaskList(List<Task> initial) {
        this.list = new ArrayList<>(initial);
    }

    public int size() {
        return list.size();
    }

    public Task get(int index) {
        return list.get(index);
    }

    public void add(Task task) {
        list.add(task);
    }

    public Task remove(int index) {
        return list.remove(index);
    }

    public List<Task> asList() {
        return new ArrayList<>(list);
    }
}

