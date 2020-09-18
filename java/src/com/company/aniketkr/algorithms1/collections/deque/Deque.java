package com.company.aniketkr.algorithms1.collections.deque;

import com.company.aniketkr.algorithms1.collections.Collection;

import java.util.Iterator;

/**
 * A deque (pronounced "deck") is a collection of objects such that we can access and
 * alter the elements from two ends - <em>front</em> and <em>back</em>. This implementation
 * of deque supports the usual <em>add</em>, <em>delete</em> and <em>peek</em> operations
 * from both ends.
 *
 * <p>Apart from these operations, the <em>copy</em> method returns a {@link Deque} object.
 * The deque also supports iteration of elements in both directions - {@link Iterable} interface
 * supports iteration from <em>front</em> to <em>back</em> and the <em>reverse</em> method
 * supports it in the other direction.</p>
 *
 * @param <E> The type of elements in the deque.
 * @author Aniket Kumar
 */
public final class Deque<E> implements Collection<E> {

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * Check if this deque is equal to the given object.
   * Calls {@link Object#equals(Object that) equals()} on the elements to check if they
   * are equal.
   *
   * @param obj The other object to check for equality.
   * @return {@code true} if {@code obj} is semantically equal to this deque,
   *     {@code false} otherwise.
   * @implSpec As it currently stands, if two deque objects are empty, the {@code equals}
   *     method will always return {@code true}. This happens as no elements are available to
   *     compare.
   */
  public boolean equals(Object obj) {
    return false;
  }

  /**
   * Return a string representation of the deque. Primarily for debugging.
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
   * Get an iterator object that produces the elements of the deque from <em>front</em>
   * to <em>back</em>.
   *
   * @return An iterator.
   */
  @Override
  public Iterator<E> iterator() {
    return null;
  }

  /**
   * Get an iterable object that produces the elements from <em>back</em> to <em>front</em>.
   *
   * @return An iterable.
   */
  public Iterable<E> reverse() {
    return null;
  }

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * How many elements are present in the deque?
   *
   * @return The count of number of elements in the deque.
   */
  @Override
  public int size() {
    return 0;
  }

  /**
   * Is the deque empty?
   *
   * @return {@code true} if deque is empty, {@code false} otherwise.
   */
  @Override
  public boolean isEmpty() {
    return false;
  }

  /**
   * Does {@code elmt} exist in the deque?
   *
   * @param elmt The element to check for.
   * @return {@code true} if {@code elmt} exists in the deque, {@code false}
   *     otherwise.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  public boolean contains(E elmt) {
    return false;
  }

  /**
   * Clear the deque of all its elements. Set it to its instantiated state.
   */
  @Override
  public void clear() {

  }

  /**
   * Get a shallow copy of the deque.
   *
   * @return A new deque.
   */
  @Override
  public Deque<E> copy() {
    return null;
  }

  /* **************************************************************************
   * Section: Deque Methods
   ************************************************************************** */

  /**
   * Add the given element to the <em>front</em> of the deque.
   *
   * @param elmt The element to add.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  public void addFront(E elmt) {
  }

  /**
   * Add the given element to the <em>back</em> of the deque.
   *
   * @param elmt The element to add.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  public void addBack(E elmt) {
  }

  /**
   * Delete and return the element at the <em>front</em> of the deque.
   *
   * @return The deleted element that was at the <em>front</em>.
   * @throws java.util.NoSuchElementException If deque is empty (underflow).
   */
  public E delFront() {
    return null;
  }

  /**
   * Delete and return the element at the <em>back</em> of the deque.
   *
   * @return The deleted element that was at the <em>back</em>.
   * @throws java.util.NoSuchElementException If deque is empty (underflow).
   */
  public E delBack() {
    return null;
  }

  /**
   * Return the element that was at the <em>front</em> of the deque, without
   * deleting it.
   *
   * @return The element that is at <em>front</em> of the deque.
   * @throws java.util.NoSuchElementException If deque is empty (underflow).
   */
  public E peekFront() {
    return null;
  }

  /**
   * Return the element that was at the <em>back</em> of the deque, without
   * deleting it.
   *
   * @return The element that is at <em>back</em> of the deque.
   * @throws java.util.NoSuchElementException If deque is empty (underflow).
   */
  public E peekBack() {
    return null;
  }
}
