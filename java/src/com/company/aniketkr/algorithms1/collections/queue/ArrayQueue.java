package com.company.aniketkr.algorithms1.collections.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;


/**
 * Implements the {@link Queue} interface using an internal resizing array. The
 * internal array starts off with initial capacity {@value INIT_CAPACITY} if nothing
 * is explicitly specified. This array is guaranteed to be between 25% to 100% full at
 * all times. The size of the array increases (or decreases) by a factor of 2.
 *
 * <p>
 * The <em>enqueue</em> and <em>dequeue</em> operations take constant amortized time;
 * they take <code>&theta;(n)</code> time in worst case. The <em>peek</em> operation takes
 * constant time. The <em>contains</em> and <em>equals</em> operations take time proportional
 * to <code>&theta;(n)</code>. The operations to <em>copy</em> and <em>deepcopy</em> take
 * <code>&theta;(n)</code> time, although <em>copy</em> is slightly faster than
 * <em>deepcopy</em>. All other operations take constant time.
 * </p>
 *
 * @param <E> The type of element in the queue.
 * @author Aniket Kumar
 */
public final class ArrayQueue<E> implements Queue<E> {
  private static final int INIT_CAPACITY = 8;  // default initial capacity
  private E[] arr;                             // array that has the queue
  private int head = 0;                        // start or front of the queue
  private int tail = 0;                        // end or back of the queue

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
   * @throws IllegalArgumentException If {@code capacity} is less than or equal to 0.
   */
  @SuppressWarnings("unchecked")
  public ArrayQueue(int capacity) {
    if (capacity <= 0) throw new IllegalArgumentException("invalid capacity: " + capacity);
    arr = (E[]) new Object[capacity];
  }

  /**
   * Increase the value of {@link #head} by 1 such that the index does not overshoot
   * <code>{@link #arr}.length</code>. It instead will wrap around the array.
   */
  private void incHead() {
    // assume that arr always has sufficient space
    head = (head + 1) % arr.length;
  }

  /**
   * Increase the value of {@link #tail} by 1 such that the index does not overshoot
   * <code>{@link #arr}.length</code>. It instead will wrap around the array.
   */
  private void incTail() {
    // assume that arr always has sufficient space
    tail = (tail + 1) % arr.length;
  }

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * {@inheritDoc}
   *
   * @param obj The other Object to compare {@code this} with for equality.
   * @return {@code true} if {@code obj} is equal to this queue,
   *     {@code false} otherwise.
   */
  @Override
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
   * Get a string representation of the queue.
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
   * Get an iterator that produces the elements of the queue in <em>FIFO</em> order.
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
   * @return The count of elements present in the queue.
   */
  @Override
  public int size() {
    /*
      [PART-1] is the regular calculation of size() when the queue does not wrap.

      [PART-2] is when the queue wraps and `head` ends up before `tail`. This is when it
      becomes ABSOLUTELY CRITICAL that the value of `tail` index is subtracted from length
      of array BEFORE adding the `head` index. This ensures that no unforeseen INTEGER
      OVERFLOWS happen.
     */
    return head >= tail ? head - tail :   // [PART-1]
        (arr.length - tail) + head;       // [PART-2]
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
   * @param elmt The element to check existence of.
   * @return {@code true} if {@code elmt} exists, {@code false} otherwise.
   *
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
   * Clear the queue of all its elements.
   * Sets all internal state members to their <em>default</em> initial state.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void clear() {
    arr = (E[]) new Object[INIT_CAPACITY];
    head = 0;
    tail = 0;
  }

  /**
   * Return a shallow copy of the queue.
   * A shallow copy creates a copy of the queue but not the elements in the
   * queue.
   *
   * @return A shallow copy of the queue.
   *
   * @see #deepcopy(Function copyFn)
   */
  @Override
  public Queue<E> copy() {
    ArrayQueue<E> cp = new ArrayQueue<>(this.size() * 2);
    copyOver(cp.arr);
    cp.head = size();
    cp.tail = 0;

    return cp;
  }

  /**
   * Returns a deepcopy of the queue.
   * A deepcopy creates a copy of the queue and populates it with copies of the
   * original elements.
   *
   * @param copyFn A {@link Function} that takes original element as the
   *               argument and returns a deepcopy of that element.
   * @return A deepcopy of the queue.
   *
   * @throws IllegalArgumentException If {@code copyFn} is {@code null} or it returns
   *                                  any {@code null} values.
   * @see #copy()
   */
  @Override
  public Queue<E> deepcopy(Function<? super E, E> copyFn) {
    if (copyFn == null) throw new IllegalArgumentException("argument to deepcopy() is null");
    copyFn = copyFn.andThen(elmt -> {
      if (elmt == null) throw new IllegalArgumentException("copyFn mustn't return null values");
      return elmt;
    });

    ArrayQueue<E> cp = new ArrayQueue<>(this.size() * 2);
    for (E elmt : this) {
      cp.enqueue(copyFn.apply(elmt));
    }
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
   *
   * @throws NoSuchElementException If queue is empty; <em>underflow</em>.
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
   *
   * @throws NoSuchElementException If queue is empty; <em>underflow</em>.
   */
  @Override
  public E peek() {
    if (isEmpty()) throw new NoSuchElementException("underflow: can't peek() at empty queue");

    return arr[tail];
  }

  /* **************************************************************************
   * Section: Helper Methods and Classes
   ************************************************************************** */

  /**
   * Resize the internal array to {@code newSize}.
   * Resizing is achieved by creating a new array of {@code newSize} and copying over elements.
   *
   * @param newSize The new desired capacity of the new internal array.
   */
  @SuppressWarnings("unchecked")
  private void resize(int newSize) {
    E[] newArr = (E[]) new Object[newSize];
    copyOver(newArr);

    head = size();  // head MUST be computed before tail
    tail = 0;
    arr = newArr;
  }

  /**
   * Safely copy over <em>references</em> of the elements from the internal array to the
   * given array. The wrap state of the queue is handled internally.
   *
   * @param toArr The array to copy over elements to.
   */
  private void copyOver(Object[] toArr) {
    if (head > tail) {
      System.arraycopy(arr, tail, toArr, 0, size());
    } else {
      System.arraycopy(arr, tail, toArr, 0, arr.length - tail);
      System.arraycopy(arr, 0, toArr, arr.length - tail, head);
    }
  }

  /**
   * An {@link Iterator} class that supports iteration over "wrapped" queues.
   */
  private class WrapArrayIterator implements Iterator<E> {
    private final int stop;     // last element of the queue lies *before* this index
    private boolean isWrapped;  // is the queue wrapped (head lies before tail)
    private int current;        // start iterating from this index

    /**
     * Initialize and return a new iterator that starts iterating from the {@code head}
     * and stops at {@code tail}. The wrapped state is handled internally.
     *
     * @param head The position of head in the array.
     * @param tail The position of tail in the array.
     */
    private WrapArrayIterator(int head, int tail) {
      isWrapped = head < tail;
      current = tail;
      stop = head;
    }

    /**
     * Can the iterator produce another value?
     *
     * @return {@code false} if the iterator has been depleted, {@code true} otherwise.
     */
    @Override
    public boolean hasNext() {
      if (isWrapped && current == arr.length) {
        current = 0;
        isWrapped = false;
      }

      return isWrapped || current < stop;
    }

    /**
     * Produce the next value from the iterator and return it.
     *
     * @return The next value.
     *
     * @throws NoSuchElementException If called on a depleted iterator.
     */
    @Override
    public E next() {
      if (!hasNext()) throw new NoSuchElementException("iterator depleted");

      return arr[current++];
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
