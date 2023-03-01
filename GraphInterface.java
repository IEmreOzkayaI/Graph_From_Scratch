package Graph;

import java.util.Stack;

public interface GraphInterface<T> {
    int getBiggestCapacity(T begin, T end) ;
    void findIncreasebleVertex(T end);
    void findMaximizeAmount(Stack<T> stack);
}
