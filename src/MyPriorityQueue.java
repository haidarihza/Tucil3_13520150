import java.util.*;

public class MyPriorityQueue {
    private ArrayList<Matrix> queue;
    private int size;

    public MyPriorityQueue() {
        this.queue = new ArrayList<Matrix>();
        this.size = 0;
    }

    public void enqueue(Matrix mat) {
        if (this.size == 0) {
            queue.add(mat);
            this.size++;
        }
        else{
            queue.add(mat);
            int i = this.size;
            while ((i > 0) && (queue.get(i).cost() < queue.get(i-1).cost())) {
                Collections.swap(queue, i, i-1);
                i--;
            }
            this.size++;
        }
    }

    public Matrix dequeue() {
        if (this.size == 0) {
            return null;
        }
        else {
            Matrix mat = queue.get(0);
            queue.remove(0);
            this.size--;
            return mat;
        }
    }

    public void clear() {
        queue.clear();
        this.size = 0;
    }

    public void displayMatrix() {
        for (int i = 0; i < this.size; i++) {
            queue.get(i).printMatrix();
        }
    }

    public boolean isEmpty() {
        return (this.size == 0);
    }
}
