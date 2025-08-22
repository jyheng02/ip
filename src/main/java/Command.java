public enum Command {
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    LIST("list"),
    BYE("bye"),
    UNKNOWN("");

    public final String word;

    Command(String word) {
        this.word = word;
    }

    // extract command word from whole command string given by user
    public static Command getCommand(String command) {
        if (command == null || command.isEmpty()) {
            return UNKNOWN;
        }
        /* find first space, if no space commandName == whole command
        else commandName == from index = 0 to index of first space
        scan through list of command enums and find match */
        int spaceIndex = command.indexOf(' ');
        String commandName = ((spaceIndex == -1) ? command : command.substring(0, spaceIndex)).toLowerCase();
        for (Command c : Command.values()) {
            if (!c.word.isEmpty() && c.word.equals(commandName)) {
                return c;
            }
        }
        return UNKNOWN;
    }
}
