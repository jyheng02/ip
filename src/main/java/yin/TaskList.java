package yin;

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

    /**
     * Returns tasks whose descriptions contain the keyword (case-insensitive).
     *
     * @param word text to search for
     * @return list of matching tasks (may be empty)
     */
    public List<Task> find(String word) {
        String w = word.toLowerCase();
        List<Task> out = new ArrayList<>();
        for (Task task : list) {
            if (task.getDescription().toLowerCase().contains(w)) {
                out.add(task);
            }
        }
        return out;
    }

    public List<Task> asList() {
        return new ArrayList<>(list);
    }

    // add Todo
    public Task addTodo(String description) {
        Task task = new Todo(description);
        list.add(task);
        return task;
    }

    // add Deadline
    public Task addDeadline(String description, java.time.LocalDateTime by) {
        Task task = new Deadline(description, by);
        list.add(task);
        return task;
    }

    // add Event
    public Task addEvent(String description, java.time.LocalDateTime from,
                         java.time.LocalDateTime to) {
        Task task = new Event(description, from, to);
        list.add(task);
        return task;
    }

    // mark by 0 based idnex
    public Task mark(int index) {
        Task task = list.get(index);
        task.mark();
        return task;
    }

    // unmark byu 0 based index
    public Task unmark(int index) {
        Task task = list.get(index);
        task.unmark();
        return task;
    }

    public Task delete(int index) {
        return list.remove(index);
    }
}

