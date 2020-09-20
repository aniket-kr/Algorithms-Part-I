package com.company.aniketkr.algorithms1.collections.stack;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

// TODO: docs under construction

/**
 * Implements the {@link Stack} interface using an internal resizing array. The
 * internal array starts off with initial capacity {@value INIT_CAPACITY} if nothing
 * is explicitly specified.
 *
 * <p>This array is guaranteed to be between 25% to 100% full at all times. The size
 * of the array increases (or decreases) by a factor of 2.</p>
 *
 * @param <E> The type of elements in the stack.
 * @author Aniket Kumar
 */
public final class ArrayStack<E> implements Stack<E> {
  private static final int INIT_CAPACITY = 8;
  private E[] arr;
  private int head = 0;

  /**
   * Initialize and return a new ArrayStack object.
   * Default capacity is {@value INIT_CAPACITY}.
   */
  public ArrayStack() {
    this(INIT_CAPACITY);
  }

  /**
   * Initialize and return a new ArrayStack object, ensuring capacity for
   * {@code capacity} objects.
   *
   * @param capacity The number of elements that internal array should be able to
   *                 accommodate without needing to resize.
   * @throws NegativeArraySizeException If {@code capacity} is negative.
   */
  @SuppressWarnings("unchecked")
  public ArrayStack(int capacity) {
    arr = (E[]) new Object[capacity];
  }

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * Check if this stack is equal to the given object.
   * Calls {@link Object#equals(Object that) equals()} on the elements to check if they
   * are equal.
   *
   * @param obj The other object to check for equality.
   * @return {@code true} if {@code obj} is semantically equal to this stack,
   *     {@code false} otherwise.
   * @implSpec As it currently stands, if two stack objects are empty, the {@code equals}
   *     method will always return {@code true}. This happens as no elements are available
   *     to compare.
   */
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
   * Return a string representation of the stack. Primarily for debugging.
   *
   * @return A string.
   */
  public String toString() {
    if (isEmpty()) return "ArrayStack[0] [ ]";

    StringBuilder sb = new StringBuilder("ArrayStack[").append(size()).append("] [ ");
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
   * Get an iterator object that produces the elements of the stack in its natural
   * order.
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
   * @return The count of number of elements in the stack.
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
   * @param elmt The element to check for.
   * @return {@code true} if {@code elmt} exists in the stack, {@code false}
   *     otherwise.
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
   * Clear the stack of all its elements. Set it to its instantiated state.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void clear() {
    arr = (E[]) new Object[INIT_CAPACITY];
    head = 0;
  }

  /**
   * Get a shallow copy of the stack.
   *
   * @return A new stack.
   */
  @Override
  public Stack<E> copy() {
    ArrayStack<E> cp = new ArrayStack<>(size() * 2);
    System.arraycopy(this.arr, 0, cp.arr, 0, size());
    cp.head = this.head;

    return cp;
  }

  /* **************************************************************************
   * Section: Stack Methods
   ************************************************************************** */

  /**
   * Push the element {@code elmt} onto the stack.
   *
   * @param elmt The element to push.
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
   * Pop/Remove the last inserted element off the stack.
   *
   * @return The removed element.
   * @throws NoSuchElementException If the stack is empty (underflow).
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
   * Return the last inserted element from the stack, without removing it.
   *
   * @return The last inserted element.
   * @throws NoSuchElementException If the stack is empty (underflow).
   */
  @Override
  public E peek() {
    if (isEmpty()) throw new NoSuchElementException("underflow: can't peek() at empty stack");

    return arr[head - 1];
  }

  /* **************************************************************************
   * Section: Helper Methods and Classes
   ************************************************************************** */

  @SuppressWarnings("unchecked")
  private void resize(int newSize) {
    E[] newArr = (E[]) new Object[newSize];
    System.arraycopy(arr, 0, newArr, 0, size());
    arr = newArr;
  }

  private class ReverseArrayIterator implements Iterator<E> {
    private int current = head - 1;

    @Override
    public boolean hasNext() {
      return current >= 0;
    }

    @Override
    public E next() {
      if (!hasNext()) throw new NoSuchElementException("iterator depleted");

      return arr[current--];
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException("remove() not supported");
    }
  }
}
