package yin;

import java.util.List;

/**
 * Command that finds tasks whose descriptions contain a given keyword (case-insensitive).
 */
public class FindCommand extends Command {
    private final String word;

    public FindCommand(String word) {
        this.word = word;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YinException {
        List<Task> matches = tasks.find(word);
        ui.showMatches(matches);
    }
}
