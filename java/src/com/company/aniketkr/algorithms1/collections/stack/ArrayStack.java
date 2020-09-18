package com.company.aniketkr.algorithms1.collections.stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

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
  private static final int INIT_CAPACITY = 4;

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
  public ArrayStack(int capacity) {
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
    return false;
  }

  /**
   * Return a string representation of the stack. Primarily for debugging.
   *
   * @return A string.
   */
  public String toString() {
    return null;
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
    return null;
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
    return 0;
  }

  /**
   * Is the stack empty?
   *
   * @return {@code true} if stack is empty, {@code false} otherwise.
   */
  @Override
  public boolean isEmpty() {
    return false;
  }

  /**
   * Clear the stack of all its elements. Set it to its instantiated state.
   */
  @Override
  public void clear() {

  }

  /**
   * Get a shallow copy of the stack.
   *
   * @return A new stack.
   */
  @Override
  public Stack<E> copy() {
    return null;
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

  }

  /**
   * Pop/Remove the last inserted element off the stack.
   *
   * @return The removed element.
   * @throws NoSuchElementException If the stack is empty (underflow).
   */
  @Override
  public E pop() {
    return null;
  }

  /**
   * Return the last inserted element from the stack, without removing it.
   *
   * @return The last inserted element.
   * @throws NoSuchElementException If the stack is empty (underflow).
   */
  @Override
  public E peek() {
    return null;
  }
}
