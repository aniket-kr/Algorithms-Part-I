package com.company.aniketkr.algorithms1.maps.symbol;

import com.company.aniketkr.algorithms1.maps.Map;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

// FIXME: code complete, javadoc pending

// TODO: write javadoc
public class ArrayMap<K, V> implements Map<K, V> {
  private static final int INIT_CAPACITY = 8;   // default capacity of ArrayMap

  private int length = 0;                       // number of key-value pairs in map
  private K[] keys;                             // array that hold keys
  private V[] vals;                             // array that hold corresponding values.

  /**
   * Initialize and return an empty ArrayMap object.
   * The default capacity is {@value INIT_CAPACITY}.
   */
  public ArrayMap() {
    this(INIT_CAPACITY);
  }

  /**
   * Initialize and return an empty ArrayMap object that has the capacity to
   * hold {@code capacity} key-value pairs.
   *
   * @param capacity The desired number of key-value pairs that the map should be able
   *                 to hold without having to resize.
   * @throws IllegalArgumentException If {@code capacity} is less than or equal to {@code 0}.
   */
  @SuppressWarnings("unchecked")
  public ArrayMap(int capacity) {
    if (capacity <= 0) throw new IllegalArgumentException("invalid capacity: " + capacity);

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
  @SuppressWarnings("unchecked")
  public boolean equals(Object obj) {
    // TODO: reformat code
    if (this == obj)                return true;
    if (obj == null)                return false;
    if (!(obj instanceof Map))      return false;
    Map<?, ?> that = (Map<?, ?>) obj;
    if (this.size() != that.size()) return false;

    // compare all elements
    try {
      for (KeyVal<?, ?> kv : that.items()) {
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
    this.items().forEach(kv -> sb.append(kv.key()).append(": ").append(kv.val()).append(", "));
    sb.setLength(sb.length() - 2);
    return sb.append(" }").toString();
  }

  /* **************************************************************************
   * Section: Iterable Methods
   ************************************************************************** */

  /**
   * Get an iterator that produces all the keys of the map in no particular order.
   * Note that the order in which the keys are produced may not be the same as order
   * of insertion.
   *
   * @return An iterator that produces keys.
   */
  @Override
  public Iterator<K> iterator() {
    return new MapKeyIterator<>(keys, length - 1);
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
   */
  @Override
  public boolean contains(K key) {
    return false;
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
    ArrayMap<K, V> cp = new ArrayMap<>((size() >= 2) ? (size() * 2) : INIT_CAPACITY);
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
  @Override
  public Map<K, V> deepcopy(Function<? super KeyVal<K, V>, KeyVal<K, V>> copyFn) {
    if (copyFn == null) throw new IllegalArgumentException("argument to deepcopy() is null");

    ArrayMap<K, V> cp = new ArrayMap<>((size() >= 2) ? (size() * 2) : INIT_CAPACITY);
    this.items().forEach(kv -> {
      KeyVal<K, V> kvCopy = copyFn.apply(kv);
      cp.put(kvCopy.key(), kvCopy.val());
    });

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
    for (int i = 0; i < length; i++) {
      if (Objects.equals(key, keys[i])) {
        return vals[i];
      }
    }

    throw new NoSuchElementException("map does not have given key: " + key);
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
   */
  @Override
  public V get(K key, V fallback) {
    try {
      return get(key);
    } catch (NoSuchElementException exc) {
      return fallback;
    }
  }

  /**
   * Put {@code key} in the map and associate the value {@code val} with it.
   * If {@code key} already exists in the map, then updated the associated value to be
   * {@code val}; the old value is discarded.
   *
   * @param key The key to add/update in the map to have {@code val} as associated value.
   * @param val The value to associate with {@code key}.
   * @return {@code true} if a new key was added, {@code false} if {@code key} already existed
   *     in the map.
   */
  @Override
  public boolean put(K key, V val) {
    if (size() == keys.length) {
      resize(keys.length * 2);
    }

    for (int i = 0; i < length; i++) {
      if (Objects.equals(key, keys[i])) {
        // key already exists
        vals[i] = val;
        return false;
      }
    }

    // new key will be inserted
    keys[length] = key;
    vals[length++] = val;
    return true;
  }

  /**
   * Delete the given {@code key} and its associated value from the map.
   * If {@code key} does not exist in the map, then simply returns {@code false} - no
   * exception is thrown.
   *
   * @param key The key in the key-value pair to delete.
   * @return {@code true} if {@code key} existed in the map and was deleted, {@code false}
   *     otherwise.
   */
  @Override
  public boolean del(K key) {
    if (size() == keys.length / 4) {
      resize(keys.length / 4);
    }

    for (int i = 0; i < length; i++) {
      if (Objects.equals(key, keys[i])) {
        shiftLeft(i + 1);
        keys[--length] = null;
        vals[length] = null;
        return true;
      }
    }

    return false;
  }

  /**
   * Get an iterable object that iterates over the key-value pairs packed together in an
   * immutable {@link KeyVal} object.
   *
   * @return An iterable.
   */
  @Override
  public Iterable<KeyVal<K, V>> items() {
    return () -> new MapKeyValIterator<>(keys, vals, length - 1);
  }

  /* **************************************************************************
   * Section: Helper Classes and Methods
   ************************************************************************** */

  /**
   * Resize the internal arrays (both {@code keys} and {@code vals}) to have capacity
   * to hold {@code newSize} key-value pairs.
   * Resizing is achieved by adding by creating new arrays, copying over elements from
   * original arrays and replacing the original arrays with new ones.
   *
   * @param newSize The desired number of key-value pairs the map should be able to hold.
   */
  @SuppressWarnings("unchecked")
  private void resize(int newSize) {
    V[] newVals = (V[]) new Object[newSize];
    System.arraycopy(vals, 0, newVals, 0, size());
    vals = newVals;

    K[] newKeys = (K[]) new Object[newSize];
    System.arraycopy(keys, 0, newKeys, 0, size());
    keys = newKeys;
  }

  /**
   * Shift the key-value pairs, in both arrays, left by 1 place, starting index
   * {@code fromIndex} (inclusive).
   *
   * @param fromIndex The index to start shifting from (inclusive).
   */
  private void shiftLeft(int fromIndex) {
    System.arraycopy(vals, fromIndex, vals, fromIndex - 1, size() - fromIndex);
    System.arraycopy(keys, fromIndex, keys, fromIndex - 1, size() - fromIndex);
  }

  /**
   * A static {@link Iterator} class that iterates over the keys of the given map.
   *
   * @param <K> The type of key in the map.
   */
  private static class MapKeyIterator<K> implements Iterator<K> {
    private final K[] keys;     // array holding keys
    private final int stop;     // stop at this index (inclusive)
    private int current = 0;    // iterate over this index next

    /**
     * Initialize and return a new MapKeyIterator object that iterates over all the elements
     * in the array {@code keys}, starting from {@code 0} upto, and including, {@code stop}.
     *
     * @param keys The array holding the keys.
     * @param stop The index to stop at (inclusive).
     */
    private MapKeyIterator(K[] keys, int stop) {
      this.keys = keys;
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
     * Produce the next key from the iterator and return it.
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
   * A static {@link Iterator} class that iterates over the key-value pairs in the
   * given map.
   *
   * @param <K> The type of key in the map.
   * @param <V> The type of value in the map.
   */
  private static class MapKeyValIterator<K, V> implements Iterator<KeyVal<K, V>> {
    private final K[] keys;     // array holding keys
    private final V[] vals;     // array holding vals
    private final int stop;     // stop at this index (inclusive)
    private int current = 0;    // iterate over this index next

    /**
     * Initialize and return a new MapKeyValIterator object, that iterates over the
     * key-value pairs of the map as immutable {@link KeyVal} objects.
     *
     * @param keys The array that holds the keys.
     * @param vals The array that holds the values, such that for a key at {@code keys[i]},
     *             its associated value is at {@code vals[i]}.
     * @param stop Iterate upto this index (inclusive).
     */
    private MapKeyValIterator(K[] keys, V[] vals, int stop) {
      this.keys = keys;
      this.vals = vals;
      this.stop = stop;
    }

    /**
     * Can the iterator produce another key-value pair to return?
     *
     * @return {@code false} if the iterator has been depleted, {@code true} otherwise.
     */
    @Override
    public boolean hasNext() {
      return current <= stop;
    }

    /**
     * Produce the next key-value pair from the iterator and return it.
     *
     * @return The next key-value pair.
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
