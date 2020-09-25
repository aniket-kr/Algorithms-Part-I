package com.company.aniketkr.algorithms1.maps.symbol;

import com.company.aniketkr.algorithms1.maps.Map;
import com.company.aniketkr.algorithms1.maps.OrderMap;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

// TODO: code under MAJOR construction

// TODO: add javadoc
public class ArrayOrderMap<K, V> implements OrderMap<K, V> {
  private static final int INIT_CAPACITY = 8;     // default capacity of internal arrays

  private final Comparator<K> comp;               // Comparator to use, if needed
  private K[] keys;                               // holds the keys
  private V[] vals;                               // holds the values
  private int length = 0;                         // number of key-value pairs

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
    return false;
  }

  /**
   * Get a string representation of key-value pairs in the map.
   * Primarily for debugging and helping find bugs in client code.
   *
   * @return A string.
   */
  @Override
  @SuppressWarnings("DuplicatedCode")
  public String toString() {
    String className = this.getClass().getSimpleName();

    if (isEmpty()) return className + "[0] { }";

    StringBuilder sb = new StringBuilder(className).append("[").append(size()).append("] { ");
    for (Map.KeyVal<K, V> kv : this.items()) {
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
    return null;
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

    return search(key, false) != -1;
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
    return null;
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
  public Map<K, V> deepcopy(Function<KeyVal<? super K, ? super V>, KeyVal<? extends K, ? extends V>> copyFn) {
    return null;
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

    int i = search(key, false);
    if (i != -1)  return vals[i];

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

    int i = search(key, false);
    return (i != -1) ? vals[i] : fallback;
  }

  /**
   * Put {@code key} in the map and associate the value {@code val} with it.
   * If {@code key} already exists in the map, then update the associated value to be
   * {@code val}; the old value is discarded.
   *
   * @param key The key to add/update in the map to have {@code val} as associated value.
   * @param val The value to associate with {@code key}.
   * @throws IllegalArgumentException If {@code key} is {@code null}.
   */
  @Override
  public void put(K key, V val) {
    if (key == null) throw new IllegalArgumentException("1st argument to set() is null");
    if (size() == keys.length) {
      resize(keys.length * 2);
    }

    int i = search(key, true);
    shift(i, 1);
    keys[i] = key;
    vals[i] = val;
    length++;
  }

  /**
   * Delete the given {@code key} and its associated value from the map.
   * If {@code key} does not exist in the map, then simply returns - no exception is thrown.
   *
   * @param key The key in the key-value pair to delete.
   * @throws IllegalArgumentException If {@code key} is {@code null}.
   */
  @Override
  public void del(K key) {
    if (key == null) throw new IllegalArgumentException("argument to del() is null");

    int i = search(key, false);
    if (i == -1) return;

    shift(i + 1, -1);
    keys[--length] = null;
    vals[length] = null;
  }

  /**
   * Get the {@code Comparator} object being user for comparing keys.
   *
   * @return The comparator being used to compare keys. If the natural order defined by
   *     the {@link Comparable} interface is being used then returns {@code null}.
   */
  public Comparator<K> comparator() {
    return comp;
  }

  /**
   * Get an iterable object that iterates over the key-value pairs packed together in an
   * immutable {@link Map.KeyVal} object.
   *
   * @return An iterable.
   */
  @Override
  public Iterable<Map.KeyVal<K, V>> items() {
    return null;
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
    return null;
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
    return null;
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
    return 0;
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
    return null;
  }

  /**
   * Delete the key with the lowest priority in the map.
   *
   * @throws NoSuchElementException If called on an empty map.
   */
  @Override
  public void delMin() {

  }

  /**
   * Delete the key with the highest priority in the map.
   *
   * @throws NoSuchElementException If called on an empty map.
   */
  @Override
  public void delMax() {

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
    return null;
  }

  /**
   * Get an iterable object that iterates over key-value pairs that have priorities between
   * {@code low} and {@code high}, in increasing order of priorities.
   * The pairs are produced as an immutable {@link Map.KeyVal} object.
   *
   * @param low  Iteration will start with the ceiling of this key.
   * @param high Iteration will end with the floor of this key.
   * @return An iterable.
   *
   * @throws IllegalArgumentException If either {@code low} or {@code high} are {@code null}.
   */
  @Override
  public Iterable<Map.KeyVal<K, V>> items(K low, K high) {
    return null;
  }

  /* **************************************************************************
   * Section: Helper Classes and Methods
   ************************************************************************** */

  // TODO: write docs
  private int search(K key, boolean findPos) {
    int start = 0;
    int end = length - 1;

    int mid = (int) ((start / 2.0) + (end / 2.0));  // if bypassing while loop
    while (start <= end) {
      // TODO: reformat cases
      mid = (int) ((start / 2.0) + (end / 2.0));
      switch (compare(keys[mid], key)) {
        case 0  -> { return mid; }
        case 1  -> end = mid - 1;
        default -> start = mid + 1;
      }
    }

    return findPos ? mid : -1;
  }

  // TODO: write docs
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

  // TODO: write docs
  private void swap(int i, int j) {
    // swap keys
    K tmpKey = keys[i];
    keys[i] = keys[j];
    keys[j] = tmpKey;

    // swap values
    V tmpVal = vals[i];
    vals[i] = vals[j];
    vals[j] = tmpVal;
  }

  // TODO: write docs
  private void shift(int fromIndex, int by) {
    System.arraycopy(keys, fromIndex, keys, fromIndex + by, length - fromIndex);
    System.arraycopy(vals, fromIndex, vals, fromIndex + by, length - fromIndex);
  }
}
