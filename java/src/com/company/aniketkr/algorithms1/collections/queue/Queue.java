package com.company.aniketkr.algorithms1.collections.queue;

import com.company.aniketkr.algorithms1.collections.Collection;

import java.util.Iterator;
import java.util.function.Function;

/**
 * A queue is a collection of objects that follows a <em>First-In-First-Out</em> (FIFO)
 * order. Alongside all {@link Collection} operations, this interface guarantees support
 * for the usual <em>enqueue</em>, <em>dequeue</em> and <em>peek</em> operations.
 *
 * <p>
 * The <em>copy</em> and <em>deepcopy</em> operations return new {@link Queue} objects. The
 * iteration of the elements of the queue happens in <em>FIFO</em> order.
 * </p>
 *
 * @param <E> The type of element in the queue.
 * @author Aniket Kumar
 */
public interface Queue<E> extends Collection<E> {

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * Is the queue equal to the given object {@code obj}?
   * A queue will be deemed equal to {@code obj} if {@code obj} satisfies the following
   * conditions:
   * <ol>
   *   <li>{@code obj} is a {@link Queue} object or one of its subclasses.</li>
   *   <li>
   *     All the elements of {@code obj} are equal to the corresponding elements in this
   *     queue.
   *   </li>
   * </ol>
   *
   * <p>
   *   Because of Java's use of erasure, it is currently not possible to differentiate
   *   between the type of elements in two empty queues. This implies that if two
   *   empty {@link Queue} objects - which store two different types of elements, are
   *   compared with {@code equals()}, then the result will always be {@code true}.
   * </p>
   *
   * @param obj The other Object to compare {@code this} with for equality.
   * @return {@code true} if {@code obj} is equal to this queue,
   *     {@code false} otherwise.
   */
  @Override
  boolean equals(Object obj);

  /**
   * Get a string representation of the queue.
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
   * Get an iterator that produces the elements of the queue in <em>FIFO</em> order.
   *
   * @return An iterator.
   */
  @Override
  Iterator<E> iterator();

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * How many elements are present in the queue?
   *
   * @return The count of elements present in the queue.
   */
  @Override
  int size();

  /**
   * Is the queue empty?
   *
   * @return {@code true} if queue is empty, {@code false} otherwise.
   */
  @Override
  boolean isEmpty();

  /**
   * Does {@code elmt} exist in the queue?
   *
   * @param elmt The element to check existence of.
   * @return {@code true} if {@code elmt} exists, {@code false} otherwise.
   *
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  @Override
  boolean contains(E elmt);

  /**
   * Clear the queue of all its elements.
   * Sets all internal state members to their <em>default</em> initial state.
   */
  @Override
  void clear();

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
  Queue<E> copy();

  /**
   * Returns a deepcopy of the queue.
   * A deepcopy creates a copy of the queue and populates it with copies of the
   * original elements.
   *
   * @param copyFn A {@link Function} that takes original element as the
   *               argument and returns a deepcopy of that element.
   * @return A deepcopy of the queue.
   *
   * @throws IllegalArgumentException If {@code copyFn} is {@code null} or it
   *                                  returns any {@code null} values.
   * @see #copy()
   */
  @Override
  Queue<E> deepcopy(Function<? super E, E> copyFn);

  /* **************************************************************************
   * Section: Queue Methods
   ************************************************************************** */

  /**
   * Add {@code elmt} to the back of the queue.
   *
   * @param elmt The element to add to the queue.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  void enqueue(E elmt);

  /**
   * Remove the first element from the front of the queue.
   *
   * @return The removed element.
   *
   * @throws java.util.NoSuchElementException If queue is empty; <em>underflow</em>.
   */
  E dequeue();

  /**
   * Return the first element from the front of the queue, without removing it.
   *
   * @return The first element of the queue.
   *
   * @throws java.util.NoSuchElementException If queue is empty; <em>underflow</em>.
   */
  E peek();
}