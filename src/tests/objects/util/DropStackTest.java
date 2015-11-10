package objects.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Lambeaux on 8/13/2015.
 * Basic testing for the DropStack object that I custom made for the program.
 *
 */
public class DropStackTest {

    @Test
    public void testEasyPeekVsPop() throws Exception
    {
        DropStack<Integer> dataStack = new DropStack<>(5);

        dataStack.Push(120);
        dataStack.Push(330);

        assertEquals("Validate count", 2, dataStack.GetCount());
        assertEquals("Validate peek", 330, (int)dataStack.Peek());
        assertEquals("Validate pop", 330, (int)dataStack.Pop());
    }

    @Test
    public void testEasyOverlap() throws Exception
    {
        DropStack<Integer> dataStack = new DropStack<>(3);

        dataStack.Push(120);

        dataStack.Push(330);
        dataStack.Push(450);
        dataStack.Push(500);

        assertEquals("Validate count", 3, dataStack.GetCount());
        assertEquals("Validate peek", 500, (int) dataStack.Peek());
        assertEquals("Validate pop", 500, (int) dataStack.Pop());
        assertEquals("Validate peek", 450, (int) dataStack.Peek());
        assertEquals("Validate pop", 450, (int)dataStack.Pop());
        assertEquals("Validate count", 1, dataStack.GetCount());
        assertEquals("Validate peek", 330, (int) dataStack.Peek());

        dataStack.Push(603);
        dataStack.Push(700);

        assertEquals("Validate count", 3, dataStack.GetCount());
        assertEquals("Validate peek", 700, (int) dataStack.Peek());

        dataStack.Push(800);

        assertEquals("Validate pop", 800, (int) dataStack.Pop());
        assertEquals("Validate pop", 700, (int)dataStack.Pop());
        assertEquals("Validate pop", 603, (int)dataStack.Pop());
        assertEquals("Validate pop", null, dataStack.Pop());
    }

    @Test
    public void testBulkLayeredInput() throws Exception
    {
        DropStack<Integer> dataStack = new DropStack<>(5);

        dataStack.Push(78201);
        dataStack.Push(78202);
        dataStack.Push(78203);
        dataStack.Push(78204);
        dataStack.Push(78205);
        dataStack.Push(78206);
        dataStack.Push(78207);
        dataStack.Push(78208);
        dataStack.Push(78209);
        dataStack.Push(78210);

        assertEquals("Validate count", 5, dataStack.GetCount());
        assertEquals("Validate peek", 78210, (int) dataStack.Peek());
        assertEquals("Validate pop", 78210, (int) dataStack.Pop());
        assertEquals("Validate peek", 78209, (int) dataStack.Peek());
        assertEquals("Validate pop", 78209, (int) dataStack.Pop());
        assertEquals("Validate count", 3, dataStack.GetCount());
        assertEquals("Validate peek", 78208, (int) dataStack.Peek());

        dataStack.Push(78201);
        dataStack.Push(78202);

        assertEquals("Validate count", 5, dataStack.GetCount());
        assertEquals("Validate pop", 78202, (int) dataStack.Pop());

        dataStack.Push(78209);
        dataStack.Push(78210);

        assertEquals("Validate pop", 78210, (int) dataStack.Pop());
        assertEquals("Validate pop", 78209, (int) dataStack.Pop());
        assertEquals("Validate pop", 78201, (int) dataStack.Pop());
        assertEquals("Validate pop", 78208, (int) dataStack.Pop());
        assertEquals("Validate pop", 78207, (int) dataStack.Pop());
        assertEquals("Validate pop", null, dataStack.Pop());
    }

    @Test
    public void testLinearInput() throws Exception
    {
        DropStack<Integer> dataStack = new DropStack<>(3);

        dataStack.Push(78201);
        dataStack.Push(78202);
        dataStack.Push(78203);
        dataStack.Push(78204);
        dataStack.Push(78205);
        dataStack.Push(78206);
        dataStack.Push(78207);
        dataStack.Push(78208);
        dataStack.Push(78209);
        dataStack.Push(78210);

        assertEquals("Validate pop", 78210, (int) dataStack.Pop());
        assertEquals("Validate pop", 78209, (int) dataStack.Pop());
        assertEquals("Validate pop", 78208, (int) dataStack.Pop());
        assertEquals("Validate pop", null, dataStack.Pop());

        dataStack.Push(87201);
        dataStack.Push(87202);
        dataStack.Push(87203);
        dataStack.Push(87204);
        dataStack.Push(87205);
        dataStack.Push(87206);
        dataStack.Push(87207);
        dataStack.Push(87208);
        dataStack.Push(87209);
        dataStack.Push(87210);

        assertEquals("Validate pop", 87210, (int) dataStack.Pop());
        assertEquals("Validate pop", 87209, (int) dataStack.Pop());
        assertEquals("Validate pop", 87208, (int) dataStack.Pop());
        assertEquals("Validate pop", null, dataStack.Pop());
    }

}