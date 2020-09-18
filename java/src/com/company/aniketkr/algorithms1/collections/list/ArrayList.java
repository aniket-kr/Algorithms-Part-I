package com.company.aniketkr.algorithms1.collections.list;


import java.util.Iterator;

/**
 * Implements the {@link List} interface using an internal resizing array.
 * The internal array starts off with initial capacity {@value INIT_CAPACITY} if
 * nothing is explicitly specified.
 *
 * <p>This array is guaranteed to be between 25% to 100% full at all times. The
 * size of the array increases (or decreases) by a factor of 2.</p>
 *
 * @param <E> The type of the elements in the list.
 * @author Aniket Kumar
 */
public final class ArrayList<E> implements List<E> {
  private static final int INIT_CAPACITY = 4;

  public ArrayList() {
    this(INIT_CAPACITY);
  }

  public ArrayList(int capacity) {
  }

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * Check if this list is equal to the given object.
   * Calls {@link Object#equals(Object that) equals()} on the elements to check if they
   * are equal.
   *
   * @param obj The other object to check for equality.
   * @return {@code true} if {@code obj} is semantically equal to this list,
   *     {@code false} otherwise.
   * @implSpec As it currently stands, if two list objects are empty, the {@code equals}
   *     method will always return {@code true}. This happens as no elements are available
   *     to compare.
   */
  @Override
  public boolean equals(Object obj) {
    return false;
  }

  /**
   * Return a string representation of the list. Primarily for debugging.
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
   * Get an iterator object that produces the elements of the list in its natural
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
   * How many elements are present in the list?
   *
   * @return The count of number of elements in the list.
   */
  @Override
  public int size() {
    return 0;
  }

  /**
   * Is the list empty?
   *
   * @return {@code true} if list is empty, {@code false} otherwise.
   */
  @Override
  public boolean isEmpty() {
    return false;
  }

  /**
   * Clear the list of all its elements. Set it to its instantiated state.
   */
  @Override
  public void clear() {

  }

  /**
   * Get a shallow copy of the list.
   *
   * @return A new list.
   */
  @Override
  public List<E> copy() {
    return null;
  }

  /* **************************************************************************
   * Section: List Methods
   ************************************************************************** */

  /**
   * Does the element {@code elmt} exist in the list?
   *
   * @param elmt The element.
   * @return {@code true} if {@code elmt} exists in the list, {@code false} otherwise.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  @Override
  public boolean contains(E elmt) {
    return false;
  }

  /**
   * Add the given element {@code elmt} to the end of the list.
   *
   * @param elmt The element.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  @Override
  public void add(E elmt) {

  }

  /**
   * Insert the given element {@code elmt} at the index {@code atIndex}.
   *
   * @param elmt    The element.
   * @param atIndex The index to insert at.
   * @throws IllegalArgumentException  If {@code elmt} is {@code null}.
   * @throws IndexOutOfBoundsException If {@code atIndex} is not in range <code>
   *                                   [0, {@link #size()}]</code>.
   */
  @Override
  public void add(E elmt, int atIndex) {

  }

  /**
   * Add all the elements in the array {@code elmts} to the end of the list.
   *
   * @param elmts The array of elements to add.
   * @throws IllegalArgumentException If either {@code elmts} or any of its elements is
   *                                  {#code null}.
   */
  @Override
  public void addAll(E[] elmts) {

  }

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
  @Override
  public void addAll(E[] elmts, int fromIndex) {

  }

  /**
   * Add all the elements produced by the iterable {@code elmts}, to the end of the
   * list.
   *
   * @param elmts The iterable that produces elements to add.
   * @throws IllegalArgumentException If {@code elmts}, or any of the elements it
   *                                  produces, is {@code null}.
   */
  @Override
  public void addAll(Iterable<? extends E> elmts) {

  }

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
  @Override
  public void addAll(Iterable<? extends E> elmts, int fromIndex) {

  }

  /**
   * Get the element at the index {@code index} in the list.
   *
   * @param index The index.
   * @return The element at {@code index}.
   * @throws IndexOutOfBoundsException If {@code index} is not in range <code>
   *                                   [0, {@link #size()}]</code>.
   */
  @Override
  public E get(int index) {
    return null;
  }

  /**
   * Get the index of the first occurrence of {@code elmt} in the list. Uses elements'
   * {@link Object#equals(Object obj) equals} implementation to check if {@code elmt}
   * is equals to any of them.
   *
   * @param elmt The element to look for.
   * @return The index of first occurrence of {@code elmt} in the list; {@literal -1}
   *     if {@code elmt} does not exist in the list.
   */
  @Override
  public int indexOf(E elmt) {
    return 0;
  }

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
  @Override
  public int indexOf(E elmt, int fromIndex, int toIndex) {
    return 0;
  }

  /**
   * Delete the element at the given index in the list and return it.
   *
   * @param index The index to delete element at.
   * @return The deleted element.
   * @throws IndexOutOfBoundsException If {@code index} is not in range <code>
   *                                   [0, {@link #size()}</code>.
   */
  @Override
  public E delete(int index) {
    return null;
  }

  /**
   * Remove the first occurrence of the element {@code elmt} in the list and return its
   * index.
   *
   * @param elmt The element to remove.
   * @return The index of the now removed element; {@literal -1} if {@code elmt} did not
   *     exist in the list.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  @Override
  public int remove(E elmt) {
    return 0;
  }
}
