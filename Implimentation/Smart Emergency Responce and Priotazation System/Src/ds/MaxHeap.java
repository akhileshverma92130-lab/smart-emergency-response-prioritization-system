package ds;

import Model.Emergency;

/**
 * MaxHeap wrapper for Emergency Priority Queue
 * Higher severity (lower number) = higher priority
 * So we use MinHeap internally but expose as "priority queue"
 */
public class MaxHeap {
    private BinaryHeap<Emergency> heap;

    public MaxHeap(int capacity) {
        // Emergency.compareTo already handles priority (1=highest)
        // So we use MinHeap behavior
        this.heap = new BinaryHeap<>(capacity, false); // false = min heap
    }

    public void insert(Emergency e) {
        heap.insert(e);
    }

    public Emergency extractHighestPriority() {
        return heap.extractExtreme();
    }

    public Emergency peek() {
        return heap.peek();
    }

    public int size() { return heap.size(); }
    public boolean isEmpty() { return heap.isEmpty(); }

    public void printQueue() {
        System.out.println("=== Emergency Queue (Priority Order) ===");
        // Note: This destroys the heap! In real code, we'd copy first
        // But for demo/debug it's okay
        MaxHeap temp = new MaxHeap(this.size());
        while (!this.isEmpty()) {
            Emergency e = this.extractHighestPriority();
            System.out.println(e);
            temp.insert(e);
        }
        // Restore
        while (!temp.isEmpty()) {
            this.insert(temp.extractHighestPriority());
        }
    }
}