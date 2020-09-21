package com.company.aniketkr.algorithms1.collections.stack;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;


/**
 * Implements the {@link Stack} interface using a singly linked list. The "nodes"
 * to the list are added/removed as elements are <em>pushed to/popped off</em> the
 * stack.
 *
 * <p>
 * The <em>push</em>, <em>pop</em>, <em>peek</em> operations take constant time.
 * The <em>equals</em> and <em>contains</em> operations take <code>&theta;(n)</code>
 * time. Operations to <em>copy</em> and <em>deepcopy</em> take <code>&theta;(n)</code>
 * time. All other operations take constant time.
 * </p>
 *
 * @param <E> The type of element in the stack.
 */
public final class LinkedStack<E> implements Stack<E> {
  private Node head;              // first `node` in the linked list
  private int length = 0;         // total number of nodes in list

  /**
   * Initialize and return a new empty LinkedStack object.
   */
  public LinkedStack() {
    head = null;
  }

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * {@inheritDoc}
   *
   * @param obj The other Object to compare {@code this} with for equality.
   * @return {@code true} if {@code obj} is equal to this stack,
   *     {@code false} otherwise.
   */
  @Override
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
   * Get a string representation of the stack.
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
   * Get an iterator that produces the elements of the stack in <em>FILO</em> order.
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
   * @return The count of elements present in the stack.
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
   * @param elmt The element to check existence of.
   * @return {@code true} if {@code elmt} exists, {@code false} otherwise.
   *
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
   * Clear the stack of all its elements.
   * Sets all internal state members to their <em>default</em> initial state.
   */
  public void clear() {
    head = null;
    length = 0;
  }

  /**
   * Return a shallow copy of the stack.
   * A shallow copy creates a copy of the stack but not the elements in the
   * stack.
   *
   * @return A shallow copy of the stack.
   *
   * @see #deepcopy(Function copyFn)
   */
  @Override
  public Stack<E> copy() {
    return deepcopy(Function.identity());
  }

  /**
   * Returns a deepcopy of the stack.
   * A deepcopy creates a copy of the stack and populates it with copies of the
   * original elements.
   *
   * @param copyFn A {@link Function} that takes original element as the
   *               argument and returns a deepcopy of that element.
   * @return A deepcopy of the stack.
   *
   * @throws IllegalArgumentException If {@code elmtCopyFunction} is {@code null}
   *                                  or it returns any {@code null} values.
   * @see #copy()
   */
  @Override
  public Stack<E> deepcopy(Function<? super E, E> copyFn) {
    if (copyFn == null) throw new IllegalArgumentException("argument to deepcopy() is null");
    copyFn = copyFn.andThen(elmt -> {
      if (elmt == null) throw new IllegalArgumentException("copyFn mustn't return null values");
      return elmt;
    });

    LinkedStack<E> cp = new LinkedStack<>();

    if (size() > 0) {
      Iterator<E> itor = this.iterator();
      cp.head = new Node(copyFn.apply(itor.next()));

      Node tail = cp.head;  // `tail` points to the end of the incomplete stack
      Node node;
      while (itor.hasNext()) {
        node = new Node(copyFn.apply(itor.next()));
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
   * Push the given element onto the stack.
   *
   * @param elmt The new element to add to the stack.
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
   * Pop the last pushed element off the stack.
   *
   * @return The removed/popped element.
   *
   * @throws NoSuchElementException If the stack is empty; <em>underflow</em>.
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
   * Get the element that was last pushed onto the stack, without popping it off.
   *
   * @return The last pushed element.
   *
   * @throws NoSuchElementException If the stack is empty; <em>underflow</em>.
   */
  @Override
  public E peek() {
    if (isEmpty()) throw new NoSuchElementException("underflow: can't peek() at an empty stack");

    return head.elmt;
  }

  /* **************************************************************************
   * Section: Helper Methods and Classes
   ************************************************************************** */

  /**
   * Represents a single node in a singly linked list.
   * Every {@code Node} has holds the client value and a reference to the next node
   * in the linked list.
   */
  private class Node {
    E elmt;               // holds client value
    Node next = null;     // reference to next node in list

    Node(E elmt) {
      this.elmt = elmt;
    }
  }

  /**
   * An {@link Iterator} class that iterates over the linked list.
   */
  private class NodeIterator implements Iterator<E> {
    private Node current = head;  // node to start with

    /**
     * Can the iterator produce another value?
     *
     * @return {@code false} if the iterator has been depleted, otherwise {@code true}.
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
     * @throws NoSuchElementException If called after iterator has been depleted.
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
