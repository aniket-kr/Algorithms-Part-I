package com.company.aniketkr.algorithms1.collections.list;

import com.company.aniketkr.algorithms1.collections.Collection;

/**
 * A list is a collection of objects in which the order is defined by the positions
 * of the elements, called "index". This interface provides guaranteed support for
 * the usual <em>add</em>, <em>delete</em>, <em>get</em> and <em>contains</em> operations.
 * Additionally, it also provides guarantee for <em>adding-all</em>, <em>inserting</em>,
 * <em>inserting-all</em> and <em>get-index</em> operations.
 *
 * <p>The <em>copy</em> operation returns a {@link List} object. The iteration happens
 * from lowest index to highest index.</p>
 *
 * @param <E> The type of elements in the list.
 * @author Aniket Kumar
 */
public interface List<E> extends Collection<E> {

  /**
   * Does the element {@code elmt} exist in the list?
   *
   * @param elmt The element.
   * @return {@code true} if {@code elmt} exists in the list, {@code false} otherwise.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  boolean contains(E elmt);

  /**
   * Add the given element {@code elmt} to the end of the list.
   *
   * @param elmt The element.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  void add(E elmt);

  /**
   * Insert the given element {@code elmt} at the index {@code atIndex}.
   *
   * @param elmt    The element.
   * @param atIndex The index to insert at.
   * @throws IllegalArgumentException  If {@code elmt} is {@code null}.
   * @throws IndexOutOfBoundsException If {@code atIndex} is not in range <code>
   *                                   [0, {@link #size()}]</code>.
   */
  void add(E elmt, int atIndex);

  /**
   * Add all the elements in the array {@code elmts} to the end of the list.
   *
   * @param elmts The array of elements to add.
   * @throws IllegalArgumentException If either {@code elmts} or any of its elements is
   *                                  {#code null}.
   */
  void addAll(E[] elmts);

  /**
   * Insert all the elements in the array {@code elmts} to the list, starting from
   * the index {@code fromIndex}.
   *
   * @param elmts     The array of elements to insert.
   * @param fromIndex The index at which the first element from {@code elmts}
   *                  should appear.
   * @throws IllegalArgumentException  If {@code elmts} or any of its elements is
   *                                   {@code null}.
   * @throws IndexOutOfBoundsException If {@code fromIndex} is not in range <code>
   *                                   [0, {@link #size()}]</code>.
   */
  void addAll(E[] elmts, int fromIndex);

  /**
   * Add all the elements produced by the iterable {@code elmts}, to the end of the
   * list.
   *
   * @param elmts The iterable that produces elements to add.
   * @throws IllegalArgumentException If {@code elmts}, or any of the elements it
   *                                  produces, is {@code null}.
   */
  void addAll(Iterable<? extends E> elmts);

  /**
   * Insert all the elements produced by the iterable {@code elmts} to the list, starting
   * from the index {#code fromIndex}.
   *
   * @param elmts     The iterable that produces elements to insert.
   * @param fromIndex The index at which the first element produced by {#code elmts}
   *                  should appear.
   * @throws IllegalArgumentException  If {@code elmts}, or any of the elements it
   *                                   produces, is {@code null}.
   * @throws IndexOutOfBoundsException If {@code fromIndex} is not in range <code>
   *                                   [0, {@link #size()}]</code>.
   */
  void addAll(Iterable<? extends E> elmts, int fromIndex);

  /**
   * Get the element at the index {@code index} in the list.
   *
   * @param index The index.
   * @return The element at {@code index}.
   * @throws IndexOutOfBoundsException If {@code index} is not in range <code>
   *                                   [0, {@link #size()}]</code>.
   */
  E get(int index);

  /**
   * Get the index of the first occurrence of {@code elmt} in the list. Uses elements'
   * {@link Object#equals(Object obj) equals} implementation to check if {@code elmt}
   * is equals to any of them.
   *
   * @param elmt The element to look for.
   * @return The index of first occurrence of {@code elmt} in the list; {@literal -1}
   *     if {@code elmt} does not exist in the list.
   */
  int indexOf(E elmt);

  /**
   * Get the index of the first occurrence of {@code elmt} between indices {@code fromIndex}
   * and {@code toIndex}.
   *
   * @param elmt      The element to look for.
   * @param fromIndex The index to start looking at (inclusive).
   * @param toIndex   The index to look upto (exclusive).
   * @return The index of the first occurrence of {@code elmt} in the range, {@literal -1}
   *     if {@code elmt} does not exist in the given range.
   * @throws IllegalArgumentException  If {@code elmt} is {@code null}.
   * @throws IndexOutOfBoundsException If either {@code fromIndex} or {@code toIndex} is
   *                                   not in range <code>[0, {@link #size()}]</code>.
   * @implSpec If no elements fall in the range specified by {@code fromIndex} and
   *     {@code toIndex}, then {@literal -1} is returned.
   */
  int indexOf(E elmt, int fromIndex, int toIndex);

  /**
   * Delete the element at the given index in the list and return it.
   *
   * @param index The index to delete element at.
   * @return The deleted element.
   * @throws IndexOutOfBoundsException If {@code index} is not in range <code>
   *                                   [0, {@link #size()}</code>.
   */
  E delete(int index);

  /**
   * Remove the first occurrence of the element {@code elmt} in the list and return its
   * index.
   *
   * @param elmt The element to remove.
   * @return The index of the now removed element; {@literal -1} if {@code elmt} did not
   *     exist in the list.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  int remove(E elmt);

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * Get a shallow copy of the list.
   *
   * @return A new list.
   */
  @Override
  List<E> copy();
}
