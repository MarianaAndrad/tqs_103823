package stack;


import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> implements IStack{

    private LinkedList<T> collection;

    public TqsStack() {
        this.collection = new LinkedList<>();
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
        collection.push((T) element);
    }

}

