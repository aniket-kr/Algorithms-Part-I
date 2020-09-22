package com.company.aniketkr.algorithms1.collections.deque;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;


/**
 * Implements the {@link Deque} interface using a doubly linked list. "Nodes"
 * are added and removed as <em>add</em> and <em>delete</em> operations are called.
 * Since every node holds references to two other nodes, it takes slightly more memory
 * per element to store. While insignificant for smaller dequeues, this extra space can
 * become significant for a moderately sized deque.
 *
 * <p>
 * The <em>add</em> and <em>delete</em> operations from both the front and the back
 * take constant time. Operations <em>contains</em> and <em>equals</em> take
 * <code>&theta;(n)</code> time. The <em>copy</em> and <em>deepcopy</em> operations
 * also take <code>&theta;(n)</code> time and extra space. All other operations take
 * constant time.
 * </p>
 *
 * @param <E> The type of element in the deque.
 * @author Aniket Kumar
 */
public final class LinkedDeque<E> implements Deque<E> {
  private Node front;       // the first node in the deque
  private Node back;        // the last node in the deque
  private int length = 0;   // number of nodes in the deque

  /**
   * Instantiate and return an empty {@link LinkedDeque} object.
   */
  public LinkedDeque() {
    front = null;
    back = null;
  }

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * {@inheritDoc}
   *
   * @param obj The other Object to compare {@code this} with for equality.
   * @return {@code true} if {@code obj} is equal to this deque,
   *     {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this)                return true;
    if (obj == null)                return false;
    if (!(obj instanceof Deque))    return false;
    Deque<?> that = (Deque<?>) obj;
    if (this.size() != that.size()) return false;

    // compare each element
    Iterator<?> itor = that.iterator();
    for (E elmt : this) {
      if (!Objects.equals(itor.next(), elmt)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Get a string representation of the deque.
   * Primarily for debugging and helping find bugs in client code.
   *
   * @return A string.
   */
  @Override
  @SuppressWarnings("DuplicatedCode")
  public String toString() {
    String className = this.getClass().getSimpleName();

    if (isEmpty()) return className + "[0] [ ]";

    StringBuilder sb = new StringBuilder(className).append("[").append(size()).append("] [ ");
    for (E elmt : this) {
      sb.append(elmt).append(", ");
    }
    sb.setLength(sb.length() - 2);
    return sb.append(" ]B)").toString();
  }

  /* **************************************************************************
   * Section: Iterable Methods
   ************************************************************************** */

  /**
   * Get an iterator that produces the elements of the deque from the front
   * to the back.
   *
   * @return An iterator.
   *
   * @see #reverse()
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
   * @return The count of elements present in the deque.
   */
  @Override
  public int size() {
    return length;
  }

  /**
   * How many elements are present in the deque?
   *
   * @return The count of elements present in the deque.
   */
  @Override
  public boolean isEmpty() {
    return length == 0;
  }

  /**
   * Does {@code elmt} exist in the deque?
   *
   * @param elmt The element to check existence of.
   * @return {@code true} if {@code elmt} exists, {@code false} otherwise.
   */
  @Override
  public boolean contains(E elmt) {
    for (E dequeElmt : this) {
      if (Objects.equals(elmt, dequeElmt)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Clear the deque of all its elements.
   * Sets all internal state members to their <em>default</em> initial state.
   */
  @Override
  public void clear() {
    length = 0;
    front = null;
    back = null;
  }

  /**
   * Return a shallow copy of the deque.
   * A shallow copy creates a copy of the deque but not the elements in the
   * deque.
   *
   * @return A shallow copy of the deque.
   *
   * @see #deepcopy(Function copyFn)
   */
  @Override
  public Deque<E> copy() {
    return deepcopy(Function.identity());
  }

  /**
   * Returns a deepcopy of the deque.
   * A deepcopy creates a copy of the deque and populates it with copies of the
   * original elements.
   *
   * @param copyFn A {@link Function} that takes original element as the
   *               argument and returns a deepcopy of that element.
   * @return A deepcopy of the deque.
   *
   * @throws IllegalArgumentException If {@code copyFn} is {@code null}.
   * @see #copy()
   */
  @Override
  public Deque<E> deepcopy(Function<? super E, E> copyFn) {
    if (copyFn == null) throw new IllegalArgumentException("argument to deepcopy() is null");

    LinkedDeque<E> cp = new LinkedDeque<>();
    for (E elmt : this) {
      cp.addBack(copyFn.apply(elmt));
    }

    return cp;
  }

  /* **************************************************************************
   * Section: Deque Methods
   ************************************************************************** */

  /**
   * Get an iterable that produces the elements from the back to the front.
   *
   * @return An iterable.
   *
   * @see #iterator()
   */
  @Override
  public Iterable<E> reverse() {
    return () -> new DoublyLinkedListIterator(back, false);
  }

  /**
   * Add {@code elmt} to the front of the deque.
   *
   * @param elmt The element to add.
   */
  @Override
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
   * Add {@code elmt} to the back of the deque.
   *
   * @param elmt The element to add.
   */
  @Override
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
   * Delete and return the element at the front of the deque.
   *
   * @return The deleted element that was at the front.
   *
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
   * Delete and return the element at the back of the deque.
   *
   * @return The deleted element that was at the back.
   *
   * @throws NoSuchElementException If deque is empty (underflow).
   */
  @Override
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
   * Return the element that was at the front of the deque, without
   * deleting it.
   *
   * @return The element that is at front of the deque.
   *
   * @throws NoSuchElementException If deque is empty (underflow).
   */
  @Override
  public E front() {
    if (isEmpty()) throw new NoSuchElementException("Underflow: can't peek at empty deque");

    return front.elmt;
  }

  /**
   * Return the element that was at the back of the deque, without
   * deleting it.
   *
   * @return The element that is at back of the deque.
   *
   * @throws NoSuchElementException If deque is empty (underflow).
   */
  @Override
  public E back() {
    if (isEmpty()) throw new NoSuchElementException("Underflow: can't peek at empty deque");

    return back.elmt;
  }

  /* **************************************************************************
   * Section: Helper Classes and Methods
   ************************************************************************** */

  /**
   * Represents a node in a doubly linked list. Holds the element (client value) and a
   * reference to the previous and next nodes.
   */
  private class Node {
    E elmt;             // holds the client value
    Node prev = null;   // reference to the previous node in the list
    Node next = null;   // reference to the next node in the list
  }

  /**
   * An {@link Iterator} class that supports iteration over a doubly linked list from
   * either direction.
   */
  private class DoublyLinkedListIterator implements Iterator<E> {
    private final boolean frontToBack;    // should the iterator go from front to back?
    private Node current;                 // the node whose value client gets next

    /**
     * Instantiate and return a new {@link DoublyLinkedListIterator} object.
     *
     * @param startNode   The node to start iterating from.
     * @param frontToBack Pass {@code true} if the iterator should move front
     *                    from {@code startNode}, {@code false} for backwards.
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
