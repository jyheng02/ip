package yin;

import org.junit.Test;
import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void parse_todo_valid_returnsAddTodoCommand() throws YinException {
        Command c = Parser.parse("todo read book");
        assertTrue("Should be AddTodoCommand", c instanceof AddTodoCommand);
    }

    @Test(expected = YinException.class)
    public void parse_todo_empty_throws() throws YinException {
        Parser.parse("todo   ");
    }

    @Test
    public void parse_list_withArgs_returnsUnknownCommand() throws YinException {
        Command c = Parser.parse("list extra");
        assertTrue("Should be UnknownCommand", c instanceof UnknownCommand);
    }

    @Test(expected = YinException.class)
    public void parse_delete_nonInteger_throws() throws YinException {
        Parser.parse("delete x");
    }
}
