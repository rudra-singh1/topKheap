import java.lang.reflect.Array;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class BinaryMaxHeap<T extends Comparable<T>> implements MyPriorityQueue<T> {
    private int size;
    private T[] arr;
    private Map<T, Integer> itemToIndex;

    public BinaryMaxHeap() {
        arr = (T[]) Array.newInstance(Comparable.class, 10);
        size = 0;
        itemToIndex = new HashMap<>();
    }

    private void percolateUp(int i) {
        while (i > 0 && arr[i].compareTo(arr[(i - 1) / 2]) > 0) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    private void percolateDown(int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;

        if (left < size && arr[left].compareTo(arr[largest]) > 0) {
            largest = left;
        }
        if (right < size && arr[right].compareTo(arr[largest]) > 0) {
            largest = right;
        }
        if (largest != i) {
            swap(i, largest);
            percolateDown(largest);
        }
    }

    private void resize() {
        T[] larger = (T[]) Array.newInstance(Comparable.class, arr.length * 2);
        System.arraycopy(arr, 0, larger, 0, arr.length);
        arr = larger;
    }

    private void swap(int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        itemToIndex.put(arr[i], i);
        itemToIndex.put(arr[j], j);
    }

    public void insert(T item) {
        if (size == arr.length) {
            resize();
        }
        arr[size] = item;
        itemToIndex.put(item, size);
        percolateUp(size);
        size++;
    }

    public T extract() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        T max = arr[0];
        arr[0] = arr[size - 1];
        itemToIndex.put(arr[0], 0);
        size--;
        percolateDown(0);
        itemToIndex.remove(max);
        return max;
    }

    public void remove(T item) {
        if (!itemToIndex.containsKey(item)) {
            throw new IllegalArgumentException("Item not in heap");
        }
        remove(itemToIndex.get(item));
    }

    private T remove(int index) {
        T removedItem = arr[index];
        arr[index] = arr[size - 1];
        itemToIndex.put(arr[index], index);
        size--;
        percolateDown(index);
        itemToIndex.remove(removedItem);
        return removedItem;
    }

    public void updatePriority(T item) {
        if (!itemToIndex.containsKey(item)) {
            throw new IllegalArgumentException("Item not found in the heap");
        }
        updatePriority(itemToIndex.get(item));
    }

    private void updatePriority(int index) {
        percolateUp(index);
        percolateDown(index);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public T peek() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        return arr[0];
    }

    public List<T> toList() {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(arr[i]);
        }
        return list;
    }
}
