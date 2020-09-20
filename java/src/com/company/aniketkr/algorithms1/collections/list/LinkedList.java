package com.company.aniketkr.algorithms1.collections.list;

import java.util.Iterator;
import java.util.NoSuchElementException;


// TODO: docs under construction

/**
 * Implements the {@link List} interface using a singly linked list. New "nodes" to
 * this dynamic list are added/removed as new elements are <em>added/inserted</em>
 * and deleted as they are <em>removed</em>.
 *
 * @param <E> The type of the elements in the list.
 * @author Aniket Kumar
 */
public final class LinkedList<E> implements List<E> {
  private Node head;
  private Node tail;
  private int length = 0;

  public LinkedList() {
    head = null;
    tail = null;
  }

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * Check if this list is equal to the given object.
   * Calls {@link Object#equals(Object that) equals()} on the elements to check if they
   * are equal.
   *
   * @param obj The other object to check for equality.
   * @return {@code true} if {@code obj} is semantically equal to this list,
   *     {@code false} otherwise.
   * @implSpec As it currently stands, if two list objects are empty, the {@code equals}
   *     method will always return {@code true}. This happens as no elements are available
   *     to compare.
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
      if (!elmt.equals(itor.next())) {
        return false;
      }
    }
    return true;
  }

  /**
   * Return a string representation of the list. Primarily for debugging.
   *
   * @return A string.
   */
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
   * Get an iterator object that produces the elements of the list in its natural
   * order.
   *
   * @return An iterator.
   */
  @Override
  public Iterator<E> iterator() {
    return new ListIterator();
  }

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * How many elements are present in the list?
   *
   * @return The count of number of elements in the list.
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
   * Does the element {@code elmt} exist in the list?
   *
   * @param elmt The element.
   * @return {@code true} if {@code elmt} exists in the list, {@code false} otherwise.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  @Override
  public boolean contains(E elmt) {
    if (elmt == null) throw new IllegalArgumentException("argument to contains() is null");

    for (E listElmt : this) {
      if (listElmt.equals(elmt)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Clear the list of all its elements. Set it to its instantiated state.
   */
  @Override
  public void clear() {
    head = null;
    tail = null;
    length = 0;
  }

  /**
   * Get a shallow copy of the list.
   *
   * @return A new list.
   */
  @Override
  public List<E> copy() {
    LinkedList<E> cp = new LinkedList<>();
    for (E elmt : this) {
      cp.add(elmt);
    }

    return cp;
  }

  /* **************************************************************************
   * Section: List Methods
   ************************************************************************** */

  /**
   * Add the given element {@code elmt} to the end of the list.
   *
   * @param elmt The element.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  @Override
  public void add(E elmt) {
    if (elmt == null) throw new IllegalArgumentException("argument to add() is null");

    Node node = new Node(elmt);
    if (isEmpty()) {
      tail = node;
      head = node;
    } else {
      tail.next = node;
      tail = node;
    }
    length++;
  }

  /**
   * Insert the given element {@code elmt} at the index {@code atIndex}.
   *
   * @param elmt    The element.
   * @param atIndex The index to insert at.
   * @throws IllegalArgumentException  If {@code elmt} is {@code null}.
   * @throws IndexOutOfBoundsException If {@code atIndex} is not in range <code>
   *                                   [0, {@link #size()}]</code>.
   */
  @Override
  public void add(E elmt, int atIndex) {
    if (elmt == null)      throw new IllegalArgumentException("1st argument to add() is null");
    if (!inRange(atIndex)) throw new IndexOutOfBoundsException("invalid index: " + atIndex);

    Node newNode = new Node(elmt);
    if (atIndex == 0) {
      newNode.next = head;
      head = newNode;
    } else {
      Node prevNode = getNode(atIndex - 1);
      newNode.next = prevNode.next;
      prevNode.next = newNode;
    }
    length++;
  }

  /**
   * Add all the elements in the array {@code elmts} to the end of the list.
   *
   * @param elmts The array of elements to add.
   * @throws IllegalArgumentException If either {@code elmts} or any of its elements is
   *                                  {#code null}.
   */
  @Override
  public void addAll(E[] elmts) {
    // TODO: implement this after deciding what to do with null values
  }

  /**
   * Insert all the elements in the array {@code elmts} to the list, starting from
   * the index {@code fromIndex}.
   *
   * @param elmts     The array of elements to insert.
   * @param fromIndex The index at which the first element from {@code elmts}
   *                  should appear.
   * @throws IllegalArgumentException  If {@code elmts} or any of its elements is
   *                                   {@code null}.
   * @throws IndexOutOfBoundsException If {@code fromIndex} is not in range <code>
   *                                   [0, {@link #size()}]</code>.
   */
  @Override
  public void addAll(E[] elmts, int fromIndex) {
    // TODO: implement this after deciding what to do with null values
  }

  /**
   * Add all the elements produced by the iterable {@code elmts}, to the end of the
   * list.
   *
   * @param elmts The iterable that produces elements to add.
   * @throws IllegalArgumentException If {@code elmts}, or any of the elements it
   *                                  produces, is {@code null}.
   */
  @Override
  public void addAll(Iterable<? extends E> elmts) {
    // TODO: implement this after deciding what to do with null values
  }

  /**
   * Insert all the elements produced by the iterable {@code elmts} to the list, starting
   * from the index {#code fromIndex}.
   *
   * @param elmts     The iterable that produces elements to insert.
   * @param fromIndex The index at which the first element produced by {#code elmts}
   *                  should appear.
   * @throws IllegalArgumentException  If {@code elmts}, or any of the elements it
   *                                   produces, is {@code null}.
   * @throws IndexOutOfBoundsException If {@code fromIndex} is not in range <code>
   *                                   [0, {@link #size()}]</code>.
   */
  @Override
  public void addAll(Iterable<? extends E> elmts, int fromIndex) {
    // TODO: implement this after deciding what to do with null values
  }

  /**
   * Get the element at the index {@code index} in the list.
   *
   * @param index The index.
   * @return The element at {@code index}.
   * @throws IndexOutOfBoundsException If {@code index} is not in range <code>
   *                                   [0, {@link #size()}]</code>.
   */
  @Override
  public E get(int index) {
    if (!inRange(index)) throw new IllegalArgumentException("invalid index: " + index);

    return getNode(index).elmt;
  }

  /**
   * Get the index of the first occurrence of {@code elmt} in the list. Uses elements'
   * {@link Object#equals(Object obj) equals} implementation to check if {@code elmt}
   * is equals to any of them.
   *
   * @param elmt The element to look for.
   * @return The index of first occurrence of {@code elmt} in the list; {@literal -1}
   *     if {@code elmt} does not exist in the list.
   */
  @Override
  public int indexOf(E elmt) {
    if (elmt == null) throw new IllegalArgumentException("argument to indexOf() is null");

    return indexOf(elmt, 0, size());
  }

  /**
   * Get the index of the first occurrence of {@code elmt} between indices {@code fromIndex}
   * and {@code toIndex}.
   *
   * @param elmt      The element to look for.
   * @param fromIndex The index to start looking at (inclusive).
   * @param toIndex   The index to look upto (exclusive).
   * @return The index of the first occurrence of {@code elmt} in the range, {@literal -1}
   *     if {@code elmt} does not exist in the given range.
   * @throws IllegalArgumentException  If {@code elmt} is {@code null}.
   * @throws IndexOutOfBoundsException If either {@code fromIndex} or {@code toIndex} is
   *                                   not in range <code>[0, {@link #size()}]</code>.
   * @implSpec If no elements fall in the range specified by {@code fromIndex} and
   *     {@code toIndex}, then {@literal -1} is returned.
   */
  @Override
  public int indexOf(E elmt, int fromIndex, int toIndex) {
    if (elmt == null) throw new IllegalArgumentException("1st argument to indexOf() is null");
    if (!(inRange(fromIndex) && inRange(toIndex, size()))) {
      throw new IndexOutOfBoundsException(String.format("invalid indices: %d, %d", fromIndex, toIndex));
    }

    int i = 0;
    Node current = getNode(fromIndex);

    // finds the required element
    for (; i < toIndex; i++) {
      if (elmt.equals(current.elmt)) {
        return i;
      }
      current = current.next;
    }
    return -1;
  }

  /**
   * Delete the element at the given index in the list and return it.
   *
   * @param index The index to delete element at.
   * @return The deleted element.
   * @throws IndexOutOfBoundsException If {@code index} is not in range <code>
   *                                   [0, {@link #size()}</code>.
   */
  @Override
  public E delete(int index) {
    if (!inRange(index)) throw new IndexOutOfBoundsException("invalid index: " + index);

    E elmt;
    if (index == 0) {
      elmt = head.elmt;
      head = head.next;
    } else {
      Node prevNode = getNode(index - 1);
      elmt = prevNode.next.elmt;
      prevNode.next = prevNode.next.next;
    }
    length--;

    return elmt;
  }

  /**
   * Remove the first occurrence of the element {@code elmt} in the list and return its
   * index.
   *
   * @param elmt The element to remove.
   * @return The index of the now removed element; {@literal -1} if {@code elmt} did not
   *     exist in the list.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  @Override
  public int remove(E elmt) {
    int i = indexOf(elmt);
    if (i != -1) {
      delete(i);
    }

    return i;
  }

  /* **************************************************************************
   * Section: Helper Methods and Classes
   ************************************************************************** */

  private boolean inRange(int i) {
    return i >= 0 && i < size();
  }

  private boolean inRange(int i, int include) {
    return inRange(i) || i == include;
  }

  private Node getNode(int atIndex) {
    Node current = head;
    for (int i = 0; i < atIndex; i++) {
      current = current.next;
    }
    return current;
  }

  private class Node {
    E elmt;
    Node next = null;

    Node(E elmt) {
      this.elmt = elmt;
    }
  }

  private class ListIterator implements Iterator<E> {
    private Node current = head;

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public E next() {
      if (!hasNext()) throw new NoSuchElementException("iterator depleted");

      E elmt = current.elmt;
      current = current.next;
      return elmt;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException("remove() not supported");
    }
  }
}
