public class Task {
    protected String dsecription;
    protected boolean isDone;

    public Task(String dsecription) {
        this.dsecription = dsecription;
        this.isDone = false;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    @Override
    public String toString() {
        return ("[" + getStatusIcon() + "] " + dsecription);
    }
}
