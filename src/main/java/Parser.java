public final class Parser {

    public enum Type {
        BYE, LIST, TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE
    }

    // carries the parsed result
    public static final class Parsed {
        public final Type type;
        // for todo/deadline/event
        public final String desc;
        public final String by;
        public final String from;
        public final String to;
        // for mark/unmark/delete 0 based
        public final Integer index0;

        public Parsed(Type type, String desc, String by, String from,
                      String to, Integer index0) {
            this.type = type;
            this.desc = desc;
            this.by = by;
            this.from = from;
            this.to = to;
            this.index0 = index0;
        }
    }

    private static String collapseSpaces(String string) {
        return string.trim().replaceAll("\\s+", " ");
    }

    public static Parsed parse(String fullCommand) throws YinException {
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
            return new Parsed(Type.BYE, null, null, null, null, null);
        case "list":
            if (!command.equals("list")) {
                throw new YinException("list alone is enough!");
            }
            return new Parsed(Type.LIST, null, null, null, null, null);
        case "todo": {
            if (tail.isBlank()) {
                throw new YinException("todo needs a description!" +
                        "\n    e.g.\"todo borrow book\"");
            }
            return new Parsed(Type.TODO, collapseSpaces(tail), null, null, null, null);
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
            return new Parsed(Type.DEADLINE, collapseSpaces(desc), by, null,
                    null, null);
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
            return new Parsed(Type.EVENT, collapseSpaces(desc), null,
                    from, to, null);
        }
        case "mark": {
            if (tail.isBlank()) throw new YinException("Give task number, e.g. \"mark 2\"");
            try {
                int index0 = Integer.parseInt(tail.trim()) - 1;
                return new Parsed(Type.MARK, null, null, null, null, index0);
            } catch (NumberFormatException e) {
                throw new YinException("Task number must be integer! e.g. \"mark 2\"");
            }
        }
        case "unmark": {
            if (tail.isBlank()) throw new YinException("Give task number, e.g. \"unmark 2\"");
            try {
                int index0 = Integer.parseInt(tail.trim()) - 1;
                return new Parsed(Type.UNMARK, null, null, null, null, index0);
            } catch (NumberFormatException e) {
                throw new YinException("Task number must be integer! e.g. \"unmark 2\"");
            }
        }
        case "delete": {
            if (tail.isBlank()) throw new YinException("Give task number, e.g. \"delete 2\"");
            try {
                int index0 = Integer.parseInt(tail.trim()) - 1;
                return new Parsed(Type.DELETE, null, null, null, null, index0);
            } catch (NumberFormatException e) {
                throw new YinException("Task number must be integer! e.g. \"delete 2\"");
            }
        }
        default:
            throw new YinException("Give me a command first >:(" +
                    "\n    Try: todo, deadline, event, list, mark, unmark, delete or bye.");
        }
    }
    private Parser() {}
}
