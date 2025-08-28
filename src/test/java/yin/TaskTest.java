package yin;

import org.junit.Test;
import static org.junit.Assert.*;

public class TaskTest {

    @Test
    public void mark_unmark_toggle() {
        Task t = new Todo("try gradle");
        assertFalse(t.isDone());
        t.mark();
        assertTrue(t.isDone());
        t.unmark();
        assertFalse(t.isDone());
    }

    @Test
    public void toString_formatsCorrectly() {
        Task t = new Todo("buy milk");
        assertEquals("[T][ ] buy milk", t.toString());
        t.mark();
        assertEquals("[T][X] buy milk", t.toString());
    }
}
