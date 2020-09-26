package com.company.aniketkr.algorithms1.maps;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * An {@code OrderMap}, short for {@link Map} with ordering, is an extension of the
 * concept of a {@code Map} with keys that can not only be equated but also define a
 * natural order. The natural order can be defined using either {@link Comparable}
 * interface or by providing a {@link Comparator} that defines a different order.
 * Note that unlike a {@code Map}, an {@code OrderMap} does not allow {@code null}
 * keys to be inserted into the map.
 *
 * <p>
 * Alongside all {@link Map} operations, this interface extends support to all those
 * operations that can be performed when keys define an order. These include
 * <em>find-the-minimum</em> & <em>find-the-maximum</em>, <em>floor</em> & <em>ceiling</em>,
 * <em>rank</em> & <em>select</em> and <em>delete-the-minimum</em> &
 * <em>delete-the-maximum</em> operations. Apart from iteration over all keys in increasing
 * order of priority, the <em>keys</em> method guarantees support for efficient iteration over
 * a subset of keys present in the map.
 * </p>
 *
 * @param <K> The type of key in the map.
 * @param <V> The type of value associated with {@link K Key} objects.
 * @author Aniket Kumar
 */
public interface OrderMap<K, V> extends Map<K, V> {

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
   * Get an iterator that produces all the keys of the map in ascending order according
   * to the specific method of comparison being used.
   *
   * @return An iterator that produces keys in ascending order.
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
  @Override
  int size();

  /**
   * Is the map empty?
   *
   * @return {@code true} if there are no key-value pairs in the map, {@code false}
   *     otherwise.
   */
  @Override
  boolean isEmpty();

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
  boolean contains(K key);

  /**
   * Clear the map of all its key-value pairs.
   * Sets it to its <em>default</em> state.
   */
  @Override
  void clear();

  /**
   * Return a shallow copy of the map.
   * A shallow copy creates a copy of the map but not of the keys and values in
   * the map.
   *
   * @return A shallow copy of the map.
   *
   * @see Map#deepcopy(Function)
   */
  @Override
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
   * @throws NoSuchElementException If {@code key} does not exist in the map.
   */
  @Override
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
   *
   * @throws IllegalArgumentException If {@code key} is {@code null}.
   */
  @Override
  V get(K key, V fallback);

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
  void put(K key, V val);

  /**
   * Delete the given {@code key} and its associated value from the map.
   * If {@code key} does not exist in the map, then simply returns - no exception is thrown.
   *
   * @param key The key in the key-value pair to delete.
   * @throws IllegalArgumentException If {@code key} is {@code null}.
   */
  @Override
  void del(K key);

  /**
   * Get the {@code Comparator} object being user for comparing keys.
   *
   * @return The comparator being used to compare keys. If the natural order defined by
   * the {@link Comparable} interface is being used then returns {@code null}.
   */
  Comparator<K> comparator();

  /**
   * Get an iterable object that iterates over the key-value pairs packed together in an
   * immutable {@link Map.KeyVal} object.
   *
   * @return An iterable.
   */
  @Override
  Iterable<Map.KeyVal<K, V>> items();

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
  K min();

  /**
   * Get the key with the highest priority according to the specific method of comparison
   * being used.
   *
   * @return The key with maximum priority.
   *
   * @throws NoSuchElementException If called on an empty map.
   */
  K max();

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
  K floor(K k);

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
  K ceil(K k);

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
  int rank(K key);

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
  K select(int rank);

  /**
   * Delete the key with the lowest priority in the map.
   *
   * @throws NoSuchElementException If called on an empty map.
   */
  void delMin();

  /**
   * Delete the key with the highest priority in the map.
   *
   * @throws NoSuchElementException If called on an empty map.
   */
  void delMax();

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
  Iterable<K> keys(K low, K high);

  /**
   * Get an iterable object that iterates over key-value pairs that have priorities between
   * {@code low} and {@code high}, in increasing order of priorities.
   * The pairs are produced as an immutable {@link Map.KeyVal} object.
   *
   * @param low  Iteration will start with the ceiling of this key.
   * @param high Iteration will end with the floor of this key.
   * @return An iterable.
   * @throws IllegalArgumentException If either {@code low} or {@code high} are {@code null}.
   */
  Iterable<Map.KeyVal<K, V>> items(K low, K high);
}
