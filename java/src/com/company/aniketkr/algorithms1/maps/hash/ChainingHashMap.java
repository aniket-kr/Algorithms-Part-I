package com.company.aniketkr.algorithms1.maps.hash;

import com.company.aniketkr.algorithms1.maps.Map;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class ChainingHashMap<K, V> implements Map<K, V> {
  private static final int INIT_CAPACITY = 8;
  private Map<K, V>[] map;
  private int length = 0;

  public ChainingHashMap() {
    this(INIT_CAPACITY);
  }

  @SuppressWarnings("unchecked")
  public ChainingHashMap(int capacity) {
    if (capacity <= 0) throw new IllegalArgumentException("invalid capacity: " + capacity);

    map = new Map[capacity];
  }

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object obj) {
    // TODO: reformat code
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof Map)) return false;
    Map<?, ?> that = (Map<?, ?>) obj;
    if (this.size() != that.size()) return false;

    // compare all key-value pairs
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

  @Override
  public String toString() {
    return super.toString();
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
   */
  @Override
  public boolean contains(K key) {
    try {
      get(key);
      return true;
    } catch (NoSuchElementException exc) {
      return false;
    }
  }

  /**
   * Clear the map of all its key-value pairs.
   * Sets it to its <em>default</em> state.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void clear() {
    map = new Map[INIT_CAPACITY];
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
  @Override
  public Map<K, V> deepcopy(Function<? super KeyVal<K, V>, KeyVal<K, V>> copyFn) {
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
    int i = hash(key);

    if (map[i] == null) throw new NoSuchElementException("map doesn't contain key");
    return map[i].get(key);
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
    int i = hash(key);
    if (map[i] == null) {
      map[i] = new ArrayMap<>(4);
    }

    if (map[i].put(key, val)) {
      length++;
      return true;
    }
    return false;
  }

  /**
   * Delete the given {@code key} and its associated value from the map.
   * If {@code key} does not exist in the map, then simply returns - no exception is thrown.
   *
   * @param key The key in the key-value pair to delete.
   * @return {@code true} if {@code key} existed in the map and was deleted, {@code false}
   *     otherwise.
   */
  @Override
  public boolean del(K key) {
    int i = hash(key);

    // bucket does not exist = key can't exist
    if (map[i] == null) return false;

    // key exists and was deleted
    if (map[i].del(key)) {
      length--;
      map[i] = map[i].isEmpty() ? null : map[i];
      return true;
    }

    // key doesn't exist in the bucket
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
    return null;
  }

  /* **************************************************************************
   * Section: Helper Classes and Methods
   ************************************************************************** */

  // TODO: write docs
  private int hash(K key) {
    if (key == null) return 0;
    return (key.hashCode() & 0x7f_fff_fff) % map.length;
  }

  // TODO: write docs
  private void rehash(int newSize) {
    ChainingHashMap<K, V> newMap = new ChainingHashMap<>(newSize);
    this.items().forEach(kv -> newMap.put(kv.key(), kv.val()));
    this.map = newMap.map;
  }
}
