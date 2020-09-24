package com.company.aniketkr.algorithms1.maps.symbol;

import com.company.aniketkr.algorithms1.maps.Map;
import com.company.aniketkr.algorithms1.maps.OrderMap;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

// TODO: code under MAJOR construction

// TODO: add javadoc
public class OrderedMap<K, V> implements OrderMap<K, V> {
  private static final int INIT_CAPACITY = 8;     // default capacity of internal arrays

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
  public String toString() {
    return null;
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
    return 0;
  }

  /**
   * Is the map empty?
   *
   * @return {@code true} if there are no key-value pairs in the map, {@code false}
   *     otherwise.
   */
  @Override
  public boolean isEmpty() {
    return false;
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
    return false;
  }

  /**
   * Return a shallow copy of the map.
   * A shallow copy creates a copy of the map but not of the keys and values in
   * the map.
   *
   * @return A shallow copy of the map.
   *
   * @see #deepcopy(Function keyCopyFn, Function valCopyFn)
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
   * @param keyCopyFn A {@link Function} that takes original key as the argument and
   *                  returns a deepcopy of that key.
   * @param valCopyFn A {@link Function} that takes the original value as the argument
   *                  and returns a deepcopy of that value.
   * @return A deepcopy of the map.
   *
   * @throws IllegalArgumentException If {@code keyCopyFn} or {code valCopyFn} is
   *                                  {@code null}. Also if any value returned by
   *                                  {@code keyCopyFn} is {@code null}.
   * @see #copy()
   */
  @Override
  public Map<K, V> deepcopy(Function<? super K, K> keyCopyFn, Function<? super V, V> valCopyFn) {
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
    return null;
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
    return null;
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
  public void set(K key, V val) {

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
    return null;
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
    return null;
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

  /* **************************************************************************
   * Section: Helper Classes and Methods
   ************************************************************************** */
}
