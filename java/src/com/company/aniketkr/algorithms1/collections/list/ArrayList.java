package com.company.aniketkr.algorithms1.collections.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;


/**
 * Implements the {@link List} interface using an internal resizing array.
 * The internal array starts off with initial capacity {@value INIT_CAPACITY} if
 * nothing is explicitly specified. This array is guaranteed to be between 25% to
 * 100% full at all times. The size of the array increases (or decreases) by a factor
 * of 2.
 *
 * <p>
 * The operations to <em>add</em> and <em>delete</em> take constant amortized time. Their
 * extensions - <em>add-all</em>, <em>insert</em>, <em>insert-all</em>, <em>remove</em>
 * take time proportional to <code>&theta;(n)</code>. The <em>contains</em>, <em>equals</em>,
 * <em>copy</em> and <em>deepcopy</em> operations take <code>&theta;(n)</code> time. The
 * <em>get</em> and <em>set</em> operations take constant time. All other operations take
 * constant time.
 * </p>
 *
 * <p>
 * Apart from all the operations defined in {@link List}, this implementation defines a
 * powerful <em>range</em> operation for iteration, alongside the {@code iterator} method
 * from {@link Iterable} interface. The <em>range</em> operation puts to use the fact that
 * the underlying structure for storage is an array, and allows flexible and versatile
 * iteration.
 * </p>
 *
 * @param <E> The type of the element in the list.
 * @author Aniket Kumar
 */
public final class ArrayList<E> implements List<E> {
  private static final int INIT_CAPACITY = 8;   // default capacity of internal array
  private E[] arr;                              // array that holds the list
  private int length = 0;                       // the length of the list

  /**
   * Initialize and return an empty ArrayList object.
   * The default capacity is {@value INIT_CAPACITY}.
   */
  public ArrayList() {
    this(INIT_CAPACITY);
  }

  /**
   * Initialize and return an empty ArrayList object, with capacity for
   * {@code capacity} elements.
   *
   * @param capacity The number of elements the list should be able to hold
   *                 before having to resize.
   * @throws IllegalArgumentException If {@code capacity} is less than or equal to 0.
   */
  @SuppressWarnings("unchecked")
  public ArrayList(int capacity) {
    arr = (E[]) new Object[capacity];
  }

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * {@inheritDoc}
   *
   * @param obj The other Object to compare {@code this} with for equality.
   * @return {@code true} if {@code obj} is equal to this list,
   *     {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)                return true;
    if (obj == null)                return false;
    if (!(obj instanceof List))     return false;
    List<?> that = (List<?>) obj;
    if (this.size() != that.size()) return false;

    // compare all elements
    Iterator<?> itor = that.iterator();
    for (E elmt : this) {
      if (!Objects.equals(elmt, itor.next())) {
        return false;
      }
    }
    return true;
  }

  /**
   * Get a string representation of the list.
   * Primarily for debugging and helping find bugs in client code.
   *
   * @return A string.
   */
  @Override
  @SuppressWarnings("DuplicatedCode")
  public String toString() {
    String className = this.getClass().getSimpleName();

    if (isEmpty()) return className + "[0] [ ]";

    StringBuilder sb = new StringBuilder(className).append("[").append(size()).append("] [ ");
    for (E elmt : this) {
      sb.append(elmt).append(", ");
    }
    sb.setLength(sb.length() - 2);
    return sb.append(" ]").toString();
  }

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
  public Iterator<E> iterator() {
    return new StepArrayIterator(0, size(), 1);
  }

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * How many elements are present in the list?
   *
   * @return The count of elements present in the list.
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
    return size() == 0;
  }

  /**
   * Does {@code elmt} exist in the list?
   *
   * @param elmt The element to check existence of.
   * @return {@code true} if {@code elmt} exists, {@code false} otherwise.
   */
  @Override
  public boolean contains(E elmt) {
    return indexOf(elmt) != -1;
  }

  /**
   * Clear the list of all its elements.
   * Sets all internal state members to their <em>default</em> initial state.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void clear() {
    arr = (E[]) new Object[INIT_CAPACITY];
    length = 0;
  }

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
  public List<E> copy() {
    ArrayList<E> cp = new ArrayList<>((this.size() >= 2) ? this.size() * 2 : INIT_CAPACITY);
    System.arraycopy(this.arr, 0, cp.arr, 0, this.size());
    cp.length = this.length;

    return cp;
  }

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
  public List<E> deepcopy(Function<? super E, E> copyFn) {
    if (copyFn == null) throw new IllegalArgumentException("argument to deepcopy() is null");

    ArrayList<E> cp = new ArrayList<>((this.size() >= 2) ? this.size() * 2 : INIT_CAPACITY);
    for (E elmt : this) {
      cp.add(copyFn.apply(elmt));
    }

    return cp;
  }

  /* **************************************************************************
   * Section: List Methods
   ************************************************************************** */

  /**
   * Add {@code elmt} to the end of the list.
   *
   * @param elmt The element.
   * @see #insert(int atIndex, E elmt)
   */
  @Override
  public void add(E elmt) {
    if (size() == arr.length) {
      resize(arr.length * 2);
    }

    arr[length++] = elmt;
  }

  /**
   * Add all the elements in {@code elmts} array to the end of the list.
   *
   * @param elmts The array with elements to add.
   * @throws IllegalArgumentException If {@code elmts} is {@code null}.
   * @see #insertAll(int fromIndex, E[] elmts)
   */
  @Override
  public void addAll(E[] elmts) {
    if (elmts == null) throw new IllegalArgumentException("argument to addAll() is null");
    if (arr.length - size() < elmts.length) {
      resize(elmts.length + arr.length);
    }

    System.arraycopy(elmts, 0, arr, length, elmts.length);
    length += elmts.length;
  }

  /**
   * Add all the elements produced by {@code elmts}, to the end of the
   * list.
   *
   * @param elmts The iterable that produces elements to add.
   * @throws IllegalArgumentException If {@code elmts}, or any of the elements it
   *                                  produces, is {@code null}.
   * @see #insertAll(int fromIndex, Iterable elmts)
   */
  @Override
  public void addAll(Iterable<? extends E> elmts) {
    if (elmts == null) throw new IllegalArgumentException("argument to addAll() is null");

    for (E elmt : elmts) {
      add(elmt);
    }
  }

  /**
   * Insert the given element {@code elmt} at the index {@code atIndex}.
   *
   * @param atIndex The index to insert at.
   * @param elmt    The element.
   * @throws IndexOutOfBoundsException If {@code atIndex} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   * @see #add(E elmt)
   */
  @Override
  public void insert(int atIndex, E elmt) {
    if (!isInRange(atIndex)) throw new IllegalArgumentException("invalid index: " + atIndex);
    if (size() == arr.length) {
      resize(arr.length * 2);
    }

    shift(atIndex, 1);
    arr[atIndex] = elmt;
    length++;
  }

  /**
   * Insert all the elements in {@code elmts} into the list, starting from
   * the index {@code fromIndex}.
   *
   * @param fromIndex The index at which the first element from {@code elmts}
   *                  should appear.
   * @param elmts     The array of elements to insert.
   * @throws IllegalArgumentException  If {@code elmts} is {@code null}.
   * @throws IndexOutOfBoundsException If {@code fromIndex} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   * @see #addAll(E[] elmts)
   */
  @Override
  public void insertAll(int fromIndex, E[] elmts) {
    if (!isInRange(fromIndex)) throw new IndexOutOfBoundsException("invalid index: " + fromIndex);
    if (elmts == null) throw new IllegalArgumentException("2nd argument to insertAll() is null");

    // make space, if needed
    int expectedSize = calcCapacity(elmts.length);
    if (expectedSize > arr.length) {
      resize(expectedSize);
    }

    shift(fromIndex, elmts.length);
    System.arraycopy(elmts, 0, arr, fromIndex, elmts.length);
    length += elmts.length;
  }

  /**
   * Insert all the elements produced by {@code elmts} into the list, starting
   * from the index {#code fromIndex}.
   *
   * @param fromIndex The index at which the first element produced by {@code elmts}
   *                  should appear.
   * @param elmts     The iterable that produces elements to insert.
   * @throws IllegalArgumentException  If {@code elmts} is {@code null}.
   * @throws IndexOutOfBoundsException If {@code fromIndex} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   * @see #addAll(Iterable elmts)
   */
  @Override
  public void insertAll(int fromIndex, Iterable<? extends E> elmts) {
    if (!isInRange(fromIndex)) throw new IndexOutOfBoundsException("invalid index: " + fromIndex);
    if (elmts == null) throw new IllegalArgumentException("2nd argument to insertAll() is null");

    ArrayList<E> aux = new ArrayList<>();
    aux.addAll(elmts);

    /* NOTE:
         The following section is basically code for insertAll() with an array.
         The only difference is that the end of the array does not define the
         end of the elements to insert, the `size()` property of the `aux`
         auxiliary ArrayList object is.
     */
    int expectedSize = calcCapacity(aux.size());
    if (expectedSize > this.arr.length) {
      resize(expectedSize);
    }

    shift(fromIndex, aux.size());
    System.arraycopy(aux.arr, 0, this.arr, fromIndex, aux.size());
    length += aux.size();
  }

  /**
   * Get element from the list, whose index is {@code index}.
   *
   * @param index The index.
   * @return The element at {@code index}.
   *
   * @throws IndexOutOfBoundsException If {@code index} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   */
  @Override
  public E get(int index) {
    if (!isInRange(index)) throw new IndexOutOfBoundsException("invalid index: " + index);

    return arr[index];
  }

  /**
   * Set {@code elmt} at index {@code index}.
   * The old value stored at the given index is discarded.
   *
   * @param index The index to change value at.
   * @param elmt  The new element.
   * @throws IndexOutOfBoundsException If {@code index} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   */
  @Override
  public void set(int index, E elmt) {
    if (!isInRange(index)) throw new IndexOutOfBoundsException("invalid index: " + index);

    arr[index] = elmt;
  }

  /**
   * Get the index of the first occurrence of {@code elmt} in the list.
   *
   * @param elmt The element to look for.
   * @return The index of first occurrence of {@code elmt} in the list; -1 if
   *     {@code elmt} does not exist in the list.
   */
  @Override
  public int indexOf(E elmt) {
    return indexOf(elmt, 0, size());
  }

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
  @Override
  public int indexOf(E elmt, int fromIndex, int toIndex) {
    if (!isInRange(fromIndex)) throw new IndexOutOfBoundsException("invalid from: " + fromIndex);
    if (!(isInRange(toIndex) || toIndex == size())) {
      throw new IndexOutOfBoundsException("invalid to: " + toIndex);
    }

    for (int i = fromIndex; i < toIndex; i++) {
      if (Objects.equals(elmt, arr[i])) {
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
   *
   * @throws IndexOutOfBoundsException If {@code index} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   * @see #remove(E elmt)
   */
  @Override
  public E delete(int index) {
    if (size() == arr.length / 4) {
      resize(arr.length / 2);
    }

    E elmt = arr[index];
    shift(index, -1);
    arr[length--] = null;

    return elmt;
  }

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
  @Override
  public int remove(E elmt) {
    int i = indexOf(elmt);

    if (i != -1) {
      shift(i, -1);
      arr[length--] = null;
    }
    return i;
  }

  /* **************************************************************************
   * Section: Class Defined Methods
   ************************************************************************** */

  /**
   * Return an iterable that iterates over all the elements from index 0 upto the index
   * {@code stop}.
   * Calls {@link #range(int start, int stop, int step) range overload} with
   * {@code start} equal to 0 and {@code step} equal to 1.
   *
   * @param stop The index to stop iterating at (exclusive).
   * @return An iterable.
   *
   * @throws IndexOutOfBoundsException If {@code stop} is not in range <code>
   *                                   [0, {@link #size()}]</code>.
   * @see #range(int start, int stop, int step)
   */
  public Iterable<E> range(int stop) {
    return range(0, stop, 1);
  }

  /**
   * Return an iterable that iterates over all elements in the list from {@code start}
   * (inclusive) to {@code stop} (exclusive).
   * Calls {@link #range(int start, int stop, int step) range overload} with {@code step}
   * equal to 1.
   *
   * @param start The index to start iterating from (inclusive).
   * @param stop  The index to stop iterating at (exclusive).
   * @return An iterable.
   *
   * @throws IndexOutOfBoundsException If {@code start} is not in range
   *                                   <code>[0, {@link #size()})</code> or if {@code stop}
   *                                   is not in range <code>[0, {@link #size()}]</code>
   * @see #range(int start, int stop, int step)
   */
  public Iterable<E> range(int start, int stop) {
    return range(start, stop, 1);
  }

  /**
   * Get an iterable that iterates over elements starting from index {@code start} upto,
   * but not including, index {@code stop}. The iterable returned will iterate over every
   * {@code step}th element in the range specified.
   *
   * <p>
   * As long as the constrains of the parameters are followed by the arguments, {@code range()}
   * <b>NEVER</b> throws any exception. Example uses:
   * <hr>
   * {@code   $ ArrayList<Integer> lst = new ArrayList<>()                } <br>
   * {@code   $ lst.addAll(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9})   } <br>
   * {@code   $ lst                                                       } <br>
   * {@code   > ArrayList[10] [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ]            } <br>
   * {@code   $ lst.range(0, 5, 2)                                        } <br>
   * {@code   > 0, 2, 4                                                   } <br>
   * {@code   $ lst.range(1, 8, 3)                                        } <br>
   * {@code   > 1, 4, 7                                                   } <br>
   * {@code   $ lst.range(9, -1, -2)                                      } <br>
   * {@code   $ 9, 7, 5, 3, 1                                             }
   * <hr>
   * </p>
   *
   * @param start The index to start iterating from (inclusive).
   * @param stop  The index to stop iterating at or over (exclusive). Can take value -1 or
   *              {@link #size()} depending on the value of {@code step}.
   * @param step  Land at every {@code step}th element starting from {@code start}. Note
   *              that step can have a negative value, in which case it iterates from
   *              {@code start} to {@code stop} in reverse order.
   * @return An iterable.
   *
   * @throws IllegalArgumentException  If {@code step} is 0. Will be stuck forever iterating
   *                                   a single element. Not Allowed!
   * @throws IndexOutOfBoundsException If {@code start} is not in range
   *                                   <code>[0, {@link #size()})</code> or if {@code stop}
   *                                   is neither in the same range nor it is any of -1 or
   *                                   {@link #size()} (depending on value on {@code step}).
   */
  public Iterable<E> range(int start, int stop, int step) {
    if (step == 0) throw new IllegalArgumentException("invalid step (= 0)");
    if (!isInRange(start)) throw new IndexOutOfBoundsException("invalid start: " + start);

    if (step > 0) {
      if (!(isInRange(stop) || stop == size())) {
        throw new IndexOutOfBoundsException("invalid stop: " + stop);
      }

      return () -> new StepArrayIterator(start, stop, step);

    } else {

      if (!(isInRange(stop) || stop == -1)) {
        throw new IndexOutOfBoundsException("invalid stop: " + stop);
      }

      return () -> new StepArrayIterator(stop, start, step);
    }
  }

  /* **************************************************************************
   * Section: Helper Classes and Methods
   ************************************************************************** */

  /**
   * Resize the internal array to {@code newSize}.
   * Resizing is achieved by copying the elements from the original array over to the
   * newly created array with capacity {@code newSize}.
   *
   * @param newSize The desired capacity of the new array.
   */
  @SuppressWarnings("unchecked")
  private void resize(int newSize) {
    E[] newArr = (E[]) new Object[newSize];
    System.arraycopy(arr, 0, newArr, 0, size());
    arr = newArr;
  }

  /**
   * Shift the elements in the internal array over by {@code delta} positions, starting
   * from index {@code fromIndex}.
   * <p>
   * <b>Notes:</b>
   * <ol>
   *   <li>
   *     It is assumed that the internal array has the required capacity to shift over the
   *     elements, in case of positive value of {@code delta}.
   *   </li>
   *   <li>The method <b>DOES NOT</b> mutate the value of field {@link #length}.</li>
   * </ol>
   * </p>
   *
   * @param fromIndex The index to start shifting elements from (inclusive).
   * @param delta     The number of positions all elements starting from index {@code fromIndex}
   *                  should be shifted to the <b>right</b> by. If this value is negative, all
   *                  elements starting {@code fromIndex} are shifted to the <b>left</b>.
   */
  private void shift(int fromIndex, int delta) {
    // assume there is always enough space in the array
    System.arraycopy(arr, fromIndex, arr, fromIndex + delta, size() - fromIndex);
  }

  /**
   * Simulates the process of "doubling array size" to determine the final size at which
   * all the elements will fit in the internal array.
   *
   * @param delta The number of new elements being added.
   * @return The new size of the array, if the current size is sufficient then it is
   *     returned.
   */
  private int calcCapacity(int delta) {
    // `posRequired`  -> total positions required
    // `posAvailable` -> total positions available
    int posRequired = delta + size();
    int posAvailable = arr.length;

    while (posAvailable < posRequired) {
      posAvailable *= 2;
    }
    return posAvailable;
  }

  /**
   * Is the index {@code i} in the range <code>[0, {@link #size()})</code>?
   *
   * @param i The index to verify.
   * @return {@code true} if {@code i} is in range, {@code false} otherwise.
   */
  private boolean isInRange(int i) {
    return i >= 0 && i < size();
  }

  /**
   * An {@link Iterator} class that iterates over the elements of an array based on given
   * <em>start</em> and <em>stop</em> indices. The <em>step-size</em> can also be configured.
   */
  private class StepArrayIterator implements Iterator<E> {
    private final int stop;     // stop at this index (exclusive)
    private final int step;     // iterate over every `step`-th index
    private int current;        // the current position of iterator

    /**
     * Initialize and return a new StepArrayIterator object that iterates over every
     * {@code step} elements in the {@link #arr}, between {@code low} and {@code high}.
     *
     * <p>
     * If {@code step} is greater than 0, then iterates from {@code low} (inclusive) to
     * {@code high} (exclusive). For {@code step} less than 0, iteration happens from
     * {@code high} (inclusive) to {@code low} (exclusive).
     * </p>
     *
     * @param low  The lower of the two indices.
     * @param high The higher of the two indices.
     * @param step Iterate over every {@code step} element in the array.
     * @throws IllegalArgumentException If {@code step} is 0.
     */
    private StepArrayIterator(int low, int high, int step) {
      this.step = step;

      if (step < 0) {
        current = high;
        stop = low;
        return;
      } else if (step > 0) {
        current = low;
        stop = high;
        return;
      }

      throw new IllegalArgumentException("invalid step (= 0)");
    }

    /**
     * Can the iterator produce another value?
     *
     * @return {@code false} if the iterator has been depleted, {@code true} otherwise.
     */
    @Override
    public boolean hasNext() {
      return (step > 0) ? (current < stop) : (current > stop);
    }

    /**
     * Produce the next value from the iterator and return it.
     *
     * @return The next value.
     *
     * @throws NoSuchElementException If called on a depleted iterator.
     */
    @Override
    public E next() {
      if (!hasNext()) throw new NoSuchElementException("iterator depleted");

      E elmt = arr[current];
      current += step;
      return elmt;
    }

    /**
     * Remove not supported. Throws UOE.
     *
     * @throws UnsupportedOperationException Always.
     */
    @Override
    public void remove() {
      throw new UnsupportedOperationException("remove() not supported");
    }
  }
}
