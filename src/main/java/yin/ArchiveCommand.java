package yin;

import java.util.ArrayList;
import java.util.List;

/**
 * Command to archive tasks.
 * Can archive all tasks or only those marked as done,
 * then removes them from the active list and writes them to an archive file.
 */
public class ArchiveCommand extends Command {
    /** Archive scope selector. */
    public enum Scope { ALL, DONE }

    private final Scope scope;

    /**
     * Creates an ArchiveCommand with the given scope.
     *
     * @param scope whether to archive all tasks or only done tasks
     */
    public ArchiveCommand(Scope scope) {
        this.scope = scope;
    }

    /**
     * Executes the archive command: selects tasks according to the scope, appends them
     * to the archive via storage, removes them from the active list, shows a summary,
     * and saves the remaining tasks.
     *
     * @param tasks the active task list to read from and mutate
     * @param ui the user interface to display feedback
     * @param storage the storage used for persistence and archiving
     * @throws YinException if an I/O problem occurs during archiving
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YinException {
        List<Task> toArchive = new ArrayList<>();

        if (scope == Scope.ALL) {
            // Copy everything in current order, then clear the list.
            toArchive.addAll(tasks.asList());
            tasks.clear();
        } else { // Scope.DONE
            // Walk backwards so indices remain valid; re-insert at front to keep original order.
            for (int i = tasks.size() - 1; i >= 0; i--) {
                Task t = tasks.get(i);
                if (t.isDone()) {
                    toArchive.add(0, t);
                    tasks.remove(i);
                }
            }
        }

        if (toArchive.isEmpty()) {
            ui.showArchived(0, scope.name().toLowerCase());
            return;
        }

        storage.appendArchive(toArchive);
        ui.showArchived(toArchive.size(), scope.name().toLowerCase());
        storage.save(tasks.asList());
    }
}
