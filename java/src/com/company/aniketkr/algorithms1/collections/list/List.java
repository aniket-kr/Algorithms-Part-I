package com.company.aniketkr.algorithms1.collections.list;

import com.company.aniketkr.algorithms1.collections.Collection;

import java.util.Iterator;
import java.util.function.Function;


/**
 * A list is a collection of objects in which the order is defined by the positions
 * of the elements. These positions are called "indices". This interface provides
 * guaranteed support for the usual <em>add</em>, <em>delete</em>, <em>get</em> and
 * <em>contains</em> operations. This implementation supports adding {@code null}
 * values as elements.
 *
 * <p>
 * Additionally, it also provides guarantee for <em>adding-all</em>, <em>inserting</em>,
 * <em>inserting-all</em> and <em>get-index</em> operations. The <em>copy</em> and
 * <em>deepcopy</em> operations return {@link List} objects. The iteration happens
 * from in ascending order of indices.
 * </p>
 *
 * @param <E> The type of element in the list.
 * @author Aniket Kumar
 */
public interface List<E> extends Collection<E> {

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * Is the list equal to the given object {@code obj}?
   * A list will be deemed equal to {@code obj} if {@code obj} satisfies the following
   * conditions:
   * <ol>
   *   <li>{@code obj} is a {@link List} object or one of its subclasses.</li>
   *   <li>
   *     All the elements of {@code obj} are equal to the corresponding elements in this
   *     list.
   *   </li>
   * </ol>
   *
   * <p>
   *   Because of Java's use of erasure, it is currently not possible to differentiate
   *   between the type of elements in two empty lists. This implies that if two
   *   empty {@link List} objects - which store two different types of elements, are
   *   compared with {@code equals()}, then the result will always be {@code true}.
   * </p>
   *
   * @param obj The other Object to compare {@code this} with for equality.
   * @return {@code true} if {@code obj} is equal to this list,
   *     {@code false} otherwise.
   */
  @Override
  boolean equals(Object obj);

  /**
   * Get a string representation of the list.
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
   * Get an iterator that produces the elements of the list in increasing order of
   * their indices.
   *
   * @return An iterator.
   */
  @Override
  Iterator<E> iterator();

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * How many elements are present in the list?
   *
   * @return The count of elements present in the list.
   */
  @Override
  int size();

  /**
   * Is the list empty?
   *
   * @return {@code true} if list is empty, {@code false} otherwise.
   */
  @Override
  boolean isEmpty();

  /**
   * Does {@code elmt} exist in the list?
   *
   * @param elmt The element to check existence of.
   * @return {@code true} if {@code elmt} exists, {@code false} otherwise.
   */
  @Override
  boolean contains(E elmt);

  /**
   * Clear the list of all its elements.
   * Sets all internal state members to their <em>default</em> initial state.
   */
  @Override
  void clear();

  /**
   * Return a shallow copy of the list.
   * A shallow copy creates a copy of the list but not the elements in the
   * list.
   *
   * @return A shallow copy of the list.
   *
   * @see #deepcopy(Function copyFn)
   */
  @Override
  List<E> copy();

  /**
   * Returns a deepcopy of the list.
   * A deepcopy creates a copy of the list and populates it with copies of the
   * original elements.
   *
   * @param copyFn A {@link Function} that takes original element as the
   *               argument and returns a deepcopy of that element.
   * @return A deepcopy of the list.
   *
   * @throws IllegalArgumentException If {@code copyFn} is {@code null}.
   * @see #copy()
   */
  @Override
  List<E> deepcopy(Function<? super E, E> copyFn);

  /* **************************************************************************
   * Section: List Methods
   ************************************************************************** */

  /**
   * Add {@code elmt} to the end of the list.
   *
   * @param elmt The element.
   * @see #insert(int atIndex, E elmt)
   */
  void add(E elmt);

  /**
   * Add all the elements in {@code elmts} array to the end of the list.
   *
   * @param elmts The array with elements to add.
   * @throws IllegalArgumentException If {@code elmts} is {@code null}.
   * @see #insertAll(int fromIndex, E[] elmts)
   */
  void addAll(E[] elmts);

  /**
   * Add all the elements produced by {@code elmts}, to the end of the
   * list.
   *
   * @param elmts The iterable that produces elements to add.
   * @throws IllegalArgumentException If {@code elmts}, or any of the elements it
   *                                  produces, is {@code null}.
   * @see #insertAll(int fromIndex, Iterable elmts)
   */
  void addAll(Iterable<? extends E> elmts);

  /**
   * Insert the given element {@code elmt} at the index {@code atIndex}.
   *
   * @param elmt    The element.
   * @param atIndex The index to insert at.
   * @throws IndexOutOfBoundsException If {@code atIndex} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   * @see #add(E elmt)
   */
  void insert(int atIndex, E elmt);

  /**
   * Insert all the elements in {@code elmts} into the list, starting from
   * the index {@code fromIndex}.
   *
   * @param elmts     The array of elements to insert.
   * @param fromIndex The index at which the first element from {@code elmts}
   *                  should appear.
   * @throws IllegalArgumentException  If {@code elmts} is {@code null}.
   * @throws IndexOutOfBoundsException If {@code fromIndex} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   * @see #addAll(E[] elmts)
   */
  void insertAll(int fromIndex, E[] elmts);

  /**
   * Insert all the elements produced by {@code elmts} into the list, starting
   * from the index {#code fromIndex}.
   *
   * @param elmts     The iterable that produces elements to insert.
   * @param fromIndex The index at which the first element produced by {@code elmts}
   *                  should appear.
   * @throws IllegalArgumentException  If {@code elmts} is {@code null}.
   * @throws IndexOutOfBoundsException If {@code fromIndex} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   * @see #addAll(Iterable elmts)
   */
  void insertAll(int fromIndex, Iterable<? extends E> elmts);

  /**
   * Get element from the list, whose index is {@code index}.
   *
   * @param index The index.
   * @return The element at {@code index}.
   *
   * @throws IndexOutOfBoundsException If {@code index} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   */
  E get(int index);

  /**
   * Set {@code elmt} at index {@code index}.
   * The old value stored at the given index is discarded.
   *
   * @param index The index to change value at.
   * @param elmt  The new element.
   * @throws IndexOutOfBoundsException If {@code index} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   */
  void set(int index, E elmt);

  /**
   * Get the index of the first occurrence of {@code elmt} in the list.
   *
   * @param elmt The element to look for.
   * @return The index of first occurrence of {@code elmt} in the list; -1 if
   *     {@code elmt} does not exist in the list.
   */
  int indexOf(E elmt);

  /**
   * Get the index of the first occurrence of {@code elmt} between indices {@code fromIndex}
   * and {@code toIndex}.
   * If {@code fromIndex} happens to be larger than or equal to {@code toIndex}, then -1
   * is returned.
   *
   * @param elmt      The element to look for.
   * @param fromIndex The index to start looking at (inclusive).
   * @param toIndex   The index to look upto (exclusive).
   * @return The index of the first occurrence of {@code elmt} in the range, {@literal -1}
   *     if {@code elmt} does not exist in the given range.
   *
   * @throws IndexOutOfBoundsException If either {@code fromIndex} or {@code toIndex} is
   *                                   not in range <code>[0, {@link #size()}]</code>.
   */
  int indexOf(E elmt, int fromIndex, int toIndex);

  /**
   * Delete the element at the given index in the list and return it.
   *
   * @param index The index to delete element at.
   * @return The deleted element.
   *
   * @throws IndexOutOfBoundsException If {@code index} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   * @see #remove(E elmt)
   */
  E delete(int index);

  /**
   * Remove the first occurrence of the element {@code elmt} in the list and return its
   * index.
   *
   * @param elmt The element to remove.
   * @return The index of the now removed element; -1 if {@code elmt} did not exist in
   *     the list.
   *
   * @see #delete(int index)
   */
  int remove(E elmt);
}
