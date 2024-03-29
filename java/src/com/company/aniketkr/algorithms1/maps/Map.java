package com.company.aniketkr.algorithms1.maps;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;


/**
 * A type of data structure where certain objects ("keys") are associated with/mapped to
 * certain other objects ("values") is called a Map. A map is short for mapping. This
 * interface guarantees support for the usual <em>get</em>, <em>set</em> and <em>delete</em>
 * operations. The interface aims to use only the {@link #equals(Object obj) equals}
 * method on the keys. Since {@code null} can be (sort of) equated, the implementing classes
 * may allow a {@code null} key to be inserted.
 *
 * @param <K> The type of key in the map.
 * @param <V> The type of value associated with {@link K Key} objects.
 * @author Aniket Kumar
 */
public interface Map<K, V> extends Iterable<K> {

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * Is the map equal to the given object {@code obj}?
   * A map will be deemed equal to {@code obj} if {@code obj} satisfies the following
   * conditions:
   * <ol>
   *   <li>{@code obj} is a {@link Map} object or one of its subclasses.</li>
   *   <li>
   *     All the keys in {@code obj} are equal to the corresponding keys in this
   *     map.
   *   </li>
   * </ol>
   *
   * <p>
   *   Because of Java's use of erasure, it is currently not possible to differentiate
   *   between the type of elements in two empty maps. This implies that if two
   *   empty {@link Map} objects - which have two different types of keys, are
   *   compared with {@code equals()}, then the result will always be {@code true}.
   * </p>
   *
   * @param obj The other Object to compare {@code this} with for equality.
   * @return {@code true} if {@code obj} is equal to this map,
   *     {@code false} otherwise.
   */
  @Override
  boolean equals(Object obj);

  /**
   * Get a string representation of key-value pairs in the map.
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
   * Get an iterator that produces all the keys of the map in no particular order.
   * Note that the order in which the keys are produced may not be the same as order
   * of insertion.
   *
   * @return An iterator that produces keys.
   */
  @Override
  Iterator<K> iterator();

  /* **************************************************************************
   * Section: Map Methods
   ************************************************************************** */

  /**
   * How many key-value pairs does this map have?
   *
   * @return The count of key-value pairs in the map.
   */
  int size();

  /**
   * Is the map empty?
   *
   * @return {@code true} if there are no key-value pairs in the map, {@code false}
   *     otherwise.
   */
  boolean isEmpty();

  /**
   * Does {@code key} exist in the map?
   *
   * @param key The key to check existence of.
   * @return {@code true} if a there is a value associated with {@code key} in the map,
   *     {@code false} otherwise.
   */
  boolean contains(K key);

  /**
   * Clear the map of all its key-value pairs.
   * Sets it to its <em>default</em> state.
   */
  void clear();

  /**
   * Return a shallow copy of the map.
   * A shallow copy creates a copy of the map but not of the keys and values in
   * the map.
   *
   * @return A shallow copy of the map.
   *
   * @see #deepcopy(Function copyFn)
   */
  Map<K, V> copy();

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
  Map<K, V> deepcopy(Function<? super KeyVal<K, V>, KeyVal<K, V>> copyFn);

  /**
   * Get the value associated with {@code key} from the map.
   *
   * @param key The key to get associated value of.
   * @return If {@code key} exists, then the value associated with {@code key}. If {@code key}
   *     does not exist, then an exception is thrown.
   *
   * @throws java.util.NoSuchElementException If {@code key} does not exist in the map.
   */
  V get(K key);

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
  V get(K key, V fallback);

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
  boolean put(K key, V val);

  /**
   * Delete the given {@code key} and its associated value from the map.
   * If {@code key} does not exist in the map, then simply returns {@code false} - no
   * exception is thrown.
   *
   * @param key The key in the key-value pair to delete.
   * @return {@code true} if {@code key} existed in the map and was deleted, {@code false}
   *     otherwise.
   */
  boolean del(K key);

  /**
   * Get an iterable object that iterates over the key-value pairs packed together in an
   * immutable {@link KeyVal} object.
   *
   * @return An iterable.
   */
  Iterable<KeyVal<K, V>> items();

  /* **************************************************************************
   * Section: Interface Static Classes
   ************************************************************************** */

  // TODO: write docs
  final class KeyVal<K, V> {
    private final K key;
    private final V val;

    public KeyVal(K key, V val) {
      this.key = key;
      this.val = val;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)              return true;
      if (!(obj instanceof KeyVal)) return false;
      KeyVal<?, ?> that = (KeyVal<?, ?>) obj;

      return Objects.equals(this.key, that.key) && Objects.equals(this.val, that.val);
    }

    @Override
    public String toString() {
      return String.format("(%s: %s)", key, val);
    }

    public K key() {
      return key;
    }

    public V val() {
      return val;
    }
  }
}
