package yin;

import java.util.ArrayList;
import java.util.List;

/**
 * Wraps a mutable list of {@link Task} objects and provides convenience methods
 * to add, remove, mark, and unmark tasks.
 *
 * <p>This class acts as the in-memory model of the task collection. It is
 * backed by an {@link ArrayList} but exposes only higher-level operations
 * relevant to the domain.</p>
 */
public class TaskList {
    /** Underlying storage for tasks. */
    private final ArrayList<Task> list;

    /** Creates an empty task list. */
    public TaskList() {
        this.list = new ArrayList<>();
    }

    /**
     * Creates a task list pre-populated with the given tasks.
     *
     * @param initial initial tasks to copy into this list
     */
    public TaskList(List<Task> initial) {
        this.list = new ArrayList<>(initial);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return current list size
     */
    public int size() {
        return list.size();
    }

    /**
     * Returns the task at the given index.
     *
     * @param index zero-based index
     * @return the task at that position
     */
    public Task get(int index) {
        return list.get(index);
    }

    /**
     * Adds an existing task to the list.
     *
     * @param task the task to add
     */
    public void add(Task task) {
        list.add(task);
    }

    /**
     * Removes and returns the task at the given index.
     *
     * @param index zero-based index
     * @return the removed task
     */
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

    /**
     * Returns a shallow copy of the underlying task list.
     *
     * @return a new list containing all tasks
     */
    public List<Task> asList() {
        return new ArrayList<>(list);
    }

    /**
     * Creates and adds a new {@link Todo} task.
     *
     * @param description the description of the todo
     * @return the created task
     */
    public Task addTodo(String description) {
        Task task = new Todo(description);
        list.add(task);
        return task;
    }

    /**
     * Creates and adds a new {@link Deadline} task.
     *
     * @param description the task description
     * @param by          the deadline timestamp
     * @return the created task
     */
    public Task addDeadline(String description, java.time.LocalDateTime by) {
        Task task = new Deadline(description, by);
        list.add(task);
        return task;
    }

    /**
     * Creates and adds a new {@link Event} task.
     *
     * @param description the event description
     * @param from        start time
     * @param to          end time
     * @return the created task
     */
    public Task addEvent(String description, java.time.LocalDateTime from,
                         java.time.LocalDateTime to) {
        Task task = new Event(description, from, to);
        list.add(task);
        return task;
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param index zero-based index
     * @return the updated task
     */
    public Task mark(int index) {
        Task task = list.get(index);
        task.mark();
        return task;
    }

    /**
     * Unmarks the task at the given index (sets to not done).
     *
     * @param index zero-based index
     * @return the updated task
     */
    public Task unmark(int index) {
        Task task = list.get(index);
        task.unmark();
        return task;
    }

    /**
     * Deletes and returns the task at the given index.
     *
     * @param index zero-based index
     * @return the deleted task
     */
    public Task delete(int index) {
        return list.remove(index);
    }
}
