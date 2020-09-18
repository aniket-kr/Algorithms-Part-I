package com.company.aniketkr.algorithms1.collections.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implements the {@link Queue} interface using a singly linked list. The
 * "nodes" to the list are added/removed as elements are <em>enqueued/dequeued</em>
 * from the queue.
 *
 * @param <E> The type of elements in the queue.
 * @author Aniket Kumar
 */
public final class LinkedQueue<E> implements Queue<E> {

  /**
   * Initialize and return a new LinkedQueue object.
   */
  public LinkedQueue() {
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
   *     method will always return {@code true}. This happens as no elements are available to
   *     compare.
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
