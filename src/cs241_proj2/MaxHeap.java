/**
 * 
 */
package cs241_proj2;

/**
 * @author bjhau
 *
 */
public class MaxHeap<T extends Comparable<? super T>> implements MaxHeapInterface<T> {
	private static final int DEFAULT_CAPACITY = 25;
	private static final int MAX_CAPACITY = 10000;
	private T[] heap;
	private int lastIndex;
	private boolean initialized = false;
	private int swap = 0;

	public MaxHeap() {
		this(DEFAULT_CAPACITY);
	}

	public MaxHeap(int initialCapacity) {
		if (initialCapacity < DEFAULT_CAPACITY)
			initialCapacity = DEFAULT_CAPACITY;
		else
			checkCapacity(initialCapacity);
		@SuppressWarnings("unchecked")
		T[] tempHeap = (T[]) new Comparable[initialCapacity + 1];
		heap = tempHeap;
		lastIndex = 0;
		initialized = true;
	}

	public MaxHeap(T[] entries) {
		this(entries.length);
		lastIndex = entries.length;
		assert initialized = true;
		swap = 0;

		for (int index = 0; index < entries.length; index++) {
			heap[index + 1] = entries[index];
		}
		for (int rootIndex = lastIndex / 2; rootIndex > 0; rootIndex--)
			reheap(rootIndex);
	}

	public void add(T newEntry) {
		checkInitialization();
		ensureCapacity();
		swap=0;
		int newIndex = lastIndex + 1;
		int parentIndex = newIndex / 2;
		while ((parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) > 0) {
			heap[newIndex] = heap[parentIndex];
			newIndex = parentIndex;
			parentIndex = newIndex / 2;
			swap++;
		}

		heap[newIndex] = newEntry;
		lastIndex++;
	}

	private void checkCapacity(int initialCapacity) {
		if (initialCapacity > MAX_CAPACITY)
			throw new ArrayIndexOutOfBoundsException();
	}

	private void checkInitialization() {
		if (!initialized) {
			throw new SecurityException("HEAP WAS NEVER INITIALIZED!");
		}

	}

	public void clear() {
		checkInitialization();
		while (lastIndex > -1) {
			heap[lastIndex] = null;
			lastIndex--;
		}
		lastIndex = 0;
	}

	@SuppressWarnings("unchecked")
	private void ensureCapacity() {
		if (lastIndex == heap.length - 1) {
			T[] tempHeap = (T[]) new Comparable[heap.length * 2];
			for (int i =0; i < heap.length; i ++)
			{
				tempHeap[i] = heap[i];
			}
			heap = tempHeap;
		}
	}

	public T getData (int index)
	{
		return heap[index];
	}

	public T getMax() {
		checkInitialization();
		T root = null;
		if (!isEmpty())
			root = heap[1];
		return root;
	}

	public int getSize() {
		return lastIndex;
	}

	/**
	 * @return the swap
	 */
	public int getSwap() {
		return swap;
	}

	public boolean isEmpty() {
		return (lastIndex < 1);
	}

	private void reheap(int rootIndex) {
		boolean done = false;
		T orphan = heap[rootIndex];
		int leftChildIndex = 2 * rootIndex;

		while (!done && (leftChildIndex <= lastIndex)) {
			int largerChildIndex = leftChildIndex;
			int rightChildIndex = leftChildIndex + 1;
			if ((rightChildIndex <= lastIndex) && heap[rightChildIndex].compareTo(heap[largerChildIndex]) > 0) {
				largerChildIndex = rightChildIndex;
			}
			if (orphan.compareTo(heap[largerChildIndex]) < 0) {
				heap[rootIndex] = heap[largerChildIndex];
				rootIndex = largerChildIndex;
				leftChildIndex = 2 * rootIndex;
				swap++;
			} else
				done = true;
		}
		heap[rootIndex] = orphan;
	}

	public T removeMax() {
		checkInitialization();
		T root = null;
		swap =0;
		if (!isEmpty()) {
			root = heap[1];
			heap[1] = heap[lastIndex];
			lastIndex--;
			reheap(1);
		}
		return root;
	}
}
