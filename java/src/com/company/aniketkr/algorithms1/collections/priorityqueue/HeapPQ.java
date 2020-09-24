package com.company.aniketkr.algorithms1.collections.priorityqueue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;


/**
 * Implements the {@link PQ} interface using resizing array based binary heaps.
 * If the capacity is not defined explicitly, the initial array starts off with the
 * default capacity of {@value INIT_CAPACITY}. The size of the array increases (or
 * decreases) by a factor of 2.
 *
 * <p>
 * The <em>insert</em> and <em>poll</em> operations take <code>&theta;(nlog<sub>2</sub>n)
 * </code> amortized time (not worst case time). The <em>peek</em> operation takes constant
 * time. The <em>contains</em>, <em>equals</em>, <em>copy</em> and <em>deepcopy</em>
 * operations all take <code>&theta;(n)</code> time. Note that <em>copy</em> is faster than
 * <em>deepcopy</em> operation. All other operations take constant time.
 * </p>
 *
 * <p>
 * The <em>iterator</em> method takes <code>O(n)</code> extra space. The iterator produces
 * keys in the order of the specific priority being used.
 * </p>
 *
 * @param <K> The type of key in the priority queue.
 * @author Aniket Kumar
 */
public class HeapPQ<K> implements PQ<K> {
  private static final int INIT_CAPACITY = 8;   // default capacity of PQ

  private final Comparator<K> comp;             // comparator to use, optional
  private K[] arr;                              // array that holds the PQ
  private int length = 0;                       // number of keys in the PQ

  /**
   * Initialize and return an empty HeapPQ object.
   * The default capacity is {@value INIT_CAPACITY}. Also, it will be assumed that
   * {@link K Key} type implements {@link Comparable} interface.
   */
  public HeapPQ() {
    this(INIT_CAPACITY, null);
  }

  /**
   * Initialize and return an empty HeapPQ object that has capacity to hold
   * {@code capacity} number of keys.
   * It will be assumed that {@link K Key} type implements {@link Comparable} interface.
   *
   * @param capacity The number of keys the priority queue should be able to hold without
   *                 having to resize.
   * @throws IllegalArgumentException If {@code capacity} is less than or equal to 0.
   */
  public HeapPQ(int capacity) {
    this(capacity, null);
  }

  /**
   * Initialize and return an empty HeapPQ object which uses {@code comparator} to compare
   * keys.
   * The default capacity of the priority queue, {@value INIT_CAPACITY}, will be used.
   *
   * @param comparator The {@link Comparator} object that will compare two {@link K Key}
   *                   objects. If {@code comparator} is {@code null}, an attempt to use
   *                   the {@link Comparable} interface will be made.
   */
  public HeapPQ(Comparator<K> comparator) {
    this(INIT_CAPACITY, comparator);
  }

  /**
   * Initialize and return an empty HeapPQ object, that has capacity to hold {@code capacity}
   * number of keys and uses {@code comparator} to compare the keys.
   *
   * @param capacity   The number of keys the priority queue should be able to hold without
   *                   having to resize.
   * @param comparator The {@link Comparator} object that will compare two {@link K Key}
   *                   objects. If {@code comparator} is {@code null}, an attempt to use
   *                   the {@link Comparable} interface will be made.
   * @throws IllegalArgumentException If {@code capacity} is less than or equal to 0.
   */
  @SuppressWarnings("unchecked")
  public HeapPQ(int capacity, Comparator<K> comparator) {
    if (capacity <= 0) throw new IllegalArgumentException("invalid capacity: " + capacity);

    comp = comparator;
    arr = (K[]) new Object[capacity];
  }

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * {@inheritDoc}
   *
   * @param obj The other Object to compare {@code this} with for equality.
   * @return {@code true} if {@code obj} is equal to this priority queue,
   *     {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)                return true;
    if (obj == null)                return false;
    if (!(obj instanceof PQ))       return false;
    PQ<?> that = (PQ<?>) obj;
    if (this.size() != that.size()) return false;

    // compare all elements
    Iterator<?> itor = that.iterator();
    for (K key : this) {
      if (!key.equals(itor.next())) {
        return false;
      }
    }
    return true;
  }

  /**
   * Get a string representation of the priority queue.
   * Primarily for debugging and helping find bugs in client code.
   *
   * @return A string.
   */
  @Override
  @SuppressWarnings("DuplicatedCode")
  public String toString() {
    String className = this.getClass().getSimpleName();

    if (isEmpty()) return className + "[0] [ ]";

    StringBuilder sb = new StringBuilder(className).append("[").append(size()).append("] [ ");
    for (K key : this) {
      sb.append(key).append(", ");
    }
    sb.setLength(sb.length() - 2);
    return sb.append(" ]").toString();
  }

  /* **************************************************************************
   * Section: Iterable Methods
   ************************************************************************** */

  /**
   * Get an iterator that produces the keys of the priority queue in the increasing order
   * of their priority, based on the specific ordering being used.
   *
   * @return An iterator.
   */
  @Override
  public Iterator<K> iterator() {
    /* NOTE:
         Using a shallow copy is MUST as it passes the same "reference" to the
         client when iterating and therefore any attempt to mutate will actually
         be successful. Sometimes, fields that do not determine the priority
         may need to be mutated.

         Also, making a shallow copy is significantly cheaper than making deep
         copies for larger priority queues.
     */
    return new HeapIterator(this.copy());
  }

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * How many keys are present in the priority queue?
   *
   * @return The count of keys present in the priority queue.
   */
  @Override
  public int size() {
    return length;
  }

  /**
   * Is the priority queue empty?
   *
   * @return {@code true} if priority queue is empty, {@code false} otherwise.
   */
  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Does {@code key} exist in the priority queue?
   *
   * @param key The key to check existence of.
   * @return {@code true} if {@code key} exists, {@code false} otherwise.
   *
   * @throws IllegalArgumentException If {@code key} is {@code null}.
   */
  @Override
  public boolean contains(K key) {
    if (key == null) throw new IllegalArgumentException("argument to contains() is null");

    // Don't use this.iterator() as array iteration is more efficient
    for (int i = 1; i <= length; i++) {
      if (key.equals(arr[i])) {
        return true;
      }
    }
    return true;
  }

  /**
   * Clear the priority queue of all its keys.
   * Sets all internal state members to their <em>default</em> initial state.
   *
   * <p>
   * Note that this does <b>NOT</b> change the method of comparison, i.e. if
   * the instance used a {@code comparator} before, it will still use the same
   * {@code comparator}. Similarly, natural order will be used if no {@code comparator}
   * was provided during instantiation.
   * </p>
   */
  @Override
  @SuppressWarnings("unchecked")
  public void clear() {
    arr = (K[]) new Object[(size() >= 2) ? (size() * 2) : INIT_CAPACITY];
    length = 0;
  }

  /**
   * Return a shallow copy of the priority queue.
   * A shallow copy creates a copy of the priority queue but not the keys in the
   * priority queue.
   *
   * @return A shallow copy of the priority queue.
   *
   * @see #deepcopy(Function copyFn)
   */
  @Override
  public PQ<K> copy() {
    /* NOTE:
         Don't use the `this.iterator()` method as that will lead to cyclic reference -
         the `iterator()` method passes a shallow copy (using `copy()`) to the constructor
         of the HeapIterator class.
     */
    HeapPQ<K> cp = new HeapPQ<>((size() >= 2) ? (size() * 2) : INIT_CAPACITY, comp);
    System.arraycopy(this.arr, 1, cp.arr, 1, this.size());
    cp.length = this.length;

    return cp;
  }

  /**
   * Returns a deepcopy of the priority queue.
   * A deepcopy creates a copy of the priority queue and populates it with copies of the
   * original keys.
   *
   * @param copyFn A {@link Function} that takes original key as the
   *               argument and returns a deepcopy of that key.
   * @return A deepcopy of the priority queue.
   *
   * @throws IllegalArgumentException If {@code copyFn} or any value returned by
   *                                  it is {@code null}.
   * @see #copy()
   */
  @Override
  public PQ<K> deepcopy(Function<? super K, K> copyFn) {
    if (copyFn == null) throw new IllegalArgumentException("argument to deepcopy() is null");

    // add `null check` to `copyFn`
    copyFn = copyFn.andThen(key -> {
      if (key == null) throw new IllegalArgumentException("copyFn returned null");
      return key;
    });

    HeapPQ<K> cp = new HeapPQ<>((size() >= 2) ? (size() * 2) : INIT_CAPACITY, comp);

    for (int i = 1; i <= size(); i++) {
      cp.arr[i] = copyFn.apply(this.arr[i]);
    }
    cp.length = this.length;

    return cp;
  }

  /* **************************************************************************
   * Section: Priority Queue Methods
   ************************************************************************** */

  /**
   * Get the {@link Comparator} object being used by the priority queue, or
   * {@code null} if the natural ordering is being used.
   *
   * @return The comparator being used to order, or {@code null} if natural ordering
   *     is being used.
   */
  @Override
  public Comparator<K> comparator() {
    return comp;
  }

  /**
   * Insert {@code key} into the priority queue.
   *
   * @param key The key to be inserted.
   * @throws IllegalArgumentException If {@code key} is {@code null}.
   * @throws IllegalStateException    If and only if the {@link K Key} implements neither
   *                                  {@link Comparable} interface nor was any {@link Comparator}
   *                                  object provided during construction.
   * @see HeapPQ HeapPQ for more info on IllegalStateException.
   */
  @Override
  public void insert(K key) {
    if (key == null) throw new IllegalArgumentException("argument to insert() is null");
    if (size() == arr.length) {
      resize(arr.length * 2);
    }

    arr[++length] = key;
    swim(length);
  }

  /**
   * Remove and return the key that has the least priority according to the specific
   * ordering being used.
   *
   * @return The removed key with least priority.
   *
   * @throws NoSuchElementException If the priority queue is empty; <em>underflow</em>.
   * @throws IllegalStateException  If and only if the {@link K Key} implements neither
   *                                {@link Comparable} interface nor was any {@link Comparator}
   *                                object provided during construction.
   * @see HeapPQ HeapPQ for more info on IllegalStateException.
   */
  @Override
  public K poll() {
    if (isEmpty()) throw new NoSuchElementException("underflow: can't poll() from empty PQ");
    if (size() == arr.length / 4) {
      resize(arr.length / 2);
    }

    K key = arr[1];
    swap(1, length--);
    sink(1);

    return key;
  }

  /**
   * Return the key that has the least priority according to the specific ordering
   * being used, without removing it.
   *
   * @return The key that has least priority in the queue.
   */
  @Override
  public K peek() {
    if (isEmpty()) throw new NoSuchElementException("underflow: can't peek() at empty PQ");

    return arr[1];
  }

  /* **************************************************************************
   * Section: Helper Classes and Methods
   ************************************************************************** */

  /**
   * Resize the internal array to have a capacity to hold {@code newSize} keys.
   * Resizing is achieved by copying over all the keys from original array to the newly
   * created internal array.
   *
   * @param newSize The size of the new array to be created.
   */
  @SuppressWarnings("unchecked")
  private void resize(int newSize) {
    K[] newArr = (K[]) new Object[newSize];
    System.arraycopy(arr, 1, newArr, 1, size());
    arr = newArr;
  }

  /**
   * Swap the key at index {@code i} with that at index {@code j}. <b>DOES NOT</b> verify
   * if the array indices are valid.
   *
   * @param i Index of key to swapped with key at index {@code j}.
   * @param j Index of key to swapped with key at index {@code i}.
   */
  private void swap(int i, int j) {
    K tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }

  /**
   * Compare the key at index {@code i} with that at index {@code j}. The comparison is done
   * using whatever means was specified during construction.
   *
   * @param i Index of key to compare with key at index {@code j}
   * @param j Index of key to compare with key at index {@code i}
   * @return negative (eg. {@code -1}) when {@code arr[i]}  is less than {@code arr[j]}, zero
   *     ({@code 0}) when equal and positive (eg. {@code +1}) when greater.
   *
   * @throws IllegalStateException If {@link K Key} type does not implement {@link Comparable},
   *                               and neither was any {@link Comparator} provided.
   * @see HeapPQ HeapPQ docs for more details on IllegalStateExeception.
   */
  @SuppressWarnings("unchecked")
  private int compare(int i, int j) {
    if (comp == null) {
      try {
        return ((Comparable<K>) arr[i]).compareTo(arr[j]);
      } catch (ClassCastException exc) {
        throw new IllegalStateException("key doesn't implement Comparable, nor was a Comparator provided", exc);
      }
    }

    return comp.compare(arr[i], arr[j]);
  }

  /**
   * Move child at index {@code k} upwards until parent at index {@code k / 2} becomes
   * smaller than the child at {@code k}.
   * Helps maintain the binary heap tree invariant by "lifting" keys with less priority up
   * towards the root.
   *
   * @param k The index to start lifting up from. Tree below this index will not be seen.
   * @throws IllegalStateException Upward propagation due to client error -
   *                               (see {@link #compare(int i, int j) compare} method).
   */
  private void swim(int k) {
    while (k > 1) {
      if (compare(k / 2, k) < 0) break;    // compare() may throw `IllegalStateException`

      swap(k, k / 2);
      k /= 2;
    }
  }

  /**
   * Move parent at index {@code k} downwards until child at index {@code 2 * k} becomes
   * larger than the parent at {@code k}.
   * Helps maintain the binary heap tree invariant by "sinking" keys with more priority down
   * towards the leaves.
   *
   * @param k The index to start sinking down from. Tree above this index will not be seen.
   * @throws IllegalStateException Upward propagation due to client error -
   *                               (see {@link #compare(int i, int j) compare} method).
   */
  private void sink(int k) {
    int j;
    while (k * 2 <= length) {
      j = k * 2;
      if (j + 1 <= length && compare(j, j + 1) > 0) j++;
      if (compare(j, k) > 0) break;        // `compare()` may throw `IllegalStateException`

      swap(j, k);
      k = j;
    }
  }

  /**
   * An {@link Iterator} class that iterates over the values of a priority queue in the order
   * of their specified priority. Is very expensive in terms of space - uses
   * <code>&theta(n)</code> space where {@code n} is the number of keys in the priority queue.
   */
  private class HeapIterator implements Iterator<K> {
    private final PQ<K> pq;     // shallow copy to operate on

    /**
     * Initialize and return a HeapIterator object that irreversibly mutates the shallow
     * copy of priority queue to be iterated.
     *
     * @param pqCopy The shallow copy of priority queue that will get mutated.
     */
    private HeapIterator(PQ<K> pqCopy) {
      pq = pqCopy;
    }

    /**
     * Does the iterator have any more values to produce?
     *
     * @return {@code false} if the iterator has been depleted, {@code true} otherwise.
     */
    @Override
    public boolean hasNext() {
      return !pq.isEmpty();
    }

    /**
     * Produce the next value from the iterator and return it.
     *
     * @return The next value.
     *
     * @throws NoSuchElementException If called on a depleted iterator.
     */
    @Override
    public K next() {
      if (!hasNext()) throw new NoSuchElementException("iterator depleted");

      return pq.poll();
    }

    /**
     * Remove not supported. Throws UOE.
     *
     * @throws UnsupportedOperationException Always.
     */
    @Override
    public void remove() {
      throw new UnsupportedOperationException("remove() not supported");
    }
  }
}
