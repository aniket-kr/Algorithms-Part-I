package com.company.aniketkr.algorithms1.collections.queue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implements the {@link Queue} interface using an internal resizing array. The
 * internal array starts off with initial capacity {@value INIT_CAPACITY} if nothing
 * is explicitly specified.
 *
 * <p>This array is guaranteed to be between 25% to 100% full at all times. The size
 * of the array increases (or decreases) by a factor of 2.</p>
 *
 * @param <E> The type of elements in the queue.
 * @author Aniket Kumar
 */
public final class ArrayQueue<E> implements Queue<E> {
  private static final int INIT_CAPACITY = 8;
  private E[] arr;
  private int head = 0;
  private int tail = 0;

  /**
   * Initialize and return a new ArrayQueue object.
   * Default capacity is {@value INIT_CAPACITY}.
   */
  public ArrayQueue() {
    this(INIT_CAPACITY);
  }

  /**
   * Initialize and return a new ArrayQueue object, ensuring capacity for
   * {@code capacity} objects.
   *
   * @param capacity The number of elements that internal array should be able to
   *                 accommodate without needing to resize.
   * @throws NegativeArraySizeException If {@code capacity} is negative.
   */
  @SuppressWarnings("unchecked")
  public ArrayQueue(int capacity) {
    arr = (E[]) new Object[capacity];
  }

  private int head() {
    return (head < tail) ? arr.length + head : head;
  }

  private void incHead() {
    // assume that arr always has sufficient space
    head = (head + 1) % arr.length;
  }

  private int tail() {
    return tail;
  }

  private void incTail() {
    // assume that arr always has sufficient space
    tail = (tail + 1) % arr.length;
  }

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * Check if this queue is equal to the given object.
   * Calls {@link Object#equals(Object that) equals()} on the elements to check if they
   * are equal.
   *
   * @param obj The other object to check for equality.
   * @return {@code true} if {@code obj} is semantically equal to this queue,
   *     {@code false} otherwise.
   * @implSpec As it currently stands, if two queue objects are empty, the {@code equals}
   *     method will always return {@code true}. This happens as no elements are available
   *     to compare.
   */
  public boolean equals(Object obj) {
    if (this == obj)                return true;
    if (obj == null)                return false;
    if (!(obj instanceof Queue))    return false;
    Queue<?> that = (Queue<?>) obj;
    if (this.size() != that.size()) return false;

    // compare all elements
    Iterator<?> itor = that.iterator();
    for (E elmt : this) {
      if (!elmt.equals(itor.next())) {
        return false;
      }
    }
    return true;
  }

  /**
   * Return a string representation of the queue. Primarily for debugging.
   *
   * @return A string.
   */
  public String toString() {
    if (isEmpty()) return "ArrayQueue[0] [ ]";

    StringBuilder sb = new StringBuilder("ArrayQueue[").append(size()).append("] [ ");
    for (E elmt : this) {
      sb.append(elmt).append(", ");
    }
    sb.setLength(sb.length() - 2);
    return sb.append(" ]").toString();
  }

  /* **************************************************************************
   * Section: Iterable Methods
   ************************************************************************** */

  /**
   * Get an iterator object that produces the elements of the queue in its natural
   * order.
   *
   * @return An iterator.
   */
  @Override
  public Iterator<E> iterator() {
    return new WrapArrayIterator(head, tail);
  }

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * How many elements are present in the queue?
   *
   * @return The count of number of elements in the queue.
   */
  @Override
  public int size() {
    return head() - tail();
  }

  /**
   * Is the queue empty?
   *
   * @return {@code true} if queue is empty, {@code false} otherwise.
   */
  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Does {@code elmt} exist in the queue?
   *
   * @param elmt The element to check for.
   * @return {@code true} if {@code elmt} exists in the queue, {@code false}
   *     otherwise.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  @Override
  public boolean contains(E elmt) {
    if (elmt == null) throw new IllegalArgumentException("argument to contains() is null");

    for (E queueElmt : this) {
      if (queueElmt.equals(elmt)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Clear the queue of all its elements. Set it to its instantiated state.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void clear() {
    arr = (E[]) new Object[INIT_CAPACITY];
    head = 0;
    tail = 0;
  }

  /**
   * Get a shallow copy of the queue.
   *
   * @return A new queue.
   */
  @Override
  public Queue<E> copy() {
    ArrayQueue<E> cp = new ArrayQueue<>(size() * 2);
    copyOver(cp.arr);
    cp.head = size();
    cp.tail = 0;

    return cp;
  }

  /* **************************************************************************
   * Section: Queue Methods
   ************************************************************************** */

  /**
   * Add the given element {@code elmt} to the back of the queue.
   *
   * @param elmt The element to add to the queue.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  @Override
  public void enqueue(E elmt) {
    if (elmt == null) throw new IllegalArgumentException("argument to enqueue() is null");
    if (size() + 1 == arr.length) {
      resize(arr.length * 2);
    }

    arr[head] = elmt;
    incHead();
  }

  /**
   * Remove the first element from the front of the queue.
   *
   * @return The removed element.
   * @throws NoSuchElementException If queue is empty (underflow).
   */
  @Override
  public E dequeue() {
    if (isEmpty()) throw new NoSuchElementException("underflow: can't dequeue() from empty queue");
    if (size() == arr.length / 4) {
      resize(arr.length / 2);
    }

    E elmt = arr[tail];
    arr[tail] = null;
    incTail();

    return elmt;
  }

  /**
   * Return the first element from the front of the queue, without removing it.
   *
   * @return The first element of the queue.
   * @throws NoSuchElementException If queue is empty (underflow).
   */
  @Override
  public E peek() {
    if (isEmpty()) throw new NoSuchElementException("underflow: can't peek() at empty queue");

    return arr[tail];
  }

  /* **************************************************************************
   * Section: Helper Methods and Classes
   ************************************************************************** */

  @SuppressWarnings("unchecked")
  private void resize(int newSize) {
    E[] newArr = (E[]) new Object[newSize];
    copyOver(newArr);

    head = size();  // head MUST be computed before tail
    tail = 0;
    arr = newArr;
  }

  private void copyOver(Object[] toArr) {
    if (head > tail) {
      System.arraycopy(arr, tail, toArr, 0, size());
    } else {
      System.arraycopy(arr, tail, toArr, 0, arr.length - tail);
      System.arraycopy(arr, 0, toArr, arr.length - tail, head);
    }
  }

  private class WrapArrayIterator implements Iterator<E> {
    private boolean resetToStart;
    private int current;
    private final int stop;

    private WrapArrayIterator(int trueHead, int trueTail) {
      resetToStart = trueHead < trueTail;
      current = trueTail;
      stop = trueHead;
    }

    @Override
    public boolean hasNext() {
      if (resetToStart && current == arr.length) {
        current = 0;
        resetToStart = false;
      }

      return resetToStart || current < stop;
    }

    @Override
    public E next() {
      if (!hasNext()) throw new NoSuchElementException("iterator depleted");

      return arr[current++];
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException("remove() not supported");
    }
  }
}
