package com.company.aniketkr.algorithms1.maps.symbol;

import com.company.aniketkr.algorithms1.maps.Map;
import com.company.aniketkr.algorithms1.maps.OrderMap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;


/**
 * Implements the {@link OrderMap} interface using an internal resizing array.
 * If nothing is explicitly specified, then the default capacity of the arrays
 * is {@value INIT_CAPACITY}. The size of the array increases (or decreases)
 * by a factor of 2.
 *
 * <p>
 * Most operations in the implementation take logarithmic or linear time; some
 * take constant time. <br>
 *
 * <b>Logarithmic Time</b> - <em>get</em>, <em>put</em> (amortized), <em>delete</em>,
 * <em>rank</em>, <em>get-the-floor</em> and <em>get-the-ceiling</em> operations take
 * time proportional to <code>&theta;(log<sub>2</sub>n)</code>. <br>
 *
 * <b>Linear Time</b> - <em>equals</em> (worst case), <em>copy</em>, <em>deepcopy</em>
 * and <em>delete-the-minimum</em> operations take time proportional to <code>&theta;(n)
 * </code>. <br>
 *
 * <b>Constant Time</b> - All other operations take constant time.
 * </p>
 *
 * <p>
 * The implementation provided 3 methods (4 operations incl. overloads) to iterate over
 * the keys and key-value pairs in the map. The <em>keys</em> and the <em>iterator</em>
 * methods iterate over the keys of the map, while the <em>items</em> method (& overload)
 * iterate over the key-value pairs in the map. Both iterate in increasing order of keys.
 * </p>
 *
 * @param <K> The type of key in the map.
 * @param <V> The type of value in the map.
 * @author Aniket Kumar
 */
public class ArrayOrderMap<K, V> implements OrderMap<K, V> {
  private static final int INIT_CAPACITY = 8;  // default capacity of internal arrays

  private final Comparator<K> comp;            // Comparator to use, if needed
  private K[] keys;                            // holds the keys
  private V[] vals;                            // holds the values
  private int length = 0;                      // number of key-value pairs

  /**
   * Initialize and return an empty ArrayOrderMap object and assumes that
   * the type {@link K Key} implements the {@link Comparable} interface.
   * The default capacity of the internal array is {@value INIT_CAPACITY}.
   */
  public ArrayOrderMap() {
    this(INIT_CAPACITY, null);
  }

  /**
   * Initialize and return an empty ArrayOrderMap object that has capacity
   * to hold {@code capacity} key-value pairs before needing to resize.
   * It is assumed that the {@link K Key} type implements the {@link Comparable}
   * interface.
   *
   * @param capacity The desired number of key-value pairs the map should be able
   *                 to hold without having to resize.
   * @throws IllegalArgumentException If {@code capacity} is less than or equal to
   *                                  {@code 0}.
   */
  public ArrayOrderMap(int capacity) {
    this(capacity, null);
  }

  /**
   * Initialize and return an empty ArrayOrderMap object.
   * The comparison of keys is done with the help of {@code comparator}, even if the type
   * does implement {@link Comparable} interface. If {@code comparator} is {@code null},
   * then an attempt to use the {@code Comparable} interface is made. The default
   * capacity of the map, {@value INIT_CAPACITY}, is used.
   *
   * @param comparator The {@link Comparator} object to use for comparison of keys.
   */
  public ArrayOrderMap(Comparator<K> comparator) {
    this(INIT_CAPACITY, comparator);
  }

  /**
   * Initialize and return an empty ArrayOrderMap object, that has a capacity to hold
   * {@code capacity} key-value pairs, before needing to resize.
   * The keys are compared using {@code comparator}. If {@code comparator} is {@code null},
   * then an attempt is made to use the {@link Comparable} interface.
   *
   * @param capacity   The desired number of key-value pairs the map should be able
   *                   to hold without having to resize.
   * @param comparator The {@link Comparator} object to use for comparison of keys.
   * @throws IllegalArgumentException If {@code capacity} is less than or equal to
   *                                  {@code 0}.
   */
  @SuppressWarnings("unchecked")
  public ArrayOrderMap(int capacity, Comparator<K> comparator) {
    if (capacity <= 0) throw new IllegalArgumentException("invalid capacity: " + capacity);

    comp = comparator;
    keys = (K[]) new Object[capacity];
    vals = (V[]) new Object[capacity];
  }

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * {@inheritDoc}
   *
   * @param obj The other Object to compare {@code this} with for equality.
   * @return {@code true} if {@code obj} is equal to this map,
   *     {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof Map)) return false;
    Map<?, ?> map = (Map<?, ?>) obj;
    if (this.size() != map.size()) return false;

    // compare all keys and values
    if (obj instanceof OrderMap) {
      return equalsForOrderMap((OrderMap<?, ?>) map);
    } else {
      return equalsForMap(map);
    }
  }

  /**
   * Get a string representation of key-value pairs in the map.
   * Primarily for debugging and helping find bugs in client code.
   *
   * @return A string.
   */
  @Override
  public String toString() {
    String className = this.getClass().getSimpleName();

    if (isEmpty()) return className + "[0] { }";

    StringBuilder sb = new StringBuilder(className).append("[").append(size()).append("] { ");
    for (KeyVal<K, V> kv : this.items()) {
      sb.append(kv.key()).append(": ").append(kv.val()).append(", ");
    }
    sb.setLength(sb.length() - 2);
    return sb.append(" }").toString();
  }

  /* **************************************************************************
   * Section: Iterable Methods
   ************************************************************************** */

  /**
   * Get an iterator that produces all the keys of the map in ascending order according
   * to the specific method of comparison being used.
   *
   * @return An iterator that produces keys in ascending order.
   */
  @Override
  public Iterator<K> iterator() {
    return new MapKeyIterator<>(keys, 0, length - 1);
  }

  /* **************************************************************************
   * Section: Map Methods
   ************************************************************************** */

  /**
   * How many key-value pairs does this map have?
   *
   * @return The count of key-value pairs in the map.
   */
  @Override
  public int size() {
    return length;
  }

  /**
   * Is the map empty?
   *
   * @return {@code true} if there are no key-value pairs in the map, {@code false}
   *     otherwise.
   */
  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Does {@code key} exist in the map?
   *
   * @param key The key to check existence of.
   * @return {@code true} if a there is a value associated with {@code key} in the map,
   *     {@code false} otherwise.
   *
   * @throws IllegalArgumentException If {@code key} is {@code null}.
   */
  @Override
  public boolean contains(K key) {
    if (key == null) throw new IllegalArgumentException("argument to contains() is null");

    return search(key) >= 0;
  }

  /**
   * Clear the map of all its key-value pairs.
   * Sets it to its <em>default</em> state.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void clear() {
    keys = (K[]) new Object[INIT_CAPACITY];
    vals = (V[]) new Object[INIT_CAPACITY];
    length = 0;
  }

  /**
   * Return a shallow copy of the map.
   * A shallow copy creates a copy of the map but not of the keys and values in
   * the map.
   *
   * @return A shallow copy of the map.
   *
   * @see #deepcopy(Function copyFn)
   */
  @Override
  public Map<K, V> copy() {
    ArrayOrderMap<K, V> cp = new ArrayOrderMap<>((size() >= 2) ? (size() * 2) : INIT_CAPACITY, comp);
    System.arraycopy(this.keys, 0, cp.keys, 0, this.size());
    System.arraycopy(this.vals, 0, cp.vals, 0, this.size());
    cp.length = this.length;

    return cp;
  }

  /**
   * Returns a deepcopy of the map.
   * A deepcopy creates a copy of the map and populates it with copies of the
   * original elements.
   *
   * @param copyFn A {@link Function} that takes original key-value pair as a {@link KeyVal}
   *               object as the argument and returns a deepcopy of the key and the val as a
   *               new {@code KeyVal} object.
   * @return A deepcopy of the map.
   *
   * @throws IllegalArgumentException If {@code copyFn} or any key in the returned {@code KeyVal}
   *                                  object is {@code null}.
   * @see #copy()
   */
  public ArrayOrderMap<K, V> deepcopy(Function<? super KeyVal<K, V>, KeyVal<K, V>> copyFn) {
    if (copyFn == null) throw new IllegalArgumentException("argument to deepcopy() is null");

    // add null check to `copyFn`
    copyFn = copyFn.andThen(kv -> {
      if (kv.key() == null) throw new IllegalArgumentException("copyFn returned KeyVal with null keys");
      return kv;
    });

    ArrayOrderMap<K, V> cp = new ArrayOrderMap<>((size() >= 2) ? (size() * 2) : INIT_CAPACITY, comp);
    KeyVal<? extends K, ? extends V> kvCopy;
    for (KeyVal<K, V> kv : this.items()) {
      kvCopy = copyFn.apply(kv);
      cp.put(kvCopy.key(), kvCopy.val());
    }

    return cp;
  }

  /**
   * Get the value associated with {@code key} from the map.
   *
   * @param key The key to get associated value of.
   * @return If {@code key} exists, then the value associated with {@code key}. If {@code key}
   *     does not exist, then an exception is thrown.
   *
   * @throws NoSuchElementException If {@code key} does not exist in the map.
   */
  @Override
  public V get(K key) {
    if (key == null) throw new IllegalArgumentException("argument to get() is null");

    int i = search(key);
    if (i >= 0) return vals[i];

    throw new NoSuchElementException("map has no key \"" + key + "\"");
  }

  /**
   * Get the value associated with {@code key} from the map.
   * If {@code key} doesn't exist in the map, then {@code fallback} value is returned.
   *
   * @param key      The key to return associated value of.
   * @param fallback The fallback value to return if {@code key} does not exist in
   *                 the map.
   * @return The value associated with {@code key} from the map, or {@code fallback}
   *     if {@code key} doesn't exist in the map.
   *
   * @throws IllegalArgumentException If {@code key} is {@code null}.
   */
  @Override
  public V get(K key, V fallback) {
    if (key == null) throw new IllegalArgumentException("argument to get() is null");

    int i = search(key);
    return (i >= 0) ? vals[i] : fallback;
  }

  /**
   * Put {@code key} in the map and associate the value {@code val} with it.
   * If {@code key} already exists in the map, then update the associated value to be
   * {@code val}; the old value is discarded.
   *
   * @param key The key to add/update in the map to have {@code val} as associated value.
   * @param val The value to associate with {@code key}.
   * @return {@code true} if a new key was added, {@code false} if {@code key} already existed
   *     in the map.
   *
   * @throws IllegalArgumentException If {@code key} is {@code null}.
   */
  @Override
  public boolean put(K key, V val) {
    if (key == null) throw new IllegalArgumentException("1st argument to set() is null");

    int i = rank(key);

    // if `key` already exists
    if (isInRange(i) && compare(keys[i], key) == 0) {
      vals[i] = val;
      return false;
    }

    // if `key` doesn't exist
    if (size() == keys.length) {
      resize(keys.length * 2);
    }

    shift(i, 1);
    keys[i] = key;
    vals[i] = val;
    length++;
    return true;
  }

  /**
   * Delete the given {@code key} and its associated value from the map.
   * If {@code key} does not exist in the map, then simply returns - no exception is thrown.
   *
   * @param key The key in the key-value pair to delete.
   * @return {@code true} if {@code key} existed in the map and was deleted, {@code false}
   *     otherwise.
   *
   * @throws IllegalArgumentException If {@code key} is {@code null}.
   */
  @Override
  public boolean del(K key) {
    if (key == null) throw new IllegalArgumentException("argument to del() is null");
    if (size() == keys.length / 4) {
      resize(keys.length / 2);
    }

    int i = search(key);
    boolean doesExist = i >= 0;
    if (doesExist) {
      shift(i + 1, -1);
      length--;
      keys[length] = null;
      vals[length] = null;
    }

    return doesExist;
  }

  /**
   * Get an iterable object that iterates over the key-value pairs packed together in an
   * immutable {@link KeyVal} object.
   *
   * @return An iterable.
   */
  @Override
  public Iterable<KeyVal<K, V>> items() {
    return () -> new MapKeyValIterator<>(keys, vals, 0, length - 1);
  }

  /* **************************************************************************
   * Section: OrderMap Methods
   ************************************************************************** */

  /**
   * Get the key with the lowest priority according to the specific method of comparison
   * being used.
   *
   * @return The key with minimum priority.
   *
   * @throws NoSuchElementException If called on an empty map.
   */
  @Override
  public K min() {
    if (isEmpty()) throw new NoSuchElementException("min() called on empty map");

    return keys[0];
  }

  /**
   * Get the key with the highest priority according to the specific method of comparison
   * being used.
   *
   * @return The key with maximum priority.
   *
   * @throws NoSuchElementException If called on an empty map.
   */
  @Override
  public K max() {
    if (isEmpty()) throw new NoSuchElementException("max() called on empty map");

    return keys[length - 1];
  }

  /**
   * Return the floor of key {@code k} using keys in the map.
   * In general, {@code floor(x)} is defined as a function that returns the largest member
   * smaller than or equal to {@code x}. If {@code k} happens to have the least priority
   * amongst all the keys in the map, then {@code null} is returned.
   *
   * @param k The key to compute floor of.
   * @return The floor of {@code k} or {@code null}.
   *
   * @throws IllegalArgumentException If {@code k} is {@code null}.
   * @see Math#floor(double a) Math package's floor method for details.
   */
  @Override
  public K floor(K k) {
    if (k == null) throw new IllegalArgumentException("argument to floor() is null");

    int i = floorIndex(k);
    return (i < 0) ? null : keys[i];
  }

  /**
   * Return the ceiling of key {@code k} using keys in the map.
   * In general, {@code ceiling(x)} is defined as a function that returns the smallest member
   * larger than or equal to {@code x}. If {@code k} happens to have the highest priority
   * amongst all the keys in the map, then {@code null} is returned.
   *
   * @param k The key to compute ceiling of.
   * @return The ceiling of {@code k} or {@code null}.
   *
   * @throws IllegalArgumentException If {@code k} is {@code null}.
   * @see Math#ceil(double a) Math package's ceil method for details.
   */
  @Override
  public K ceil(K k) {
    if (k == null) throw new IllegalArgumentException("argument to ceil() is null");

    int i = ceilIndex(k);
    return (i < 0) ? null : keys[i];
  }

  /**
   * Get the number of keys that have priority strictly less than {@code key} in
   * the map.
   * If {@code key} doesn't exist in the map, then treats it like it does and
   * returns the number of keys, that exist in the map, and have priority less than
   * {@code key}.
   *
   * @param key The key to find 'rank' of.
   * @return The number of keys with priority less than {@code key}.
   *
   * @throws IllegalArgumentException If {@code key} is {@code null}.
   * @see #select(int rank)
   */
  @Override
  public int rank(K key) {
    if (key == null) throw new IllegalArgumentException("argument to rank() is null");

    if (isEmpty()) return 0;

    int start = 0;
    int end = length - 1;

    int mid = end / 2;
    int cmp;
    while (start <= end) {
      mid = (int) (start / 2.0 + end / 2.0);
      cmp = compare(key, keys[mid]);
      if (cmp == 0) {
        return mid;
      } else if (cmp > 0) {
        start = mid + 1;
      } else {
        end = mid - 1;
      }
    }

    return (compare(key, keys[mid]) > 0) ? mid + 1 : mid;
  }

  /**
   * Get the key that has exactly {@code rank} number of keys with priority less than
   * itself.
   *
   * @param rank Key returned will be strictly larger than this number of keys in the map.
   * @return The key with rank {@code rank}.
   *
   * @throws IllegalArgumentException If {@code rank} is not in range
   *                                  <code>[0, {@link #size()})</code>.
   * @see #rank(K key)
   */
  @Override
  public K select(int rank) {
    if (isEmpty()) throw new IllegalArgumentException("can't select from empty map");
    if (!(isInRange(rank) || rank == size())) throw new IllegalArgumentException("no key with rank " + rank);

    return keys[rank];
  }

  /**
   * Get the {@code Comparator} object being user for comparing keys.
   *
   * @return The comparator being used to compare keys. If the natural order defined by
   *     the {@link Comparable} interface is being used then returns {@code null}.
   */
  @Override
  public Comparator<K> comparator() {
    return comp;
  }

  /**
   * Delete the key with the lowest priority in the map.
   *
   * @throws NoSuchElementException If called on an empty map.
   */
  @Override
  public void delMin() {
    if (isEmpty()) throw new NoSuchElementException("can't delete from empty map");

    shift(1, -1);
    length--;
    keys[length] = null;
    vals[length] = null;
  }

  /**
   * Delete the key with the highest priority in the map.
   *
   * @throws NoSuchElementException If called on an empty map.
   */
  @Override
  public void delMax() {
    if (isEmpty()) throw new NoSuchElementException("can't delete from empty map");

    length--;
    keys[length] = null;
    vals[length] = null;
  }

  /**
   * Get an iterable that iterates over all the keys (in increasing order of priority) whose
   * priority lies between the priorities of keys {@code low} and {@code high}, both
   * endpoints included.
   *
   * @param low  Iteration starts with the ceiling of this key.
   * @param high Iteration stops at the floor of this key.
   * @return An iterable.
   *
   * @throws IllegalArgumentException When either {@code low} or {@code high} is {@code null}.
   */
  @Override
  public Iterable<K> keys(K low, K high) {
    if (high == null || low == null) throw new IllegalArgumentException("keys can't be null");

    return () -> new MapKeyIterator<>(keys, ceilIndex(low), floorIndex(high));
  }

  /**
   * Get an iterable object that iterates over key-value pairs that have priorities between
   * {@code low} and {@code high}, in increasing order of priorities.
   * The pairs are produced as an immutable {@link KeyVal} object.
   *
   * @param low  Iteration will start with the ceiling of this key.
   * @param high Iteration will end with the floor of this key.
   * @return An iterable.
   *
   * @throws IllegalArgumentException If either {@code low} or {@code high} are {@code null}.
   */
  @Override
  public Iterable<KeyVal<K, V>> items(K low, K high) {
    if (high == null || low == null) throw new IllegalArgumentException("keys can't be null");

    return () -> new MapKeyValIterator<>(keys, vals, ceilIndex(low), floorIndex(high));
  }

  /* **************************************************************************
   * Section: Helper Classes and Methods
   ************************************************************************** */

  /**
   * Is the given index {@code i} a valid index of the internal array?
   *
   * @param i The index to validate.
   * @return {@code true} if {@code i} is in range <code>[0, {@link #size()})</code>,
   *     {@code false} otherwise.
   */
  private boolean isInRange(int i) {
    return i >= 0 && i < length;
  }

  /**
   * Binary search for {@code key} in the internal array.
   *
   * @param key The key to search for. Must NOT be {@code null}.
   * @return The index at which {@code key} is located. If key doesn't exist,
   *     then {@code -1} is returned.
   */
  private int search(K key) {
    return Arrays.binarySearch(keys, 0, length, key, comp);
  }

  /**
   * Get the index at which the ceiling of a given {@code key} is at.
   *
   * @param k The key to find the ceiling value of. Must NOT be {@code null}.
   * @return The index of the ceiling of {@code key}, {@code -1} if key happens to be
   *     the highest amongst the keys in the map.
   */
  private int ceilIndex(K k) {
    int i = rank(k);
    if (isInRange(i)) {
      return i;
    } else {
      return -1;
    }
  }

  /**
   * Get the index at which the floor of a given {@code key} is at.
   *
   * @param k The key to find the floor value of. Must NOT be {@code null}.
   * @return The index of the floor of {@code key}, {@code -1} if key happens to be
   *     the lowest amongst the keys in the map.
   */
  private int floorIndex(K k) {
    int i = rank(k);
    if (isInRange(i) && compare(k, keys[i]) == 0) {
      return i;
    } else if (isInRange(i - 1)) {
      return i - 1;
    } else {
      return -1;
    }
  }

  /**
   * A helper method for {@link #equals(Object obj) equals} method.
   * Compares all the key-value pairs for equality, until a pair doesn't
   * match or all match, whichever is first.
   *
   * @param map The {@link OrderMap} object to equate all elements with.
   * @return {@code true} if all key-value pairs in {@code map} are equal to
   *     key-value pairs in {@code this}, {@code false} otherwise.
   */
  private boolean equalsForOrderMap(OrderMap<?, ?> map) {
    Iterator<? extends KeyVal<?, ?>> itor = map.items().iterator();
    for (KeyVal<K, V> kv : this.items()) {
      if (!kv.equals(itor.next())) {
        return false;
      }
    }
    return true;
  }

  /**
   * A helper method for {@link #equals(Object obj) equals} method.
   * Compares all the key-value pairs for equality, until a pair doesn't
   * match or all match, whichever is first.
   *
   * <p>
   * Sometimes the key type in {@code this} and {@code map} may not be the same.
   * In such cases, internally, a {@link ClassCastException} is thrown and caught,
   * and {@code false} returned.
   * </p>
   *
   * @param map The {@link Map} object to equate all elements with.
   * @return {@code true} if all key-value pairs in {@code map} are equal to
   *     key-value pairs in {@code this}, {@code false} otherwise.
   */
  @SuppressWarnings("unchecked")
  private boolean equalsForMap(Map<?, ?> map) {
    try {
      for (KeyVal<?, ?> kv : map.items()) {
        if (!this.get((K) kv.key()).equals(kv.val())) {
          return false;
        }
      }
      return true;

    } catch (ClassCastException exc) {
      return false;
    }
  }

  /**
   * Compare the keys {@code a} and {@code b}. If a comparator was provided at the
   * time of construction, then it is used. Otherwise an attempt to cast the keys to
   * {@link Comparable} is made. If the attempt fails, then a {@link IllegalStateException}
   * is thrown.
   *
   * @param a Key to compare with {@code b}.
   * @param b Key to compare with {@code a}.
   * @return A number less than {@code 0} if {@code a < b}, {@code 0} if {@code a == b}
   *     and greater than {@code 0} if {@code a > b}.
   *
   * @throws IllegalStateException If type {@link K Key} doesn't implement a natural order
   *                               using the {@link Comparable} interface, nor was a
   *                               {@link Comparator} provided.
   * @see ArrayOrderMap ArrayOrderMap for details on IllegalStateException.
   */
  @SuppressWarnings("unchecked")
  private int compare(K a, K b) {
    if (comp == null) {
      try {
        return ((Comparable<K>) a).compareTo(b);
      } catch (ClassCastException exc) {
        throw new IllegalStateException("key doesn't implement Comparable nor was a Comparator given", exc);
      }
    }

    return comp.compare(a, b);
  }

  /**
   * Shift the elements of the internal arrays (both {@link #keys} and {@link #vals})
   * over by {@code by} places. The shifting of the elements starts from the index
   * {@code fromIndex} (inclusive). Assumes that the internal arrays have enough space
   * to accommodate the "shifting".
   *
   * @param fromIndex The index to start shifting from (inclusive).
   * @param by        The number of position to shift elements by.
   */
  private void shift(int fromIndex, int by) {
    System.arraycopy(keys, fromIndex, keys, fromIndex + by, length - fromIndex);
    System.arraycopy(vals, fromIndex, vals, fromIndex + by, length - fromIndex);
  }

  /**
   * Resize the internal {@link #keys} and {@link #vals} arrays to have capacity to
   * hold {@code newSize} elements.
   * Resizing is achieved by creating new arrays, copying over the elements from
   * the original arrays, and then replacing the original array with the new arrays.
   *
   * @param newSize The desired capacity of the new arrays.
   */
  @SuppressWarnings("unchecked")
  private void resize(int newSize) {
    K[] newKeys = (K[]) new Object[newSize];
    System.arraycopy(keys, 0, newKeys, 0, size());
    keys = newKeys;

    V[] newVals = (V[]) new Object[newSize];
    System.arraycopy(vals, 0, newVals, 0, size());
    vals = newVals;
  }

  /**
   * A static {@link Iterator} class that iterates over the keys of the map.
   *
   * @param <K> The type of key in the map.
   */
  private static class MapKeyIterator<K> implements Iterator<K> {
    private final K[] keys;     // array of keys
    private final int stop;     // stop before this index (inclusive)
    private int current;        // start at this index (inclusive), represents the current index

    /**
     * Initialize and return a new MapKeyIterator object.
     *
     * @param keys  The array of keys.
     * @param start The index to start iterating from (inclusive).
     * @param stop  The index to stop iterating at (inclusive).
     */
    private MapKeyIterator(K[] keys, int start, int stop) {
      this.keys = keys;
      this.current = start;
      this.stop = stop;
    }

    /**
     * Can the iterator produce another key to return?
     *
     * @return {@code false} if the iterator has been depleted, {@code true} otherwise.
     */
    @Override
    public boolean hasNext() {
      return current <= stop;
    }

    /**
     * Produce the next key from the map and return it.
     *
     * @return The next key.
     *
     * @throws NoSuchElementException If called on a depleted iterator.
     */
    @Override
    public K next() {
      if (!hasNext()) throw new NoSuchElementException("iterator depleted");

      return keys[current++];
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

  /**
   * A static {@link Iterator} class that iterates over the key-value pairs of a map.
   * The key-value pairs are iterated over as {@link KeyVal} objects.
   *
   * @param <K> The type of key in the map.
   * @param <V> The type of value in the map.
   */
  private static class MapKeyValIterator<K, V> implements Iterator<KeyVal<K, V>> {
    private final K[] keys;   // keys array of the map
    private final V[] vals;   // values array of the map
    private final int stop;   // stop at this index (inclusive)
    private int current;      // initialised to start (inclusive), represents the current index

    /**
     * Initialize and return a new MapKeyValIterator object.
     *
     * @param keys  The array of keys of the map.
     * @param vals  The array of values of the map, synchronised with the keys such that
     *              {@code keys[i]} should have value {@code vals[i]}.
     * @param start The index to start iterating from (inclusive).
     * @param stop  The index to stop iterating at (inclusive).
     */
    private MapKeyValIterator(K[] keys, V[] vals, int start, int stop) {
      this.keys = keys;
      this.vals = vals;
      this.current = start;
      this.stop = stop;
    }

    /**
     * Can the iterator produce another key-value pair and return it?
     *
     * @return {@code false} if the iterator has been depleted, {@code true} otherwise.
     */
    @Override
    public boolean hasNext() {
      return current <= stop;
    }

    /**
     * Produce the next key-value pair from the map and return it.
     *
     * @return The next key-value pair as an immutable {@link KeyVal} object.
     *
     * @throws NoSuchElementException If called on a depleted iterator.
     */
    @Override
    public KeyVal<K, V> next() {
      if (!hasNext()) throw new NoSuchElementException("iterator depleted");

      return new KeyVal<>(keys[current], vals[current++]);
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
