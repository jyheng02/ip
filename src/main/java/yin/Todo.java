package yin;

/**
 * Represents a Todo task which only has a description and no associated date/time.
 * A Todo is a basic type of task managed by Yin.
 */
public class Todo extends Task {

    /**
     * Creates a new Todo with the specified description.
     *
     * @param description Description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of the Todo task,
     * including its type indicator "[T]".
     *
     * @return String representation of this Todo task.
     */
    @Override
    public String toString() {
        return ("[T]" + super.toString());
    }
}
