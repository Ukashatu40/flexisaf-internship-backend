import java.util.ArrayList;
import java.util.List;

public class PowerOfTwoMaxHeap<T extends Comparable<T>> {

    private List<T> heap;
    private int numberOfChildrenPerParent;

    public PowerOfTwoMaxHeap(int powerOfTwo) {
        if (powerOfTwo < 0) {
            throw new IllegalArgumentException("The power of two must be non-negative.");
        }
        this.heap = new ArrayList<>();
        this.numberOfChildrenPerParent = (int) Math.pow(2, powerOfTwo);
        if (this.numberOfChildrenPerParent < 2) {
            this.numberOfChildrenPerParent = 2; // A minimum of 2 children for a valid heap
        }
    }

    public void insert(T item) {
        heap.add(item);
        siftUp(heap.size() - 1);
    }

    public T popMax() {
        if (heap.isEmpty()) {
            return null;
        }

        T max = heap.get(0);
        T lastItem = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, lastItem);
            siftDown(0);
        }

        return max;
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void siftUp(int index) {
        if (index == 0) return;
        int parentIndex = getParentIndex(index);
        while (index > 0 && heap.get(index).compareTo(heap.get(parentIndex)) > 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = getParentIndex(index);
        }
    }

    private void siftDown(int index) {
        int maxChildIndex = findLargestChildIndex(index);
        while (maxChildIndex != -1 && heap.get(index).compareTo(heap.get(maxChildIndex)) < 0) {
            swap(index, maxChildIndex);
            index = maxChildIndex;
            maxChildIndex = findLargestChildIndex(index);
        }
    }

    private int getParentIndex(int childIndex) {
        if (childIndex == 0) return -1;
        return (childIndex - 1) / numberOfChildrenPerParent;
    }

    private int getFirstChildIndex(int parentIndex) {
        return numberOfChildrenPerParent * parentIndex + 1;
    }

    private int findLargestChildIndex(int parentIndex) {
        int firstChildIndex = getFirstChildIndex(parentIndex);
        if (firstChildIndex >= heap.size()) {
            return -1;
        }

        int largestChildIndex = firstChildIndex;
        int lastChildIndex = Math.min(firstChildIndex + numberOfChildrenPerParent, heap.size());

        for (int i = firstChildIndex + 1; i < lastChildIndex; i++) {
            if (heap.get(i).compareTo(heap.get(largestChildIndex)) > 0) {
                largestChildIndex = i;
            }
        }
        return largestChildIndex;
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}