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

	/**
	 * Checks the initial capacity of the max heap to make sure it is less than Max Capacity.
	 * @param initialCapacity	Initial desired capacity
	 * @throws ArrayIndexOutOfBoundsException	if the desired size exceeds the max.
	 */
	private void checkCapacity(int initialCapacity) {
		if (initialCapacity > MAX_CAPACITY)
			throw new ArrayIndexOutOfBoundsException();
	}

	/**
	 * Checks to ensure the maxHeap was initialized.
	 */
	private void checkInitialization() {
		if (!initialized) {
			throw new SecurityException("HEAP WAS NEVER INITIALIZED!");
		}

	}

	/**
	 * Clears the max heap.
	 */
	public void clear() {
		checkInitialization();
		while (lastIndex > -1) {
			heap[lastIndex] = null;
			lastIndex--;
		}
		lastIndex = 0;
	}

	/**
	 * Checks if there is room left in the max heap for another input, if not, it doubles the size of the max heap.
	 */
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

	/**
	 * Gives the data in the heap at the index provided
	 * @param index	Location of data requested
	 * @return	Data in the location requested
	 */
	public T getData (int index)
	{
		return heap[index];
	}

	/**
	 * Returns the root of the maxheap
	 */
	public T getMax() {
		checkInitialization();
		T root = null;
		if (!isEmpty())
			root = heap[1];
		return root;
	}

	/**
	 * Returns the size of the current MaxHeap
	 * @return	lastIndex
	 */
	public int getSize() {
		return lastIndex;
	}

	/**
	 * Returns how many swaps were performed
	 * @return the swap
	 */
	public int getSwap() {
		return swap;
	}

	/**
	 * Checks if the heap is empty.
	 */
	public boolean isEmpty() {
		return (lastIndex < 1);
	}

	/**
	 * Reheaps the Heap after a removal to maintain a complete tree
	 * @param rootIndex	The index of the root of the tree.
	 */
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

	/**
	 * Removes the max in the Heap
	 */
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
