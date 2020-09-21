package com.company.aniketkr.algorithms1.collections.stack;


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;


/**
 * Implements the {@link Stack} interface using an internally resizing array.
 * If nothing is explicitly specified, then the internal array starts off with capacity
 * for {@value INIT_CAPACITY} elements. The internal array is guaranteed to be between
 * 25% to 100% full at all times. The size of the array increases (or decreases) by a factor
 * of 2.
 *
 * <p>
 * The <em>push</em> and the <em>pop</em> operations take constant amortized time, worst
 * case time is <code>O(n)</code>. The operations <em>equals</em> and <em>contains</em> take
 * <code>&theta;(n)</code> time. Operations to <em>copy</em> and <em>deepcopy</em> take
 * <code>&theta;(n)</code> time, with <em>copy</em> operations being slightly faster. All
 * other operations take constant time.
 * </p>
 *
 * @param <E> The type of element in the stack.
 * @author Aniket Kumar
 */
public final class ArrayStack<E> implements Stack<E> {
  private static final int INIT_CAPACITY = 8;   // default initial capacity of stack
  private E[] arr;                              // the stack
  private int head = 0;

  /**
   * Initialize and return a new {@link ArrayStack} object with default capacity
   * for {@value INIT_CAPACITY} elements.
   */
  public ArrayStack() {
    this(INIT_CAPACITY);
  }

  /**
   * Initialize and return a new ArrayStack object, ensuring capacity for {@code capacity}
   * elements.
   *
   * @param capacity The number of elements that internal array should be able to
   *                 accommodate without needing to resize.
   * @throws IllegalArgumentException If {@code capacity} is less than or equal to 0.
   */
  @SuppressWarnings("unchecked")  // cast to generic array
  public ArrayStack(int capacity) {
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
   * @return {@code true} if {@code obj} is equal to this stack,
   *     {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)                return true;
    if (obj == null)                return false;
    if (!(obj instanceof Stack))    return false;
    Stack<?> that = (Stack<?>) obj;
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
   * Get a string representation of the stack.
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
   * Get an iterator that produces the elements of the stack in <em>FILO</em> order.
   *
   * @return An iterator.
   */
  @Override
  public Iterator<E> iterator() {
    return new ReverseArrayIterator();
  }

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * How many elements are present in the stack?
   *
   * @return The count of elements present in the stack.
   */
  @Override
  public int size() {
    return head;
  }

  /**
   * Is the stack empty?
   *
   * @return {@code true} if stack is empty, {@code false} otherwise.
   */
  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Does {@code elmt} exist in the stack?
   *
   * @param elmt The element to check existence of.
   * @return {@code true} if {@code elmt} exists, {@code false} otherwise.
   *
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  @Override
  public boolean contains(E elmt) {
    if (elmt == null) throw new IllegalArgumentException("argument to contains() in null");

    for (E stackElmt : this) {
      if (stackElmt.equals(elmt)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Clear the stack of all its elements.
   * Sets all internal state members to their <em>default</em> initial state.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void clear() {
    arr = (E[]) new Object[INIT_CAPACITY];
    head = 0;
  }

  /**
   * Return a shallow copy of the stack.
   * A shallow copy creates a copy of the stack but not the elements in the
   * stack.
   *
   * @return A shallow copy of the stack.
   *
   * @see #deepcopy(Function elmtCopyFunction)
   */
  @Override
  public Stack<E> copy() {
    ArrayStack<E> cp = new ArrayStack<>(size() * 2);
    System.arraycopy(this.arr, 0, cp.arr, 0, size());
    cp.head = this.head;

    return cp;
  }

  /**
   * Returns a deepcopy of the stack.
   * A deepcopy creates a copy of the stack and populates it with copies of the
   * original elements.
   *
   * @param copyFn A {@link Function} that takes original element as the
   *               argument and returns a copy of that element.
   * @return A deepcopy of the stack.
   *
   * @throws IllegalArgumentException If {@code copyFn} is {@code null}.
   * @see #copy()
   */
  @Override
  public Stack<E> deepcopy(Function<? super E, E> copyFn) {
    if (copyFn == null) throw new IllegalArgumentException("argument to deepcopy is null");

    ArrayStack<E> cp = new ArrayStack<>(size() * 2);

    for (int i = 0; i < size(); i++) {
      cp.arr[i] = copyFn.apply(this.arr[i]);
    }
    cp.head = this.head;

    return cp;
  }

  /* **************************************************************************
   * Section: Stack Methods
   ************************************************************************** */

  /**
   * Push the given element onto the stack.
   *
   * @param elmt The new element to add to the stack.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  @Override
  public void push(E elmt) {
    if (elmt == null) throw new IllegalArgumentException("argument to push() is null");
    if (size() == arr.length) {
      resize(arr.length * 2);
    }

    arr[head++] = elmt;
  }

  /**
   * Pop the last pushed element off the stack.
   *
   * @return The removed/popped element.
   *
   * @throws NoSuchElementException If the stack is empty; <em>underflow</em>.
   */
  @Override
  public E pop() {
    if (isEmpty()) throw new NoSuchElementException("underflow: can't pop() from empty stack");
    if (size() == arr.length / 4) {
      resize(arr.length / 2);
    }

    E elmt = arr[--head];
    arr[head] = null;
    return elmt;
  }

  /**
   * Get the element that was last pushed onto the stack, without popping it off.
   *
   * @return The last pushed element.
   *
   * @throws NoSuchElementException If the stack is empty; <em>underflow</em>.
   */
  @Override
  public E peek() {
    if (isEmpty()) throw new NoSuchElementException("underflow: can't peek() at empty stack");

    return arr[head - 1];
  }

  /* **************************************************************************
   * Section: Helper Methods and Classes
   ************************************************************************** */

  /**
   * Resize the internal array to {@code newSize}.
   * Creates a new array, copies over elements from the original array and then reassigns
   * the new array in place of the original array.
   *
   * @param newSize The new desired capacity of the internal array, and hence the new capacity
   *                of the stack.
   * @implNote The method does <strong>NOT</strong> validate the argument passed to it in
   *     any form.
   */
  @SuppressWarnings("unchecked")
  private void resize(int newSize) {
    E[] newArr = (E[]) new Object[newSize];
    System.arraycopy(arr, 0, newArr, 0, size());
    arr = newArr;
  }

  /**
   * An {@link Iterator} implementing class that iterates over the internal array in reverse.
   * This essentially makes sure that the stack is iterated in <em>FILO</em> order.
   */
  private class ReverseArrayIterator implements Iterator<E> {
    private int current = head - 1;  // start with this index of arr

    /**
     * Can the iterator produce another value?
     * Check if the iterator has been depleted, or not.
     *
     * @return {@code false} if the iterator has been depleted, otherwise {@code true}.
     */
    @Override
    public boolean hasNext() {
      return current >= 0;
    }

    /**
     * Produce the next value from the iterator.
     *
     * @return The next value.
     *
     * @throws NoSuchElementException If {@code next()} is called after iterator has been
     *                                depleted.
     */
    @Override
    public E next() {
      if (!hasNext()) throw new NoSuchElementException("iterator depleted");

      return arr[current--];
    }

    /**
     * Remove operation is not supported. Throws UOE.
     *
     * @throws UnsupportedOperationException Always.
     */
    @Override
    public void remove() {
      throw new UnsupportedOperationException("remove() not supported");
    }
  }
}
