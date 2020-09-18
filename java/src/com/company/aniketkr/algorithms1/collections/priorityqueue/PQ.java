package com.company.aniketkr.algorithms1.collections.priorityqueue;

import com.company.aniketkr.algorithms1.collections.Collection;

import java.util.Comparator;
import java.util.Iterator;


abstract class PQ<E> implements Collection<E> {
  protected static final int INIT_CAPACITY = 8;

  protected final Comparator<E> comparator;
  protected E[] arr;
  protected int length = 0;

  @SuppressWarnings("unchecked")
  protected PQ(int capacity, Comparator<E> comparator) {
    if (comparator == null) {
      comparator = (a, b) -> ((Comparable<E>) a).compareTo(b);
    }

    this.arr = (E[]) new Comparable<?>[capacity];
    this.comparator = comparator;
  }

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * Check if this priority queue is equal to the given object.
   * Calls {@link Object#equals(Object that) equals()} on the elements to check if they
   * are equal.
   *
   * @param obj The other object to check for equality.
   * @return {@code true} if {@code obj} is semantically equal to this priority queue,
   *     {@code false} otherwise.
   * @implSpec As it currently stands, if two priority queue objects are empty, the {@code equals}
   *     method will always return {@code true}. This happens as no elements are available to
   *     compare.
   */
  public boolean equals(Object obj) {
    return false;
  }

  /**
   * Return a string representation of the priority queue. Primarily for debugging.
   *
   * @return A string.
   */
  public String toString() {
    return null;
  }

  /* **************************************************************************
   * Section: Iterable Methods
   ************************************************************************** */

  /**
   * Get an iterator object that produces the elements of the priority queue in the order
   * in which they are arranged in the priority queue.
   *
   * @return An iterator.
   */
  @Override
  public abstract Iterator<E> iterator();

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * How many elements are present in the priority queue?
   *
   * @return The count of number of elements in the priority queue.
   */
  @Override
  public int size() {
    return length;
  }

  /**
   * Is the priority queue empty?
   *
   * @return {@code true} if priority queue is empty, {@code false} otherwise.
   */
  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Clear the priority queue of all its elements. Set it to its instantiated state.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void clear() {
    arr = (E[]) new Object[INIT_CAPACITY];
    length = 0;
  }

  /**
   * Get a shallow copy of the priority queue.
   *
   * @return A new collection.
   */
  @Override
  public abstract Collection<E> copy();

  /* **************************************************************************
   * Section: Priority Queue Methods
   ************************************************************************** */

  /**
   * Insert the element {@code elmt} into the priority queue.
   *
   * @param elmt The element to insert.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  public void insert(E elmt) {
    if (elmt == null) {
      throw new IllegalArgumentException("argument to insert() is null");
    }
    if (size() == arr.length) {
      resizeArr(arr.length * 2);
    }
  }

  /* **************************************************************************
   * Section: Helper Methods
   ************************************************************************** */

  protected void swim(int k) {
  }

  protected void sink(int k) {
  }

  @SuppressWarnings("unchecked")
  protected void resizeArr(int newSize) {
    E[] newArr = (E[]) new Object[newSize];
    System.arraycopy(arr, 0, newArr, 0, size());
    arr = newArr;
  }
}
