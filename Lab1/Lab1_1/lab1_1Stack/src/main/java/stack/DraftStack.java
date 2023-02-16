package stack;

import java.util.LinkedList;

public class DraftStack <T> implements IStack{
    private LinkedList<T> collection;

    public DraftStack(){
        this.collection = new LinkedList<>();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Object pop() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Object peek() {
        return null;
    }

    @Override
    public void push(Object element) {
    }
}
