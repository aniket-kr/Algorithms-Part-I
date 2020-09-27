package com.company.aniketkr.algorithms1.maps.symbol;

import com.company.aniketkr.algorithms1.maps.Map;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

// FIXME: code under MAJOR construction

// TODO: write javadoc
public class ArrayMap<K, V> implements Map<K, V> {
  private static final int INIT_CAPACITY = 8;

  private int length = 0;
  private K[] keys;
  private V[] vals;

  // TODO: add ctor docs and comments for fields
  public ArrayMap() {
    this(INIT_CAPACITY);
  }

  // TODO: docs
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
  public boolean equals(Object obj) {
    return super.equals(obj);
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
      return null;    // TODO: impl method copy()
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
    return null;     // TODO: impl method deepcopy()
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

  // TODO: write docs
  @SuppressWarnings("unchecked")
  private void resize(int newSize) {
    V[] newVals = (V[]) new Object[newSize];
    System.arraycopy(vals, 0, newVals, 0, size());
    vals = newVals;

    K[] newKeys = (K[]) new Object[newSize];
    System.arraycopy(keys, 0, newKeys, 0, size());
    keys = newKeys;
  }

  // TODO: write docs
  private void shiftLeft(int fromIndex) {
    System.arraycopy(vals, fromIndex, vals, fromIndex - 1, size() - fromIndex);
    System.arraycopy(keys, fromIndex, keys, fromIndex - 1, size() - fromIndex);
  }

  // TODO: write docs
  private static class MapKeyIterator<K> implements Iterator<K> {
    private final K[] keys;     // array holding keys
    private final int stop;     // stop at this index (inclusive)
    private int current = 0;    // iterate over this index next

    // TODO: write docs
    private MapKeyIterator(K[] keys, int stop) {
      this.keys = keys;
      this.stop = stop;
    }

    // TODO: write docs
    @Override
    public boolean hasNext() {
      return current <= stop;
    }

    // TODO: write docs
    @Override
    public K next() {
      if (!hasNext()) throw new NoSuchElementException("iterator depleted");

      return keys[current++];
    }

    // TODO: write docs
    @Override
    public void remove() {
      throw new UnsupportedOperationException("remove() not supported");
    }
  }

  // TODO: write docs
  private static class MapKeyValIterator<K, V> implements Iterator<KeyVal<K, V>> {
    private final K[] keys;     // array holding keys
    private final V[] vals;     // array holding vals
    private final int stop;     // stop at this index (inclusive)
    private int current = 0;    // iterate over this index next

    // TODO: write docs
    private MapKeyValIterator(K[] keys, V[] vals, int stop) {
      this.keys = keys;
      this.vals = vals;
      this.stop = stop;
    }

    // TODO: write docs
    @Override
    public boolean hasNext() {
      return current <= stop;
    }

    // TODO: write docs
    @Override
    public KeyVal<K, V> next() {
      if (!hasNext()) throw new NoSuchElementException("iterator depleted");

      return new KeyVal<>(keys[current], vals[current++]);
    }

    // TODO: write docs
    @Override
    public void remove() {
      throw new UnsupportedOperationException("remove() not supported");
    }
  }
}
