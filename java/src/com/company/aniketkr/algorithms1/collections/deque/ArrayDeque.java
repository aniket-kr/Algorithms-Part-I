package com.company.aniketkr.algorithms1.collections.deque;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;


/**
 * Implements the {@link Deque} interface using an internal resizing array that
 * wraps the deque wraps around. If no explicit capacity is provided, the default
 * capacity of the internal array is {@value INIT_CAPACITY}. The size of the array
 * increases (or decreases) by a factor of 2.
 *
 * <p>
 * The deque supports <em>add</em> and <em>delete</em> operations from both ends in
 * <code>&theta;(nlog<sub>2</sub>n)</code> amortized time (not worst case time). The
 * <em>peek</em> operations on both ends take constant time. The <em>equals</em> and
 * <em>contains</em> operations take <code>&theta;(n)</code> time. The operations to
 * <em>copy</em> and <em>deepcopy</em> also take <code>&theta;(n)</code> time, although
 * <em>copy</em> is faster than <em>deepcopy</em>. Iteration (via
 * <em>iterator</em> and <em>reverse</em> methods) take constant extra space. All other
 * methods take constant time.
 * </p>
 *
 * @param <E> The type of element in the deque.
 * @author Aniket Kumar
 */
public final class ArrayDeque<E> implements Deque<E> {
  private static final int INIT_CAPACITY = 8;    // default capacity of deque

  private E[] arr;                               // holds the deque
  private int front = 0;                         // head/front of deque at this index
  private int back = 0;                          // tail/back of deque at this index

  /**
   * Initialize and return an empty ArrayDeque object.
   * The default capacity is {@value INIT_CAPACITY}.
   */
  public ArrayDeque() {
    this(INIT_CAPACITY);
  }

  /**
   * Initialize and return an empty ArrayDeque object, which has capacity to hold
   * {@code capacity} elements.
   *
   * @param capacity The desired number of elements the deque should be able to hold
   *                 before having to resize.
   * @throws IllegalArgumentException If {@code capacity} is less than or equal to 0.
   */
  @SuppressWarnings("unchecked")
  public ArrayDeque(int capacity) {
    if (capacity <= 0) throw new IllegalArgumentException("invalid capacity: " + capacity);

    arr = (E[]) new Object[capacity];
  }

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * {@inheritDoc}
   *
   * @param obj The other Object to compare {@code this} with for equality.
   * @return {@code true} if {@code obj} is equal to this deque,
   *     {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)                return true;
    if (obj == null)                return false;
    if (!(obj instanceof Deque))    return false;
    Deque<?> that = (Deque<?>) obj;
    if (this.size() != that.size()) return false;

    // compare all the elements
    Iterator<?> itor = that.iterator();
    for (E elmt : this) {
      if (!Objects.equals(elmt, itor.next())) {
        return false;
      }
    }
    return true;
  }

  /**
   * Get a string representation of the deque.
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
   * Get an iterator that produces the elements of the deque from the front
   * to the back.
   *
   * @return An iterator.
   *
   * @see #reverse()
   */
  @Override
  public Iterator<E> iterator() {
    return new WrapArrayIterator(move(front, -1), back, true);
  }

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * How many elements are present in the deque?
   *
   * @return The count of elements present in the deque.
   */
  @Override
  public int size() {
    /* NOTE:
         In case the deque is wrapped around the internal array (the false case),
         `back` MUST BE subtracted from `arr.length` before `front` is added - to
         avoid INTEGER OVERFLOWS for larger values of `front` and `back`.
     */
    return (front >= back) ? front - back : (arr.length - back) + front;
  }

  /**
   * Is the deque empty?
   *
   * @return {@code true} if deque is empty, {@code false} otherwise.
   */
  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Does {@code elmt} exist in the deque?
   *
   * @param elmt The element to check existence of.
   * @return {@code true} if {@code elmt} exists, {@code false} otherwise.
   */
  @Override
  public boolean contains(E elmt) {
    for (E dequeElmt : this) {
      if (Objects.equals(elmt, dequeElmt)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Clear the deque of all its elements.
   * Sets all internal state members to their <em>default</em> initial state.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void clear() {
    arr = (E[]) new Object[INIT_CAPACITY];
    front = 0;
    back = 0;
  }

  /**
   * Return a shallow copy of the deque.
   * A shallow copy creates a copy of the deque but not the elements in the
   * deque.
   *
   * @return A shallow copy of the deque.
   *
   * @see #deepcopy(Function copyFn)
   */
  @Override
  public Deque<E> copy() {
    ArrayDeque<E> cp = new ArrayDeque<>((size() >= 2) ? (size() * 2) : INIT_CAPACITY);
    copyOver(cp.arr);
    cp.front = this.size();
    cp.back = 0;

    return cp;
  }

  /**
   * Returns a deepcopy of the deque.
   * A deepcopy creates a copy of the deque and populates it with copies of the
   * original elements.
   *
   * @param copyFn A {@link Function} that takes original element as the
   *               argument and returns a deepcopy of that element.
   * @return A deepcopy of the deque.
   *
   * @throws IllegalArgumentException If {@code copyFn} is {@code null}.
   * @see #copy()
   */
  @Override
  public Deque<E> deepcopy(Function<? super E, E> copyFn) {
    if (copyFn == null) throw new IllegalArgumentException("argument to deepcopy() is null");

    ArrayDeque<E> cp = new ArrayDeque<>((size() >= 2) ? (size() * 2) : INIT_CAPACITY);
    for (E elmt : this) {
      cp.addBack(copyFn.apply(elmt));
    }

    return cp;
  }

  /* **************************************************************************
   * Section: Deque Methods
   ************************************************************************** */

  /**
   * Get an iterable that produces the elements from the back to the front.
   *
   * @return An iterable.
   *
   * @see #iterator()
   */
  @Override
  public Iterable<E> reverse() {
    return () -> new WrapArrayIterator(move(front, -1), back, false);
  }

  /**
   * Add {@code elmt} to the front of the deque.
   *
   * @param elmt The element to add.
   */
  @Override
  public void addFront(E elmt) {
    if (size() + 1 == arr.length) {
      resize(arr.length * 2);
    }

    arr[front] = elmt;
    front = move(front, 1);
  }

  /**
   * Add {@code elmt} to the back of the deque.
   *
   * @param elmt The element to add.
   */
  @Override
  public void addBack(E elmt) {
    if (size() + 1 == arr.length) {
      resize(arr.length * 2);
    }

    back = move(back, -1);
    arr[back] = elmt;
  }

  /**
   * Delete and return the element at the front of the deque.
   *
   * @return The deleted element that was at the front.
   *
   * @throws NoSuchElementException If deque is empty (underflow).
   */
  @Override
  public E delFront() {
    if (isEmpty()) throw new NoSuchElementException("underflow: can't delete from empty deque");
    if (size() == arr.length / 4) {
      resize(arr.length / 2);
    }

    front = move(front, -1);
    E elmt = arr[front];
    arr[front] = null;

    return elmt;
  }

  /**
   * Delete and return the element at the back of the deque.
   *
   * @return The deleted element that was at the back.
   *
   * @throws NoSuchElementException If deque is empty (underflow).
   */
  @Override
  public E delBack() {
    if (isEmpty()) throw new NoSuchElementException("underflow: can't delete from empty deque");
    if (size() == arr.length / 4) {
      resize(arr.length / 2);
    }

    E elmt = arr[back];
    arr[back] = null;
    back = move(back, 1);

    return elmt;
  }

  /**
   * Return the element that was at the front of the deque, without
   * deleting it.
   *
   * @return The element that is at front of the deque.
   *
   * @throws NoSuchElementException If deque is empty (underflow).
   */
  @Override
  public E front() {
    if (isEmpty()) throw new NoSuchElementException("underflow: can't peek at empty queue");

    return arr[move(front, -1)];
  }

  /**
   * Return the element that was at the back of the deque, without
   * deleting it.
   *
   * @return The element that is at back of the deque.
   *
   * @throws NoSuchElementException If deque is empty (underflow).
   */
  @Override
  public E back() {
    if (isEmpty()) throw new NoSuchElementException("underflow: can't peek at empty queue");

    return arr[back];
  }

  /* **************************************************************************
   * Section: Helper Classes and Methods
   ************************************************************************** */

  /**
   * Takes an index value {@code val} as an argument and moves the index right by
   * {@code by} places. The move happens such that the indices wrap around the
   * internal array and "run off" the bounds.
   *
   * @param val The initial index to move right from.
   * @param by  The number of places to move right. <b>If {@code by} is negative,
   *            {@code val} moves left instead.</b>
   * @return The index that you end up at after the wrapped move.
   */
  private int move(int val, int by) {
    // assume that internal array has enough space
    int res = val + by;

    if (by >= 0) {
      return res % arr.length;
    } else {
      return (res >= 0) ? res : arr.length + res;
    }
  }

  /**
   * Resize the internal array to be of capacity {@code newSize}.
   * Resizing is achieved by copying over all the elements to a new array
   * and replacing the internal array.
   *
   * @param newSize The new desired capacity of the internal array. Must be greater
   *                than the size of the deque.
   */
  @SuppressWarnings("unchecked")
  private void resize(int newSize) {
    E[] newArr = (E[]) new Object[newSize];

    copyOver(newArr);
    front = size();   // `front` MUST be computed before `back`
    back = 0;

    arr = newArr;
  }

  /**
   * Copy over the elements from the internal array to {@code toArr}.
   * This copy over is a shallow copy as only references are copied. This method
   * handles wrapped state of the deque internally, but does not adjust the {@link #front}
   * and the {@link #back} indices. The copied over dequeue always starts at {@code 0}
   * forwards. Also, {@code toArr} is not checked for being of appropriately length.
   *
   * @param toArr The array to copy over elements into.
   * @throws ArrayIndexOutOfBoundsException If {@code toArr} has length less than deque
   *                                        size.
   */
  private void copyOver(E[] toArr) {
    if (front >= back) {
      System.arraycopy(arr, back, toArr, 0, size());
    } else {
      System.arraycopy(arr, back, toArr, 0, arr.length - back);
      System.arraycopy(arr, 0, toArr, arr.length - back, front);
    }
  }

  /**
   * An {@link Iterator} class that can iterate over the elements of a wrapped deque
   * from both sides.
   */
  private class WrapArrayIterator implements Iterator<E> {
    private final int last;        // last element to iterate here
    private final int step;        // step decides direction to iterate in
    private int current;           // the start, then current index of iteration

    /**
     * Initialize and return a new WrapArrayIterator iterates between indices {@code frontIncl}
     * and {@code backIncl}.
     * The direction of iteration is decided by {@code frontToBack}.
     *
     * @param frontIncl   The index at which first key of the deque is located.
     * @param backIncl    The index at which the last key of the deque is located.
     * @param frontToBack Pass {@code true} if iteration should take place from front
     *                    to the back of the deque, {@code false} for the other direction.
     */
    private WrapArrayIterator(int frontIncl, int backIncl, boolean frontToBack) {
      if (frontToBack) {
        current = frontIncl;
        last = backIncl;
        step = -1;
      } else {
        current = backIncl;
        last = frontIncl;
        step = 1;
      }
    }

    /**
     * Can the iterator produce another value?
     *
     * @return {@code false} if the iterator has been depleted, {@code true} otherwise.
     */
    @Override
    public boolean hasNext() {
      return current != ArrayDeque.this.move(last, step);
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

      E elmt = arr[current];
      current = ArrayDeque.this.move(current, step);

      return elmt;
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
