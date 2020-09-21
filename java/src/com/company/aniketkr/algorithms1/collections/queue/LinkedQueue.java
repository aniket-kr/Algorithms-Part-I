package com.company.aniketkr.algorithms1.collections.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;


/**
 * Implements the {@link Queue} interface using a singly linked list. The
 * "nodes" to the list are added/removed as elements are <em>enqueued/dequeued</em>
 * from the queue.
 *
 * <p>
 * The <em>enqueue</em>, <em>dequeue</em> and <em>peek</em> operations take constant time.
 * The <em>equals</em> and <em>contains</em> operations take <code>&theta;(n)</code> time.
 * The operations to <em>copy</em>a nd <em>deepcopy</em> take <code>&theta;(n)</code> time.
 * All other operations take constant time.
 * </p>
 *
 * @param <E> The type of element in the queue.
 * @author Aniket Kumar
 */
public final class LinkedQueue<E> implements Queue<E> {
  private Node head;        // start or front of the queue
  private Node tail;        // end or back of the queue
  private int length = 0;   // length of the queue

  /**
   * Initialize and return an empty LinkedQueue object.
   */
  public LinkedQueue() {
    head = null;
    tail = null;
  }

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * {@inheritDoc}
   *
   * @param obj The other Object to compare {@code this} with for equality.
   * @return {@code true} if {@code obj} is equal to this queue,
   *     {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)                return true;
    if (obj == null)                return false;
    if (!(obj instanceof Queue))    return false;
    Queue<?> that = (Queue<?>) obj;
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
   * Get a string representation of the queue.
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
    return sb.append(" ]").toString();
  }

  /* **************************************************************************
   * Section: Iterable Methods
   ************************************************************************** */

  /**
   * Get an iterator that produces the elements of the queue in <em>FIFO</em> order.
   *
   * @return An iterator.
   */
  @Override
  public Iterator<E> iterator() {
    return new NodeIterator();
  }

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * How many elements are present in the queue?
   *
   * @return The count of elements present in the queue.
   */
  @Override
  public int size() {
    return length;
  }

  /**
   * Is the queue empty?
   *
   * @return {@code true} if queue is empty, {@code false} otherwise.
   */
  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Does {@code elmt} exist in the queue?
   *
   * @param elmt The element to check existence of.
   * @return {@code true} if {@code elmt} exists, {@code false} otherwise.
   *
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  @Override
  public boolean contains(E elmt) {
    if (elmt == null) throw new IllegalArgumentException("argument to contains() is null");

    for (E queueElmt : this) {
      if (queueElmt.equals(elmt)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Clear the queue of all its elements.
   * Sets all internal state members to their <em>default</em> initial state.
   */
  @Override
  public void clear() {
    head = null;
    tail = null;
    length = 0;
  }

  /**
   * Return a shallow copy of the queue.
   * A shallow copy creates a copy of the queue but not the elements in the
   * queue.
   *
   * @return A shallow copy of the queue.
   *
   * @see #deepcopy(Function copyFn)
   */
  @Override
  public Queue<E> copy() {
    return deepcopy(Function.identity());
  }

  /**
   * Returns a deepcopy of the queue.
   * A deepcopy creates a copy of the queue and populates it with copies of the
   * original elements.
   *
   * @param copyFn A {@link Function} that takes original element as the
   *               argument and returns a deepcopy of that element.
   * @return A deepcopy of the queue.
   *
   * @throws IllegalArgumentException If {@code copyFn} is {@code null} or it returns
   *                                  any {@code null} values.
   * @see #copy()
   */
  @Override
  public Queue<E> deepcopy(Function<? super E, E> copyFn) {
    if (copyFn == null) throw new IllegalArgumentException("argument to deepcopy() is null");
    copyFn = copyFn.andThen(elmt -> {
      if (elmt == null) throw new IllegalArgumentException("copyFn mustn't return null");
      return elmt;
    });

    Queue<E> cp = new LinkedQueue<>();
    for (E elmt : this) {
      cp.enqueue(copyFn.apply(elmt));
    }

    return cp;
  }

  /* **************************************************************************
   * Section: Queue Methods
   ************************************************************************** */

  /**
   * Add the given element {@code elmt} to the back of the queue.
   *
   * @param elmt The element to add to the queue.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  @Override
  public void enqueue(E elmt) {
    if (elmt == null) throw new IllegalArgumentException("argument to enqueue() is null");

    Node node = new Node(elmt);
    if (isEmpty()) {
      head = node;
      tail = node;
    } else {
      head.next = node;
      head = node;
    }
    length++;
  }

  /**
   * Remove the first element from the front of the queue.
   *
   * @return The removed element.
   *
   * @throws NoSuchElementException If queue is empty; <em>underflow</em>.
   */
  @Override
  public E dequeue() {
    if (isEmpty()) throw new NoSuchElementException("Underflow: can't dequeue() from empty queue");

    E elmt = tail.elmt;

    if (size() == 1) {
      tail = null;
      head = null;
    } else {
      tail = tail.next;
    }
    length--;

    return elmt;
  }

  /**
   * Return the first element from the front of the queue, without removing it.
   *
   * @return The first element of the queue.
   *
   * @throws NoSuchElementException If queue is empty; <em>underflow</em>.
   */
  @Override
  public E peek() {
    if (isEmpty()) throw new NoSuchElementException("Underflow: can't peek() at empty queue");

    return tail.elmt;
  }

  /* **************************************************************************
   * Section: Helper Methods and Classes
   ************************************************************************** */

  /**
   * Represents a Node in a singly linked list.
   * Each node hold the client value and a reference to the next node in the list.
   */
  private class Node {
    E elmt;             // holds the client value
    Node next = null;   // reference to the next node

    /**
     * Initialize and return a new Node object with client value set to {@code elmt}.
     *
     * @param elmt The client value node shall hold.
     */
    Node(E elmt) {
      this.elmt = elmt;
    }
  }

  /**
   * An {@link Iterator} class that iterates over the values stores in the nodes of a
   * linked list.
   */
  private class NodeIterator implements Iterator<E> {
    private Node current = tail;  // start with this node

    /**
     * Can the iterator produce another value?
     *
     * @return {@code false} if the iterator has been depleted, {@code true} otherwise.
     */
    @Override
    public boolean hasNext() {
      return current != null;
    }

    /**
     * Produce the next value from the iterator.
     *
     * @return The next value.
     *
     * @throws NoSuchElementException If called on a depleted iterator.
     */
    @Override
    public E next() {
      if (!hasNext()) throw new NoSuchElementException("iterator depleted");

      E elmt = current.elmt;
      current = current.next;
      return elmt;
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
