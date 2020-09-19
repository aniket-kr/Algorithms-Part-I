package com.company.aniketkr.algorithms1.collections.list;


import java.util.Iterator;
import java.util.NoSuchElementException;

// FIXME: docs under construction

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
  private static final int INIT_CAPACITY = 8;
  private E[] arr;
  private int length = 0;

  /**
   * Initialize and return a new ArrayList object. Default
   * capacity is {@value INIT_CAPACITY}.
   */
  public ArrayList() {
    this(INIT_CAPACITY);
  }

  /**
   * Initialize and return a new ArrayList object, ensuring capacity for
   * {@code capacity} objects.
   *
   * @param capacity The number of elements that internal array should be able to
   *                 accommodate without needing to resize.
   * @throws NegativeArraySizeException If {@code capacity} is negative.
   */
  @SuppressWarnings("unchecked")
  public ArrayList(int capacity) {
    arr = (E[]) new Object[capacity];
  }

  /**
   * Initialize and return a new ArrayList object and add all the elements of
   * {@code array} to the list.
   *
   * @param array The array whose elements will be added to the list.
   */
  public ArrayList(Object[] array) {
    // TODO: Implement this after deciding what to do with null values
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
    if (this == obj)                return true;
    if (obj == null)                return false;
    if (!(obj instanceof List))     return false;
    List<?> that = (List<?>) obj;
    if (this.size() != that.size()) return false;

    // check all elements
    Iterator<E> itor1 = this.iterator();
    Iterator<?> itor2 = that.iterator();
    while (itor1.hasNext()) {
      if (!itor1.next().equals(itor2.next())) {
        return false;
      }
    }
    return true;
  }

  /**
   * Return a string representation of the list. Primarily for debugging.
   *
   * @return A string.
   */
  public String toString() {
    if (isEmpty()) return "[ ]";

    StringBuilder sb = new StringBuilder("[ ");
    for (E elmt : this) {
      sb.append(elmt);
      sb.append(", ");
    }
    sb.setLength(sb.length() - 2);
    sb.append(" ]");
    return sb.toString();
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
    return new StepArrayIterator(0, size(), 1);
  }

  public Iterable<E> range(int from, int to, int step) {
    if (step == 0) throw new IllegalArgumentException("step size cannot be 0");

    if (step > 0 && inRange(from) && inRange(to, size())) {
      return () -> new StepArrayIterator(from, to, step);
    } else if (step < 0 && inRange(from) && inRange(to, -1)) {
      // FIXME: range() doesn't work for negative "step"
      return () -> new StepArrayIterator(to, from, step);
    }

    throw new IndexOutOfBoundsException(String.format("invalid index pair: %d, %d", from, to));
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
    return length;
  }

  /**
   * Is the list empty?
   *
   * @return {@code true} if list is empty, {@code false} otherwise.
   */
  @Override
  public boolean isEmpty() {
    return length == 0;
  }


  /**
   * Does the element {@code elmt} exist in the list?
   *
   * @param elmt The element.
   * @return {@code true} if {@code elmt} exists in the list, {@code false} otherwise.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  @Override
  public boolean contains(E elmt) {
    if (elmt == null) throw new IllegalArgumentException("argument to contains is null");

    return indexOf(elmt) != -1;
  }

  /**
   * Clear the list of all its elements. Set it to its instantiated state.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void clear() {
    arr = (E[]) new Object[INIT_CAPACITY];
    length = 0;
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
   * Add the given element {@code elmt} to the end of the list.
   *
   * @param elmt The element.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  @Override
  public void add(E elmt) {
    if (elmt == null) throw new IllegalArgumentException("argument to add() is null");
    if (size() == arr.length) {
      resize(arr.length * 2);
    }

    arr[length++] = elmt;
  }

  /**
   * Insert the given element {@code elmt} at the index {@code atIndex}.
   *
   * @param elmt    The element.
   * @param atIndex The index to insert at.
   * @throws IllegalArgumentException  If {@code elmt} is {@code null}.
   * @throws IndexOutOfBoundsException If {@code atIndex} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   */
  @Override
  public void add(E elmt, int atIndex) {
    if (elmt == null) throw new IllegalArgumentException("1st argument to add() is null");
    if (!inRange(atIndex)) throw new IndexOutOfBoundsException("invalid index: " + atIndex);
    if (size() == arr.length) {
      resize(arr.length * 2);
    }

    shift(atIndex, 1);
    arr[atIndex] = elmt;
    length++;
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
    // TODO: Implement this after deciding what to do with null values
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
   *                                   [0, {@link #size()})</code>.
   */
  @Override
  public void addAll(E[] elmts, int fromIndex) {
    // TODO: Implement this after deciding what to do with null values
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
    // TODO: Implement this after deciding what to do with null values
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
   *                                   [0, {@link #size()})</code>.
   */
  @Override
  public void addAll(Iterable<? extends E> elmts, int fromIndex) {
    // TODO: Implement this after deciding what to do with null values
  }

  /**
   * Get the element at the index {@code index} in the list.
   *
   * @param index The index.
   * @return The element at {@code index}.
   * @throws IndexOutOfBoundsException If {@code index} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   */
  @Override
  public E get(int index) {
    if (!inRange(index)) throw new IndexOutOfBoundsException("invalid index: " + index);

    return arr[index];
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
    return indexOf(elmt, 0, size());
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
   *                                   not in range <code>[0, {@link #size()})</code>.
   * @implSpec If no elements fall in the range specified by {@code fromIndex} and
   *     {@code toIndex}, then {@literal -1} is returned.
   */
  @Override
  public int indexOf(E elmt, int fromIndex, int toIndex) {
    if (elmt == null) throw new IllegalArgumentException("argument to indexOf() is null");
    if (!inRange(fromIndex)) throw new IndexOutOfBoundsException("invalid fromIndex: " + fromIndex);
    if (!(inRange(toIndex) || toIndex == size())) {
      throw new IndexOutOfBoundsException("invalid toIndex: " + toIndex);
    }

    for (int i = fromIndex; i < toIndex; i++) {
      if (arr[i].equals(elmt)) {
        return i;
      }
    }
    return -1;
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
    if (!inRange(index)) throw new IndexOutOfBoundsException("invalid index: " + index);
    if (size() == arr.length / 4) {
      resize(arr.length / 2);
    }

    E elmt = get(index);
    shift(index + 1, -1);
    arr[--length] = null;

    return elmt;
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
    if (elmt == null) throw new IllegalArgumentException("argument to remove() is null");

    int i = indexOf(elmt);
    if (i >= 0) {
      delete(i);
    }
    return i;
  }

  /* **************************************************************************
   * Section: Helper Classes and Methods
   ************************************************************************** */

  /**
   * Is the index {@code i} in range <code>[0, {@link #size()})</code> w.r.t. the
   * current state of the list?
   *
   * @param i The index to verify.
   * @return {@code true} if {@code i} lies in range, {@code false} otherwise.
   */
  private boolean inRange(int i) {
    return i >= 0 && i < size();
  }

  /**
   * Is the index {@code i} in range <code>[0, {@link #size()}) &cup; set{include}</code>
   * w.r.t. the current state of the list?
   *
   * @param i The index to verify.
   * @return {@code true} if {@code i} lies in range, {@code false} otherwise.
   */
  private boolean inRange(int i, int include) {
    return inRange(i) || i == include;
  }

  /**
   * Resize the internal array to the given size and copy over all the elements.
   *
   * @param newSize The new size of the internal array.
   */
  @SuppressWarnings("unchecked")
  private void resize(int newSize) {
    E[] newArr = (E[]) new Object[newSize];
    System.arraycopy(arr, 0, newArr, 0, size());
    arr = newArr;
  }

  /**
   * Shift the elements of the internal array {@link #arr} over by {@code delta} indices
   * starting from the index {@code i}.
   *
   * @param i     The index to starting shifting elements from (inclusive).
   * @param delta The number of empty spaces to make starting index {@code i}.
   */
  private void shift(int i, int delta) {
    System.arraycopy(arr, i, arr, i + delta, size() - i);
  }

  private class StepArrayIterator implements Iterator<E> {
    private final int stop;
    private final int step;
    private int current;

    private StepArrayIterator(int start, int stop, int step) {
      this.current = start;
      this.stop = stop;
      this.step = step;
    }

    @Override
    public boolean hasNext() {
      return current < stop;
    }

    @Override
    public E next() {
      if (!hasNext()) throw new NoSuchElementException("iterator depleted");

      E elmt = arr[current];
      current += step;
      return elmt;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException("remove() not supported");
    }
  }
}
