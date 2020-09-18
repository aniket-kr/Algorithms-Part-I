package com.company.aniketkr.algorithms1.collections.queue;

import com.company.aniketkr.algorithms1.collections.stack.Stack;

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
 * @param <E> The type of elements in the queue.
 * @author Aniket Kumar
 */
public final class ArrayQueue<E> implements Queue<E> {
  private static final int INIT_CAPACITY = 8;

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
  public ArrayQueue(int capacity) {
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
    return false;
  }

  /**
   * Return a string representation of the queue. Primarily for debugging.
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
   * Get an iterator object that produces the elements of the queue in its natural
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
   * How many elements are present in the queue?
   *
   * @return The count of number of elements in the queue.
   */
  @Override
  public int size() {
    return 0;
  }

  /**
   * Is the queue empty?
   *
   * @return {@code true} if queue is empty, {@code false} otherwise.
   */
  @Override
  public boolean isEmpty() {
    return false;
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
    return false;
  }

  /**
   * Clear the queue of all its elements. Set it to its instantiated state.
   */
  @Override
  public void clear() {

  }

  /**
   * Get a shallow copy of the queue.
   *
   * @return A new queue.
   */
  @Override
  public Queue<E> copy() {
    return null;
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

  }

  /**
   * Remove the first element from the front of the queue.
   *
   * @return The removed element.
   * @throws NoSuchElementException If queue is empty (underflow).
   */
  @Override
  public E dequeue() {
    return null;
  }

  /**
   * Return the first element from the front of the queue, without removing it.
   *
   * @return The first element of the queue.
   * @throws NoSuchElementException If queue is empty (underflow).
   */
  @Override
  public E peek() {
    return null;
  }
}
