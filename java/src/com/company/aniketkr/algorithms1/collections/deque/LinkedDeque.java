package com.company.aniketkr.algorithms1.collections.deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

// FIXME: under MAJOR construction

/**
 * A deque (pronounced "deck") is a collection of objects such that we can access and
 * alter the elements from two ends - <em>front</em> and <em>back</em>. This implementation
 * of deque supports the usual <em>add</em>, <em>delete</em> and <em>peek</em> operations
 * from both ends.
 *
 * <p>Apart from these operations, the <em>copy</em> method returns a {@link LinkedDeque} object.
 * The deque also supports iteration of elements in both directions - {@link Iterable} interface
 * supports iteration from <em>front</em> to <em>back</em> and the <em>reverse</em> method
 * supports it in the other direction.</p>
 *
 * @param <E> The type of elements in the deque.
 * @author Aniket Kumar
 */
public final class LinkedDeque<E> implements Deque<E> {
  private Node front;
  private Node back;
  private int length = 0;

  /**
   * Instantiate and return a new {@link LinkedDeque} object.
   */
  public LinkedDeque() {
    front = null;
    back = null;
  }

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * Check if this deque is equal to the given object.
   * Calls {@link Object#equals(Object that) equals()} on the elements to check if they
   * are equal.
   *
   * @param obj The other object to check for equality.
   * @return {@code true} if {@code obj} is a subclass of {@link LinkedDeque} and all the elements
   *     in the deque are equal; {@code false} otherwise.
   * @implNote As it currently stands, if two deque objects are empty, the {@code equals()}
   *     method will always return {@code true}. This happens as no elements are available to
   *     compare.
   */
  public boolean equals(Object obj) {
    if (obj == this)                return true;
    if (obj == null)                return false;
    if (!(obj instanceof LinkedDeque))    return false;
    LinkedDeque<?> that = (LinkedDeque<?>) obj;
    if (this.size() != that.size()) return false;

    // compare each element
    Iterator<E> itor1 = this.iterator();
    Iterator<?> itor2 = that.iterator();
    while (itor1.hasNext()) {
      if (!itor1.next().equals(itor2.next())) {
        return false;
      }
    }
    return true;
  }

  /**
   * Return a string representation of the deque. Primarily for debugging.
   *
   * @return A string.
   */
  public String toString() {
    if (isEmpty()) return "(F[ ]B)";

    StringBuilder sb = new StringBuilder("(F[ ");
    for (E elmt : this) {
      sb.append(elmt);
      sb.append(", ");
    }
    sb.setLength(sb.length() - 2);
    sb.append(" ]B)");
    return sb.toString();
  }

  /* **************************************************************************
   * Section: Iterable Methods
   ************************************************************************** */

  /**
   * Get an iterator object that produces the elements of the deque from <em>front</em>
   * to <em>back</em>.
   *
   * @return An iterator.
   */
  @Override
  public Iterator<E> iterator() {
    return new DoublyLinkedListIterator(front, true);
  }

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * How many elements are present in the deque?
   *
   * @return The count of number of elements in the deque.
   */
  @Override
  public int size() {
    return length;
  }

  /**
   * Is the deque empty?
   *
   * @return {@code true} if deque is empty, {@code false} otherwise.
   */
  @Override
  public boolean isEmpty() {
    return length == 0;
  }

  /**
   * Does {@code elmt} exist in the deque?
   *
   * @param elmt The element to check for.
   * @return {@code true} if {@code elmt} exists in the deque, {@code false}
   *     otherwise.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  public boolean contains(E elmt) {
    if (elmt == null) throw new IllegalArgumentException("argument to contains() is null");

    for (E dequeElmt : this) {
      if (dequeElmt.equals(elmt)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Clear the deque of all its elements. Set it to its instantiated state.
   */
  @Override
  public void clear() {
    length = 0;
    front = null;
    back = null;
  }

  /**
   * Get a shallow copy of the deque.
   *
   * @return A new deque.
   */
  @Override
  public LinkedDeque<E> copy() {
    LinkedDeque<E> cp = new LinkedDeque<>();
    for (E elmt : this) {
      cp.addBack(elmt);
    }

    return cp;
  }

  /**
   * Get an iterable object that produces the elements from <em>back</em> to <em>front</em>.
   *
   * @return An iterable.
   */
  public Iterable<E> reverse() {
    return () -> new DoublyLinkedListIterator(back, false);
  }

  /* **************************************************************************
   * Section: Deque Methods
   ************************************************************************** */

  /**
   * Add the given element to the <em>front</em> of the deque.
   *
   * @param elmt The element to add.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  public void addFront(E elmt) {
    if (elmt == null) throw new IllegalArgumentException("argument to addFront() is null");

    Node node = new Node();
    node.elmt = elmt;

    if (isEmpty()) {
      front = node;
      back = node;
    } else {
      front.prev = node;
      node.next = front;
      front = node;
    }
    length++;
  }

  /**
   * Add the given element to the <em>back</em> of the deque.
   *
   * @param elmt The element to add.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  public void addBack(E elmt) {
    if (elmt == null) throw new IllegalArgumentException("argument to addBack() is null");

    Node node = new Node();
    node.elmt = elmt;

    if (isEmpty()) {
      back = node;
      front = node;
    } else {
      back.next = node;
      node.prev = back;
      back = node;
    }
    length++;
  }

  /**
   * Delete and return the element at the <em>front</em> of the deque.
   *
   * @return The deleted element that was at the <em>front</em>.
   * @throws NoSuchElementException If deque is empty (underflow).
   */
  @Override
  public E delFront() {
    if (isEmpty()) throw new NoSuchElementException("Underflow: can't delete from empty deque");

    E elmt = front.elmt;

    if (size() == 1) {
      front = null;
      back = null;
    } else {
      front = front.next;
      front.prev.next = null;
      front.prev = null;
    }
    length--;

    return elmt;
  }

  /**
   * Delete and return the element at the <em>back</em> of the deque.
   *
   * @return The deleted element that was at the <em>back</em>.
   * @throws NoSuchElementException If deque is empty (underflow).
   */
  public E delBack() {
    if (isEmpty()) throw new NoSuchElementException("Underflow: can't delete from empty deque");

    E elmt = back.elmt;

    if (size() == 1) {
      back = null;
      front = null;
    } else {
      back = back.prev;
      back.next.prev = null;
      back.next = null;
    }
    length--;

    return elmt;
  }

  /**
   * Return the element that was at the <em>front</em> of the deque, without
   * deleting it.
   *
   * @return The element that is at <em>front</em> of the deque.
   * @throws NoSuchElementException If deque is empty (underflow).
   */
  public E front() {
    if (isEmpty()) throw new NoSuchElementException("Underflow: can't peek at empty deque");

    return front.elmt;
  }

  /**
   * Return the element that was at the <em>back</em> of the deque, without
   * deleting it.
   *
   * @return The element that is at <em>back</em> of the deque.
   * @throws NoSuchElementException If deque is empty (underflow).
   */
  public E back() {
    if (isEmpty()) throw new NoSuchElementException("Underflow: can't peek at empty deque");

    return back.elmt;
  }

  /* **************************************************************************
   * Section: Helper Classes and Methods
   ************************************************************************** */

  /**
   * Represents a node in a doubly linked list. Holds the element and a reference to
   * the prev and next nodes.
   */
  private class Node {
    E elmt;
    Node prev = null;
    Node next = null;
  }

  /**
   * An iterator class that supports iteration over a doubly linked list from
   * either direction.
   */
  private class DoublyLinkedListIterator implements Iterator<E> {
    private final boolean frontToBack;
    private Node current;

    /**
     * Instantiate and return a new {@link DoublyLinkedListIterator} object.
     *
     * @param startNode   The node to start iterating from.
     * @param frontToBack Pass {@code true} if the iterator should move <em>front</em>
     *                    from {@code startNode}, {@code false} for <em>backwards</em>.
     */
    private DoublyLinkedListIterator(Node startNode, boolean frontToBack) {
      this.frontToBack = frontToBack;
      this.current = startNode;
    }

    /**
     * Does the iterator have any more elements to produce?
     *
     * @return {@code false} if the iterator has been depleted, {@code false} otherwise.
     */
    @Override
    public boolean hasNext() {
      return current != null;
    }

    /**
     * Gets the next element produced by the iterator.
     *
     * @return The next element.
     * @throws NoSuchElementException If the iterator has been depleted.
     */
    @Override
    public E next() {
      if (!hasNext()) throw new NoSuchElementException("iterator depleted");

      E elmt = current.elmt;
      current = frontToBack ? current.next : current.prev;
      return elmt;
    }

    /**
     * Throws UOE. {@code remove()} is not supported.
     *
     * @throws UnsupportedOperationException Always.
     */
    @Override
    public void remove() {
      throw new UnsupportedOperationException("remove() is not supported");
    }
  }
}
