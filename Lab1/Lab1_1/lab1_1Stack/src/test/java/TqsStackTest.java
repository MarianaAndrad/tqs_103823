
import org.junit.jupiter.api.*;
import stack.IStack;
import stack.TqsStack;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TqsStackTest {

    protected IStack<String> wordsStack;

    @BeforeEach
    void setUp(){
        wordsStack= new TqsStack<String>();

        // A stack is empty on construction.
        assertTrue(wordsStack.isEmpty());
        // A stack has size 0 on construction.
        assertEquals(0, wordsStack.size());

    }

    @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n")
    @Test
    void testPushesToEmptyStack(){
        int numberOfPushes = 4;
        wordsStack.push("ola");
        wordsStack.push("Hello");
        wordsStack.push("adeus");
        wordsStack.push("bye");

        assertFalse(wordsStack.isEmpty());
        assertEquals(numberOfPushes, wordsStack.size());
    }

    @DisplayName("If one pushes x then pops, the value popped is x.")
    @Test
    void testPushThenPop(){
        String message = "Hola";
        wordsStack.push("ola");
        wordsStack.push(message);
        assertEquals(message, wordsStack.pop());
    }

    @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same.")
    @Test
    void testPushTenPeek(){
        String message = "Hola";
        wordsStack.push("ola");
        wordsStack.push(message);
        int size = wordsStack.size();

        assertEquals(message, wordsStack.peek());
        assertEquals(size, wordsStack.size());
    }

    @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0.")
    @Test
    void testPoppingDownToEmpty(){
        int numberOfPushes = 4;
        wordsStack.push("ola");
        wordsStack.push("Hello");
        wordsStack.push("adeus");
        wordsStack.push("bye");

        for(int x = 0;  x< numberOfPushes; x++){
            wordsStack.pop();
        }

        assertTrue(wordsStack.isEmpty());
        assertEquals(0,wordsStack.size());
    }

    @DisplayName("Popping from an empty stack does throw a NoSuchElementException.")
    @Test
    void testPopOnEmptyStackThrows(){
        assertTrue(wordsStack.isEmpty());
        assertThrows(NoSuchElementException.class, ()->wordsStack.pop());
    }

    @DisplayName("Peeking into an empty stack does throw a NoSuchElementException")
    @Test
    void testPeekIntoEmptyStackThrows(){
        assertTrue(wordsStack.isEmpty());
        assertThrows(NoSuchElementException.class, () -> wordsStack.peek());;
    }
}
