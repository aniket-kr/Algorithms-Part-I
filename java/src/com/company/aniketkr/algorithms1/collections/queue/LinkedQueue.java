package com.company.aniketkr.algorithms1.collections.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

// FIXME: proofread the docs

/**
 * Implements the {@link Queue} interface using a singly linked list. The
 * "nodes" to the list are added/removed as elements are <em>enqueued/dequeued</em>
 * from the queue.
 *
 * @param <E> The type of elements in the queue.
 * @author Aniket Kumar
 */
public final class LinkedQueue<E> implements Queue<E> {
  private Node head;
  private Node tail;
  private int length = 0;

  /**
   * Initialize and return a new LinkedQueue object.
   */
  public LinkedQueue() {
    head = null;
    tail = null;
  }

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * Check if this queue is equal to the given object.
   * Calls {@link Object#equals(Object that) equals()} on the elements to check if they
   * are equal.
   *
   * @param obj The other object to check for equality.
   * @return {@code true} if {@code obj} is semantically equal to this queue,
   *     {@code false} otherwise.
   * @implSpec As it currently stands, if two queue objects are empty, the {@code equals}
   *     method will always return {@code true}. This happens as no elements are available to
   *     compare.
   */
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
   * Return a string representation of the queue. Primarily for debugging.
   *
   * @return A string.
   */
  public String toString() {
    if (isEmpty()) return "LinkedQueue[0] [ ]";

    StringBuilder sb = new StringBuilder("LinkedQueue[").append(size()).append("] [ ");
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
   * Get an iterator object that produces the elements of the queue in its natural
   * order.
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
   * @return The count of number of elements in the queue.
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
   * @param elmt The element to check for.
   * @return {@code true} if {@code elmt} exists in the queue, {@code false}
   *     otherwise.
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
   * Clear the queue of all its elements. Set it to its instantiated state.
   */
  @Override
  public void clear() {
    head = null;
    tail = null;
    length = 0;
  }

  /**
   * Get a shallow copy of the queue.
   *
   * @return A new queue.
   */
  @Override
  public Queue<E> copy() {
    Queue<E> cp = new LinkedQueue<>();
    for (E elmt : this) {
      cp.enqueue(elmt);
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
   * @throws NoSuchElementException If queue is empty (underflow).
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
   * @throws NoSuchElementException If queue is empty (underflow).
   */
  @Override
  public E peek() {
    if (isEmpty()) throw new NoSuchElementException("Underflow: can't peek() at empty queue");

    return tail.elmt;
  }

  /* **************************************************************************
   * Section: Helper Methods and Classes
   ************************************************************************** */

  private class Node {
    E elmt;
    Node next = null;

    Node(E elmt) {
      this.elmt = elmt;
    }
  }

  private class NodeIterator implements Iterator<E> {
    private Node current = tail;

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
