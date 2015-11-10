package objects.util;

/**
 * Created by Lambeaux on 8/13/2015.
 * Simple data structure for continuously adding to a stack
 * while maintaining a fixed size. The oldest item on the
 * stack will drop off to make room for more.
 *
 */
public class DropStack<T>
{
    private Object[] $T_Array;

    private int $Head;
    private int $Count;
    private int $Size;

    public int GetCount() { return $Count; }

    public DropStack(int size)
    {
        $T_Array = new Object[size];

        $Head = 0;
        $Count = 0;
        $Size = size;
    }

    public void Push(T item)
    {
        $T_Array[$Head] = item;
        $Head++;

        if ($Head == $Size)
            $Head = 0;

        if ($Count != $Size)
            $Count++;
    }

    public T Pop()
    {
        if ($Count == 0)
            return null;

        $Count--;

        if ($Head == 0)
            $Head = $Size - 1;

        else
            $Head--;

        return (T)$T_Array[$Head];
    }

    public T Peek()
    {
        if ($Count == 0)
            return null;

        if ($Head == 0)
            return (T)$T_Array[$Size - 1];

        else
            return (T)$T_Array[$Head - 1];
    }
}
