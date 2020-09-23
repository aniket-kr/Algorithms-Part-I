package com.company.aniketkr.algorithms1.collections.priorityqueue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

// TODO: code under MAJOR construction


// TODO: add a javadoc here
public class HeapPQ<K> implements PQ<K> {
  private static final int INIT_CAPACITY = 8;   // default capacity of PQ

  private final Comparator<K> comp;             // comparator to use, optional
  private K[] arr;                              // array that holds the PQ
  private int length = 0;                       // number of keys in the PQ

  // TODO: document this
  public HeapPQ() {
    this(INIT_CAPACITY, null);
  }

  // TODO: document this
  public HeapPQ(int capacity) {
    this(capacity, null);
  }

  // TODO: document this
  public HeapPQ(Comparator<K> comparator) {
    this(INIT_CAPACITY, comparator);
  }

  // TODO: document this
  @SuppressWarnings("unchecked")
  public HeapPQ(int capacity, Comparator<K> comparator) {
    if (capacity <= 0) throw new IllegalArgumentException("illegal capacity: " + capacity);

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
    // TODO: reformat code in this method
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof PQ)) return false;
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
    return null;      // TODO: incomplete method
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
    return null;    // TODO: incomplete method
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
    return null;    // TODO: incomplete method
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
   */
  @Override
  public void insert(K key) {
    // TODO: add throws "IllegalStateException" to docs (through less -> swim)
    if (key == null) throw new IllegalArgumentException("argument to insert() is null");
    if (size() == arr.length) {
      resize(arr.length * 2);
    }

    swim(++length);
  }

  /**
   * Remove and return the key that has the least priority according to the specific
   * ordering being used.
   *
   * @return The removed key with least priority.
   *
   * @throws NoSuchElementException If the priority queue is empty;
   *                                <em>underflow</em>.
   */
  @Override
  public K poll() {
    // TODO: add throws "IllegalStateException" to docs (through less -> sink)
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

  // TODO: implement `swap`, `swim`, `sink` and `resize`.
}
