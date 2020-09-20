package com.company.aniketkr.algorithms1.collections.stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

// TODO: docs under construction

/**
 * Implements the {@link Stack} interface using a singly linked list. The "nodes"
 * to the list are added/removed as elements are <em>pushed to/popped off</em> the
 * stack.
 *
 * @param <E> The type of elements in the stack.
 */
public final class LinkedStack<E> implements Stack<E> {
  private Node head;
  private int length = 0;

  /**
   * Initialize and return a new LinkedStack object.
   */
  public LinkedStack() {
    head = null;
  }

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * Check if this stack is equal to the given object.
   * Calls {@link Object#equals(Object that) equals()} on the elements to check if they
   * are equal.
   *
   * @param obj The other object to check for equality.
   * @return {@code true} if {@code obj} is semantically equal to this stack,
   *     {@code false} otherwise.
   * @implSpec As it currently stands, if two stack objects are empty, the {@code equals}
   *     method will always return {@code true}. This happens as no elements are available
   *     to compare.
   */
  public boolean equals(Object obj) {
    if (this == obj)                return true;
    if (obj == null)                return false;
    if (!(obj instanceof Stack))    return false;
    Stack<?> that = (Stack<?>) obj;
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
   * Return a string representation of the stack. Primarily for debugging.
   *
   * @return A string.
   */
  public String toString() {
    if (isEmpty()) return "Stack[0] [ ]";

    StringBuilder sb = new StringBuilder("Stack[").append(size()).append("] [ ");
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
   * Get an iterator object that produces the elements of the stack in its natural
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
   * How many elements are present in the stack?
   *
   * @return The count of number of elements in the stack.
   */
  @Override
  public int size() {
    return length;
  }

  /**
   * Is the stack empty?
   *
   * @return {@code true} if stack is empty, {@code false} otherwise.
   */
  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Does {@code elmt} exist in the stack?
   *
   * @param elmt The element to check for.
   * @return {@code true} if {@code elmt} exists in the stack, {@code false}
   *     otherwise.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  @Override
  public boolean contains(E elmt) {
    if (elmt == null) throw new IllegalArgumentException("argument to contains() is null");

    for (E stackElmt : this) {
      if (stackElmt.equals(elmt)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Clear the stack of all its elements. Set it to its instantiated state.
   */
  @Override
  public void clear() {
    head = null;
    length = 0;
  }

  /**
   * Get a shallow copy of the stack.
   *
   * @return A new stack.
   */
  @Override
  public Stack<E> copy() {
    LinkedStack<E> cp = new LinkedStack<>();

    if (size() > 0) {
      Iterator<E> itor = this.iterator();
      cp.head = new Node(itor.next());

      Node tail = cp.head;  // `tail` points to the end of the incomplete stack
      Node node;
      while (itor.hasNext()) {
        node = new Node(itor.next());
        tail.next = node;
        tail = node;
      }

      cp.length = this.length;
    }

    return cp;
  }

  /* **************************************************************************
   * Section: Stack Methods
   ************************************************************************** */

  /**
   * Push the element {@code elmt} onto the stack.
   *
   * @param elmt The element to push.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  @Override
  public void push(E elmt) {
    if (elmt == null) throw new IllegalArgumentException("argument to push() is null");

    Node node = new Node(elmt);
    node.next = head;
    head = node;
    length++;
  }

  /**
   * Pop/Remove the last inserted element off the stack.
   *
   * @return The removed element.
   * @throws NoSuchElementException If the stack is empty (underflow).
   */
  @Override
  public E pop() {
    if (isEmpty()) throw new NoSuchElementException("underflow: can't pop() from empty stack");

    E elmt = head.elmt;
    head = head.next;
    length--;

    return elmt;
  }

  /**
   * Return the last inserted element from the stack, without removing it.
   *
   * @return The last inserted element.
   * @throws NoSuchElementException If the stack is empty (underflow).
   */
  @Override
  public E peek() {
    if (isEmpty()) throw new NoSuchElementException("underflow: can't peek() at an empty stack");

    return head.elmt;
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
