package com.company.aniketkr.algorithms1.collections.priorityqueue;


import com.company.aniketkr.algorithms1.collections.Collection;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Function;


/**
 * A priority queue is a collection that is very similar to a
 * {@link com.company.aniketkr.algorithms1.collections.queue.Queue queue} but with the
 * difference that the elements are arranged according to a specific order. The elements
 * are, hence, called "keys". The priority queue can be ordered using either the natural
 * order defined by the {@link Comparable} interface or use a specific order using a
 * {@link Comparator} interface.
 *
 * <p>
 * Alongside all other {@link Collection} operations, this interface guarantees support for
 * the usual <em>insert</em>, <em>find-the-minimum</em> and <em>delete-the-minimum</em>
 * operations. Apart from these operations, the <em>copy</em> and <em>deepcopy</em> operations
 * return new {@link PQ} objects. The keys in the priority queue can be iterated over in
 * increasing order of the specific priority being used.
 * </p>
 *
 * @param <K> The type of key in the priority queue. This type should either implement
 *            the {@link Comparable} interface or be accompanied by a {@link Comparator}
 *            object that can compare keys of this type. If neither of these conditions is
 *            met, then an {@link IllegalStateException} should be thrown whenever the
 *            noncompliance is detected.
 * @author Aniket Kumar
 */
public interface PQ<K> extends Collection<K> {

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * Is the priority queue equal to the given object {@code obj}?
   * A priority queue will be deemed equal to {@code obj} if {@code obj} satisfies
   * the following conditions:
   * <ol>
   *   <li>{@code obj} is a {@link PQ} object or one of its subclasses.</li>
   *   <li>
   *     All the keys of {@code obj} are equal to the corresponding keys in this
   *     priority queue.
   *   </li>
   * </ol>
   *
   * <p>
   *   Because of Java's use of erasure, it is currently not possible to differentiate
   *   between the type of keys in two empty priority queues. This implies that if
   *   two empty {@link PQ} objects - which store two different types of keys, are
   *   compared with {@code equals()}, then the result will always be {@code true}.
   * </p>
   *
   * @param obj The other Object to compare {@code this} with for equality.
   * @return {@code true} if {@code obj} is equal to this priority queue,
   *     {@code false} otherwise.
   */
  @Override
  boolean equals(Object obj);

  /**
   * Get a string representation of the priority queue.
   * Primarily for debugging and helping find bugs in client code.
   *
   * @return A string.
   */
  @Override
  String toString();

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
  Iterator<K> iterator();

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * How many keys are present in the priority queue?
   *
   * @return The count of keys present in the priority queue.
   */
  @Override
  int size();

  /**
   * Is the priority queue empty?
   *
   * @return {@code true} if priority queue is empty, {@code false} otherwise.
   */
  @Override
  boolean isEmpty();

  /**
   * Does {@code key} exist in the priority queue?
   *
   * @param key The key to check existence of.
   * @return {@code true} if {@code key} exists, {@code false} otherwise.
   *
   * @throws IllegalArgumentException If {@code key} is {@code null}.
   */
  @Override
  boolean contains(K key);

  /**
   * Clear the priority queue of all its keys.
   * Sets all internal state members to their <em>default</em> initial state.
   */
  @Override
  void clear();

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
  PQ<K> copy();

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
  PQ<K> deepcopy(Function<? super K, K> copyFn);

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
  Comparator<K> comparator();

  /**
   * Insert {@code key} into the priority queue.
   *
   * @param key The key to be inserted.
   * @throws IllegalArgumentException If {@code key} is {@code null}.
   */
  void insert(K key);

  /**
   * Remove and return the key that has the least priority according to the specific
   * ordering being used.
   *
   * @return The removed key with least priority.
   *
   * @throws java.util.NoSuchElementException If the priority queue is empty;
   *                                          <em>underflow</em>.
   */
  K poll();

  /**
   * Return the key that has the least priority according to the specific ordering
   * being used, without removing it.
   *
   * @return The key that has least priority in the queue.
   */
  K peek();
}
