package com.company.aniketkr.algorithms1.collections;

import java.util.Iterator;

/**
 * A {@link Collection} represents a collection of objects of the same type. Every
 * collection object supports the methods <em>size</em>, <em>is-empty</em>,
 * <em>clear</em> and <em>copy</em>. Apart from these methods, all collection abjects
 * guarantee overriding {@link Object#equals(Object obj) equals} and
 * {@link Object#toString() toString} Object methods. All collections are naturally iterable
 * and hence implement the {@link Iterable} interface.
 *
 * @param <E> The type of elements in the collection.
 * @author Aniket Kumar
 */
public interface Collection<E> extends Iterable<E> {

  /**
   * How many elements are present in the collection?
   *
   * @return The count of number of elements in the collection.
   */
  int size();

  /**
   * Is the collection empty?
   *
   * @return {@code true} if collection is empty, {@code false} otherwise.
   */
  boolean isEmpty();

  /**
   * Clear the collection of all its elements. Set it to its instantiated state.
   */
  void clear();

  /**
   * Get a shallow copy of the collection.
   *
   * @return A new collection.
   */
  Collection<E> copy();

  /**
   * Get an iterator object that produces the elements of the collection in its natural
   * order.
   *
   * @return An iterator.
   */
  Iterator<E> iterator();

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * Check if this collection is equal to the given object.
   * Calls {@link Object#equals(Object that) equals()} on the elements to check if they
   * are equal.
   *
   * @param obj The other object to check for equality.
   * @return {@code true} if {@code obj} is semantically equal to this collection,
   *     {@code false} otherwise.
   * @implSpec As it currently stands, if two collection objects are empty, the {@code equals}
   *     method will always return {@code true}. This happens as no elements are available to
   *     compare.
   */
  boolean equals(Object obj);

  /**
   * Return a string representation of the collection. Primarily for debugging.
   *
   * @return A string.
   */
  String toString();
}
