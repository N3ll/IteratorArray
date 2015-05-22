import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AList<T> implements IndexedListI<T> {
	private T[] list;
	private int numberOfentries;
	private static final int DEFAULT_CAPACITY = 25;

	public AList(int capacity) {
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Object[capacity];
		list = temp;
		numberOfentries = 0;
	}

	public AList() {
		this(DEFAULT_CAPACITY);
	}

	@Override
	public void add(T entry) {
		ensureCapacity();
		list[numberOfentries] = entry;
		numberOfentries++;

	}

	@Override
	public void add(int index, T entry) {
		if (index >= 0 && index <= size()) {
			ensureCapacity();
			for (int i = size(); i >= index; i--) {
				list[i] = list[i - 1];
				list[index] = entry;
				numberOfentries++;
			}
		} else {
			throw new IndexOutOfBoundsException();
		}

	}

	@Override
	public T remove(int index) {
		T result = null;
		if (isEmpty() || (index >= 0 && index <= size() - 1)) {
			result = list[index];
			if (index < numberOfentries) {
				for (int i = index; i < size() - 1; i++) {
					list[i] = list[i + 1];
				}
			}
			numberOfentries--;
		} else {
			throw new IndexOutOfBoundsException();
		}
		return result;
	}

	@Override
	public T replace(int index, T entry) {
		T result = null;
		if (isEmpty() || (index >= 0 && index <= size() - 1)) {
			result = list[index];
			list[index] = entry;
		} else {
			throw new IndexOutOfBoundsException();
		}
		return result;
	}

	@Override
	public T get(int index) {
		T result = null;
		if (isEmpty() || (index >= 0 && index <= size() - 1)) {
			result = list[index];
		} else {
			throw new IndexOutOfBoundsException();
		}
		return result;
	}

	@Override
	public boolean contains(T entry) {
		boolean found = false;
		for (int i = 0; !found && i < size(); i++) {
			found = entry.equals(list[i]);
		}
		return found;
	}

	@Override
	public int size() {
		return numberOfentries;
	}

	@Override
	public boolean isEmpty() {
		return numberOfentries == 0;
	}

	@Override
	public void clear() {
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Object[DEFAULT_CAPACITY];
		list = temp;
		numberOfentries = 0;

	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] result = (T[]) new Object[numberOfentries];
		for (int i = 0; i < numberOfentries; i++) {
			result[i] = list[i];
		}
		return result;
	}

	private void ensureCapacity() {
		if (numberOfentries == list.length) {
			list = Arrays.copyOf(list, 2 * list.length);
		}
	}

	@Override
	public Iterator<T> iterator() {

		return new IteratorArrayList(list);
	}

	private class IteratorArrayList implements Iterator<T> {

		private T[] arr;
		private int position = 0;

		public IteratorArrayList(T[] arr) {
			this.arr = arr;
		}

		@Override
		public boolean hasNext() {
			if (position == arr.length || arr[position] == null)
				return false;
			return true;
		}

		@Override
		public T next() {
			if (hasNext()) {
				T temp = arr[position];
				position++;
				return temp;
			} else {
				throw new NoSuchElementException(
						"Illegal call to next(); iterator is after end of list");
			}
		}
		
		public void remove() {
			throw new UnsupportedOperationException(
					"remove() is not supported by this operator");
		}

	}
}
