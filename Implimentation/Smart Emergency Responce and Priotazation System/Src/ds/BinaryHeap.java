package ds;

/**
 * Binary Heap implementation - can be Min or Max heap
 * Used for Priority Queue in Emergency system
 *
 * Time Complexity:
 *   insert: O(log n)
 *   extractMax/Min: O(log n)
 *   peek: O(1)
 */
public class BinaryHeap<T extends Comparable<T>> {
    private T[] heap;
    private int size;
    private int capacity;
    private boolean isMaxHeap;

    @SuppressWarnings("unchecked")
    public BinaryHeap(int capacity, boolean isMaxHeap) {
        this.capacity = capacity;
        this.isMaxHeap = isMaxHeap;
        this.heap = (T[]) new Comparable[capacity];
        this.size = 0;
    }

    private int parent(int i) { return (i - 1) / 2; }
    private int leftChild(int i) { return 2 * i + 1; }
    private int rightChild(int i) { return 2 * i + 2; }

    private boolean compare(int i, int j) {
        if (isMaxHeap) {
            return heap[i].compareTo(heap[j]) > 0;
        } else {
            return heap[i].compareTo(heap[j]) < 0;
        }
    }

    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void heapifyUp(int i) {
        while (i > 0 && compare(i, parent(i))) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private void heapifyDown(int i) {
        int extreme = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < size && compare(left, extreme)) {
            extreme = left;
        }
        if (right < size && compare(right, extreme)) {
            extreme = right;
        }

        if (extreme != i) {
            swap(i, extreme);
            heapifyDown(extreme);
        }
    }

    public void insert(T item) {
        if (size == capacity) {
            // Resize array - double capacity
            @SuppressWarnings("unchecked")
            T[] newHeap = (T[]) new Comparable[capacity * 2];
            System.arraycopy(heap, 0, newHeap, 0, capacity);
            heap = newHeap;
            capacity *= 2;
        }
        heap[size] = item;
        size++;
        heapifyUp(size - 1);
    }

    public T extractExtreme() {
        if (size == 0) return null;
        T root = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        if (size > 0) heapifyDown(0);
        return root;
    }

    public T peek() {
        if (size == 0) return null;
        return heap[0];
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    // For debugging/display
    public void printHeap() {
        System.out.print("Heap: ");
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }
}