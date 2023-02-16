import org.junit.jupiter.api.*;
import stack.BoundedStack;
import stack.IStack;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class BoundedStackTest {
    private static final int CAPACITY = 5;
    protected IStack<String> wordsStack;

    @BeforeEach
    public void makeBoundedStack(){
        wordsStack = new BoundedStack(5);
        // A stack is empty on construction.
        assertTrue(wordsStack.isEmpty());
        // A stack has size 0 on construction.
        assertEquals(0, wordsStack.size());
    }

    @Test
    @DisplayName("For bounded stacks only: pushing onto a full stack does throw an IllegalStateException")
    void testPushToFullStack(){
        for (var i = 0; i < CAPACITY; i++) {
            wordsStack.push("ola");
        }
        assertThrows(IllegalStateException.class, () -> wordsStack.push("ola"));
    }

    @Test
    void testIsFull(){
        for (var i = 0; i < CAPACITY; i++) {
            wordsStack.push("abc");
            assertFalse(wordsStack.isEmpty());
        }
        assertTrue(((BoundedStack) wordsStack).isFull());

    }


}
