package stack;

public interface IStack<T> {
    boolean isEmpty();
    T pop();
    int size();
    T peek();
    void push(T element);
}
