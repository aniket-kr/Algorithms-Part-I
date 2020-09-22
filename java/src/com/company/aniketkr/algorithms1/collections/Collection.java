package com.company.aniketkr.algorithms1.collections;

import java.util.Iterator;
import java.util.function.Function;

/**
 * A {@link Collection} represents a collection of objects of the same type. Every
 * collection object supports the methods <em>size</em>, <em>is-empty</em>,
 * <em>clear</em> and <em>copy</em>. Apart from these methods, all collection objects
 * guarantee overriding {@link Object#equals(Object obj) equals} and
 * {@link Object#toString() toString} Object methods. Collections are naturally iterable
 * and hence implement the {@link Iterable} interface.
 *
 * @param <E> The type of element in the collection.
 * @author Aniket Kumar
 */
public interface Collection<E> extends Iterable<E> {

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * Is the collection equal to the given object {@code obj}?
   * A collection will be deemed equal to {@code obj} if {@code obj} satisfies the following
   * conditions:
   * <ol>
   *   <li>{@code obj} is a {@link Collection} object or one of its subclasses.</li>
   *   <li>
   *     All the elements of {@code obj} are equal to the corresponding elements in this
   *     collection.
   *   </li>
   * </ol>
   *
   * <p>
   *   Because of Java's use of erasure, it is currently not possible to differentiate
   *   between the type of elements in two empty collections. This implies that if two
   *   empty {@link Collection} objects - which store two different types of elements, are
   *   compared with {@code equals()}, then the result will always be {@code true}.
   * </p>
   *
   * @param obj The other Object to compare {@code this} with for equality.
   * @return {@code true} if {@code obj} is equal to this collection,
   *     {@code false} otherwise.
   */
  @Override
  boolean equals(Object obj);

  /**
   * Get a string representation of the collection.
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
   * Get an iterator that produces the elements of the collection in its <em>natural
   * order</em>.
   * The natural order is best defined by the implementing subclass.
   *
   * @return An iterator.
   */
  Iterator<E> iterator();

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * How many elements are present in the collection?
   *
   * @return The count of elements present in the collection.
   */
  int size();

  /**
   * Is the collection empty?
   *
   * @return {@code true} if collection is empty, {@code false} otherwise.
   */
  boolean isEmpty();

  /**
   * Does {@code elmt} exist in the collection?
   *
   * @param elmt The element to check existence of.
   * @return {@code true} if {@code elmt} exists, {@code false} otherwise.
   */
  boolean contains(E elmt);

  /**
   * Clear the collection of all its elements.
   * Sets all internal state members to their <em>default</em> initial state.
   */
  void clear();

  /**
   * Return a shallow copy of the collection.
   * A shallow copy creates a copy of the collection but not the elements in the
   * collection.
   *
   * @return A shallow copy of the collection.
   *
   * @see #deepcopy(Function copyFn)
   */
  Collection<E> copy();

  /**
   * Returns a deepcopy of the collection.
   * A deepcopy creates a copy of the collection and populates it with copies of the
   * original elements.
   *
   * @param copyFn A {@link Function} that takes original element as the
   *               argument and returns a deepcopy of that element.
   * @return A deepcopy of the collection.
   *
   * @throws IllegalArgumentException If {@code copyFn} is {@code null}.
   * @see #copy()
   */
  Collection<E> deepcopy(Function<? super E, E> copyFn);
}
