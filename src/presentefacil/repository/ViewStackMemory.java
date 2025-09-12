package repository;

import java.util.List;
import java.util.Stack;

import view.View;

public class ViewStackMemory {
    private static Stack<View> inMemoryViewStack = new Stack<>();

    public static View pop() {
        return inMemoryViewStack.pop();
    }
    public static void push(View view) {
        inMemoryViewStack.push(view);
    }
    public static void reset() {
        inMemoryViewStack.clear();
    }

    public static List<View> toList() {
        return inMemoryViewStack.subList(0, inMemoryViewStack.size());
    }
}
