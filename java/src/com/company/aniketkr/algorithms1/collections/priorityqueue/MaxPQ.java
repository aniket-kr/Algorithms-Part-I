package com.company.aniketkr.algorithms1.collections.priorityqueue;

import com.company.aniketkr.algorithms1.collections.Collection;

import java.util.Iterator;

/**
 * A maximum oriented priority queue is an ordered collection of objects that
 * supports efficient operations to <em>insert</em>, <em>delete</em> and
 * <em>find-the-maximum</em> key(s). This implementation also supports the above
 * operations.
 *
 * <p>Apart from these, the {@link MaxPQ} also supports iteration over keys in
 * descending order, using time and space proportional to <code>&theta;(n)</code>.</p>
 *
 * @param <K> The type of keys in the max-oriented PQ that implement either a natural
 *            order using {@link Comparable} interface or have an accompanying
 *            {@link java.util.Comparator} passed to the constructor.
 * @author Aniket Kumar
 */
public class MaxPQ<K> implements Collection<K> {

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * Check if this priority queue is equal to the given object.
   * Calls {@link Object#equals(Object that) equals()} on the elements to check if they
   * are equal.
   *
   * @param obj The other object to check for equality.
   * @return {@code true} if {@code obj} is semantically equal to this priority queue,
   *     {@code false} otherwise.
   * @implSpec As it currently stands, if two priority queue objects are empty, the
   *     {@code equals} method will always return {@code true}. This happens as no
   *     elements are available to compare.
   */
  public boolean equals(Object obj) {
    return false;
  }

  /**
   * Return a string representation of the priority queue. Primarily for debugging.
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
   * Get an iterator object that produces the elements of the priority queue in
   * descending order.
   *
   * @return An iterator.
   */
  @Override
  public Iterator<K> iterator() {
    return null;
  }

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * How many elements are present in the priority queue?
   *
   * @return The count of number of elements in the priority queue.
   */
  @Override
  public int size() {
    return 0;
  }

  /**
   * Is the priority queue empty?
   *
   * @return {@code true} if priority queue is empty, {@code false} otherwise.
   */
  @Override
  public boolean isEmpty() {
    return false;
  }

  /**
   * Clear the priority queue of all its elements. Set it to its instantiated state.
   */
  @Override
  public void clear() {

  }

  /**
   * Get a shallow copy of the priority queue.
   *
   * @return A new priority queue.
   */
  @Override
  public MaxPQ<K> copy() {
    return null;
  }

  /* **************************************************************************
   * Section: Priority Queue Methods
   ************************************************************************** */

  /**
   * Insert the given key {@code key} into the priority queue.
   *
   * @param key The key to insert.
   * @throws IllegalArgumentException When {@code key} is {@code null}.
   */
  public void insert(K key) {
  }

  /**
   * Delete and return the maximum key from the priority queue.
   *
   * @return The now deleted key.
   * @throws java.util.NoSuchElementException If the priority queue is empty (underflow).
   */
  public K delMax() {
    return null;
  }

  /**
   * Return the maximum key from the priority queue, without removing it.
   *
   * @return The maximum most key in the priority queue.
   * @throws java.util.NoSuchElementException If the priority queue is empty (underflow).
   */
  public K max() {
    return null;
  }

  /* **************************************************************************
   * Section: Helper Methods
   ************************************************************************** */
}
