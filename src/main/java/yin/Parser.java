package yin;

public final class Parser {

    private Parser() {}

    // collapse multiple whitespaces to single space
    private static String collapseSpaces(String string) {
        return string.trim().replaceAll("\\s+", " ");
    }

    // parse raw input into a command object
    public static Command parse(String fullCommand) throws YinException {
        if (fullCommand == null || fullCommand.trim().isEmpty()) {
            throw new YinException("input is empty >:(" +
                    "\n    Give me something please.");
        }

        String command = fullCommand.trim();
        int spaceIndex = command.indexOf(' ');
        String head = (spaceIndex == -1 ? command : command.substring(0, spaceIndex)).toLowerCase();
        String tail = (spaceIndex == -1 ? "" : command.substring(spaceIndex + 1));

        switch (head) {
        case "bye":
            return new ExitCommand();

        case "list":
            if (!command.equals("list")) {
                return new UnknownCommand("list alone is enough!");
            }
            return new ListCommand();

        case "todo": {
            if (tail.isBlank()) {
                throw new YinException("todo needs a description!" +
                        "\n    e.g.\"todo borrow book\"");
            }
            return new AddTodoCommand(collapseSpaces(tail));
        }

        case "deadline": {
            if (tail.isBlank()) {
                throw new YinException("Give me a proper input please..." +
                        "\n    Deadline format: deadline <desc> /by <when>");
            }
            String body = tail.trim();
            int separator = body.indexOf("/by");
            if (separator == -1) {
                throw new YinException("Give me a proper input please..." +
                        "\n    Deadline format: deadline <desc> /by <when>");
            }
            String desc = body.substring(0, separator).trim();
            String by   = body.substring(separator + 3).trim();
            if (desc.isEmpty() || by.isEmpty()) {
                throw new YinException("Give me a proper input please..." +
                        "\n    Deadline format: deadline <desc> /by <when>");
            }
            return new AddDeadlineCommand(collapseSpaces(desc), by);
        }

        case "event": {
            if (tail.isBlank()) {
                throw new YinException("Please feed me some proper input..." +
                        "\n    Event format: event <desc> /from <start> /to <end>");
            }
            String body = tail.trim();
            int fromPosition = body.indexOf("/from");
            int toPosition   = body.indexOf("/to", fromPosition + 5);
            if (fromPosition == -1 || toPosition == -1 || toPosition < fromPosition + 5) {
                throw new YinException("Please feed me a proper input man..." +
                        "\n    Event format: event <desc> /from <start> /to <end>");
            }
            String desc = body.substring(0, fromPosition).trim();
            String from = body.substring(fromPosition + 5, toPosition).trim();
            String to   = body.substring(toPosition + 3).trim();
            if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new YinException("Please feed me some proper input man..." +
                        "\n    Event format: event <desc> /from <start> /to <end>");
            }
            return new AddEventCommand(collapseSpaces(desc), from, to);
        }

        case "mark": {
            if (tail.isBlank()) {
                throw new YinException("Give task number, e.g. \"mark 2\"");
            }
            try {
                int index0 = Integer.parseInt(tail.trim()) - 1; // convert to 0-based
                return new MarkCommand(index0);
            } catch (NumberFormatException e) {
                throw new YinException("Task number must be integer! e.g. \"mark 2\"");
            }
        }

        case "unmark": {
            if (tail.isBlank()) {
                throw new YinException("Give task number, e.g. \"unmark 2\"");
            }
            try {
                // convert to 0-based
                int index0 = Integer.parseInt(tail.trim()) - 1;
                return new UnmarkCommand(index0);
            } catch (NumberFormatException e) {
                throw new YinException("Task number must be integer! e.g. \"unmark 2\"");
            }
        }

        case "delete": {
            if (tail.isBlank()) {
                throw new YinException("Give task number, e.g. \"delete 2\"");
            }
            try {
                // convert to 0-based
                int index0 = Integer.parseInt(tail.trim()) - 1;
                return new DeleteCommand(index0);
            } catch (NumberFormatException e) {
                throw new YinException("Task number must be integer! e.g. \"delete 2\"");
            }
        }

        default:
            // unknown command -> handled by a Command that throws on execute()
            return new UnknownCommand("Give me a command first >:(" +
                    "\n    Try: todo, deadline, event, list, mark, unmark, delete or bye.");
        }
    }
}
