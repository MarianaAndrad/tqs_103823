package stack;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class BoundedStack<T> implements IStack{

    private LinkedList<T> collection;
    private int capacity = 0;

    public BoundedStack(int capacity) {
        this.collection = new LinkedList<>();
        this.capacity = capacity;
    }

    @Override
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    @Override
    public Object pop() {
        if (isEmpty()){
            throw new NoSuchElementException();
        }
        return collection.pop();
    }

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public Object peek() {
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        return collection.peek();
    }

    @Override
    public void push(Object element) {
        if (isFull()) {
            throw new IllegalStateException("Cannot add to full stack");
        }
        collection.push((T) element);
    }

    public Boolean isFull(){
        return collection.size() == this.capacity;
    }
}
