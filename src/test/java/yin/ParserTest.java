package yin;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ParserTest {

    @Test
    public void parse_todoValid_returnsAddTodoCommand() throws YinException {
        Command c = Parser.parse("todo read book");
        assertTrue("Should be AddTodoCommand", c instanceof AddTodoCommand);
    }

    @Test(expected = YinException.class)
    public void parse_todoEmpty_throws() throws YinException {
        Parser.parse("todo   ");
    }

    @Test
    public void parse_listWithArgs_returnsUnknownCommand() throws YinException {
        Command c = Parser.parse("list extra");
        assertTrue("Should be UnknownCommand", c instanceof UnknownCommand);
    }

    @Test(expected = YinException.class)
    public void parse_deleteNonInteger_throws() throws YinException {
        Parser.parse("delete x");
    }
}
