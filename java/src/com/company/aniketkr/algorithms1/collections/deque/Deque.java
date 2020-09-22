package com.company.aniketkr.algorithms1.collections.deque;

import com.company.aniketkr.algorithms1.collections.Collection;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;


/**
 * A deque (pronounced "deck") is a collection of objects such that we can access and
 * alter the elements from two ends - the <em>front</em> and the <em>back</em>. This
 * implementation of deque supports the usual <em>add</em>, <em>delete</em> and <em>peek</em>
 * operations from both ends. The collection allows {@code null} values as elements.
 *
 * <p>
 * Apart from these operations, the <em>copy</em> method returns a {@link Deque} object.
 * The deque also supports iteration of elements in both directions - {@link Iterable} interface
 * supports iteration from the <em>front</em> to the <em>back</em> and the <em>reverse</em>
 * method supports it in the other direction.
 * </p>
 *
 * @param <E> The type of element in the deque.
 * @author Aniket Kumar
 */
public interface Deque<E> extends Collection<E> {

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * Is the deque equal to the given object {@code obj}?
   * A deque will be deemed equal to {@code obj} if {@code obj} satisfies the following
   * conditions:
   * <ol>
   *   <li>{@code obj} is a {@link Deque} object or one of its subclasses.</li>
   *   <li>
   *     All the elements of {@code obj} are equal to the corresponding elements in this
   *     deque.
   *   </li>
   * </ol>
   *
   * <p>
   *   Because of Java's use of erasure, it is currently not possible to differentiate
   *   between the type of elements in two empty dequeues. This implies that if two
   *   empty {@link Deque} objects - which store two different types of elements, are
   *   compared with {@code equals()}, then the result will always be {@code true}.
   * </p>
   *
   * @param obj The other Object to compare {@code this} with for equality.
   * @return {@code true} if {@code obj} is equal to this deque,
   *     {@code false} otherwise.
   */
  @Override
  boolean equals(Object obj);

  /**
   * Get a string representation of the deque.
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
   * Get an iterator that produces the elements of the deque from the front
   * to the back.
   *
   * @return An iterator.
   *
   * @see #reverse()
   */
  @Override
  Iterator<E> iterator();

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * How many elements are present in the deque?
   *
   * @return The count of elements present in the deque.
   */
  @Override
  int size();

  /**
   * Is the deque empty?
   *
   * @return {@code true} if deque is empty, {@code false} otherwise.
   */
  @Override
  boolean isEmpty();

  /**
   * Does {@code elmt} exist in the deque?
   *
   * @param elmt The element to check existence of.
   * @return {@code true} if {@code elmt} exists, {@code false} otherwise.
   */
  @Override
  boolean contains(E elmt);

  /**
   * Clear the deque of all its elements.
   * Sets all internal state members to their <em>default</em> initial state.
   */
  @Override
  void clear();

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
  Collection<E> copy();

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
  Collection<E> deepcopy(Function<? super E, E> copyFn);

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
  Iterable<E> reverse();

  /**
   * Add {@code elmt} to the front of the deque.
   *
   * @param elmt The element to add.
   */
  void addFront(E elmt);

  /**
   * Add {@code elmt} to the back of the deque.
   *
   * @param elmt The element to add.
   */
  void addBack(E elmt);

  /**
   * Delete and return the element at the front of the deque.
   *
   * @return The deleted element that was at the front.
   *
   * @throws NoSuchElementException If deque is empty (underflow).
   */
  E delFront();

  /**
   * Delete and return the element at the back of the deque.
   *
   * @return The deleted element that was at the back.
   *
   * @throws NoSuchElementException If deque is empty (underflow).
   */
  E delBack();

  /**
   * Return the element that was at the front of the deque, without
   * deleting it.
   *
   * @return The element that is at front of the deque.
   *
   * @throws NoSuchElementException If deque is empty (underflow).
   */
  E front();

  /**
   * Return the element that was at the back of the deque, without
   * deleting it.
   *
   * @return The element that is at back of the deque.
   *
   * @throws NoSuchElementException If deque is empty (underflow).
   */
  E back();
}
