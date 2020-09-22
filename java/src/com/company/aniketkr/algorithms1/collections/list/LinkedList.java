package com.company.aniketkr.algorithms1.collections.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;


/**
 * Implements the {@link List} interface as a singly linked list with nodes.
 * Nodes are added as elements are <em>added</em>/<em>inserted</em> and removed
 * as they are <em>removed</em>/<em>deleted</em>.
 *
 * <p>
 * The <em>add</em> and <em>add-all</em> operations take constant and <code>&theta;(n)
 * </code> time respectively. The <em>insert</em>, <em>insert-all</em>, <em>remove</em,
 * <em>delete</em>, </><em>contains</em>, <em>equals</em> and <em>index-of</em>
 * operations all take <code>&theta;(n)</code> time. If the upper and lower
 * bounds are provided to the <em>index-of</em> operation, then the time taken is
 * proportional to the number of elements that fall in the given range. The operations
 * <em>get</em> and <em>set</em> also take <code>&theta;(n)</code> time. Operations
 * to <em>copy</em> and <em>deepcopy</em> also take <code>&theta;(n)</code> time. All
 * other operations take constant time.
 * </p>
 *
 * <p>
 * For all operations (barring <em>copy</em> and <em>deepcopy</em>), constant extra memory
 * is used. It is strongly recommended to use the <em>addAll</em> and <em>insertAll</em>
 * operations for inserting multiple values, over <em>add</em> and <em>insert</em>. These
 * methods are internally optimised.
 * </p>
 *
 * @param <E> The type of element in the list.
 * @author Aniket Kumar
 */
public final class LinkedList<E> implements List<E> {
  private Node head;       // the first node in the list
  private Node tail;       // the last node in the list
  private int length = 0;  // number of nodes in the list

  /**
   * Initialize and return an empty LinkedList object.
   */
  public LinkedList() {
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
   * @return {@code true} if {@code obj} is equal to this list,
   *     {@code false} otherwise.
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
      if (!Objects.equals(elmt, itor.next())) {
        return false;
      }
    }
    return true;
  }

  /**
   * Get a string representation of the list.
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
   * Get an iterator that produces the elements of the list in increasing order of
   * their indices.
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
   * How many elements are present in the list?
   *
   * @return The count of elements present in the list.
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
   * Does {@code elmt} exist in the list?
   *
   * @param elmt The element to check existence of.
   * @return {@code true} if {@code elmt} exists, {@code false} otherwise.
   */
  @Override
  public boolean contains(E elmt) {
    return indexOf(elmt) != -1;
  }

  /**
   * Clear the list of all its elements.
   * Sets all internal state members to their <em>default</em> initial state.
   */
  @Override
  public void clear() {
    head = null;
    tail = null;
    length = 0;
  }

  /**
   * Return a shallow copy of the list.
   * A shallow copy creates a copy of the list but not the elements in the
   * list.
   *
   * @return A shallow copy of the list.
   *
   * @see #deepcopy(Function copyFn)
   */
  @Override
  public List<E> copy() {
    return deepcopy(Function.identity());
  }

  /**
   * Returns a deepcopy of the list.
   * A deepcopy creates a copy of the list and populates it with copies of the
   * original elements.
   *
   * @param copyFn A {@link Function} that takes original element as the
   *               argument and returns a deepcopy of that element.
   * @return A deepcopy of the list.
   *
   * @throws IllegalArgumentException If {@code copyFn} is {@code null}.
   * @see #copy()
   */
  @Override
  public List<E> deepcopy(Function<? super E, E> copyFn) {
    if (copyFn == null) throw new IllegalArgumentException("argument to deepcopy() is null");

    LinkedList<E> cp = new LinkedList<>();
    for (E elmt : this) {
      cp.add(copyFn.apply(elmt));
    }

    return cp;
  }

  /* **************************************************************************
   * Section: List Methods
   ************************************************************************** */

  /**
   * Add {@code elmt} to the end of the list.
   *
   * @param elmt The element.
   * @see #insert(int atIndex, E elmt)
   */
  @Override
  public void add(E elmt) {
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
   * Add all the elements in {@code elmts} array to the end of the list.
   *
   * @param elmts The array with elements to add.
   * @throws IllegalArgumentException If {@code elmts} is {@code null}.
   * @see #insertAll(int fromIndex, E[] elmts)
   */
  @Override
  public void addAll(E[] elmts) {
    if (elmts == null) throw new IllegalArgumentException("argument to addAll() is null");

    for (E elmt : elmts) {
      add(elmt);
    }
  }

  /**
   * Add all the elements produced by {@code elmts}, to the end of the
   * list.
   *
   * @param elmts The iterable that produces elements to add.
   * @throws IllegalArgumentException If {@code elmts}, or any of the elements it
   *                                  produces, is {@code null}.
   * @see #insertAll(int fromIndex, Iterable elmts)
   */
  @Override
  public void addAll(Iterable<? extends E> elmts) {
    if (elmts == null) throw new IllegalArgumentException("argument to addAll() is null");

    for (E elmt : elmts) {
      add(elmt);
    }
  }

  /**
   * Insert the given element {@code elmt} at the index {@code atIndex}.
   *
   * @param elmt    The element.
   * @param atIndex The index to insert at.
   * @throws IndexOutOfBoundsException If {@code atIndex} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   * @see #add(E elmt)
   */
  @Override
  public void insert(int atIndex, E elmt) {
    if (!isInRange(atIndex)) throw new IndexOutOfBoundsException("invalid index: " + atIndex);

    Node node = new Node(elmt);
    Node prevNode = (atIndex == 0) ? head : nodeAt(atIndex - 1);

    node.next = prevNode.next;
    prevNode.next = node;
    length++;
  }

  /**
   * Insert all the elements in {@code elmts} into the list, starting from
   * the index {@code fromIndex}.
   *
   * @param elmts     The array of elements to insert.
   * @param fromIndex The index at which the first element from {@code elmts}
   *                  should appear.
   * @throws IllegalArgumentException  If {@code elmts} is {@code null}.
   * @throws IndexOutOfBoundsException If {@code fromIndex} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   * @see #addAll(E[] elmts)
   */
  @Override
  @SuppressWarnings("DuplicatedCode")  // insertAll() overload
  public void insertAll(int fromIndex, E[] elmts) {
    if (elmts == null)         throw new IllegalArgumentException("1st argument to insertAll() is null");
    if (!isInRange(fromIndex)) throw new IndexOutOfBoundsException("invalid index: " + fromIndex);

    Node prevNode = (fromIndex == 0) ? head : nodeAt(fromIndex - 1);
    Node successor = prevNode.next;

    for (E elmt : elmts) {
      prevNode.next = new Node(elmt);
      prevNode = prevNode.next;
      length++;
    }

    // prevNode is now references last inserted Node
    prevNode.next = successor;
  }

  /**
   * Insert all the elements produced by {@code elmts} into the list, starting
   * from the index {#code fromIndex}.
   *
   * @param elmts     The iterable that produces elements to insert.
   * @param fromIndex The index at which the first element produced by {@code elmts}
   *                  should appear.
   * @throws IllegalArgumentException  If {@code elmts} is {@code null}.
   * @throws IndexOutOfBoundsException If {@code fromIndex} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   * @see #addAll(Iterable elmts)
   */
  @Override
  @SuppressWarnings("DuplicatedCode")  // insertAll() overload
  public void insertAll(int fromIndex, Iterable<? extends E> elmts) {
    if (elmts == null)         throw new IllegalArgumentException("1st argument to insertAll() is null");
    if (!isInRange(fromIndex)) throw new IndexOutOfBoundsException("invalid index: " + fromIndex);

    Node prevNode = (fromIndex == 0) ? head : nodeAt(fromIndex - 1);
    Node successor = prevNode.next;

    for (E elmt : elmts) {
      prevNode.next = new Node(elmt);
      prevNode = prevNode.next;
      length++;
    }

    // prevNode is now references last inserted Node
    prevNode.next = successor;
  }

  /**
   * Get element from the list, whose index is {@code index}.
   *
   * @param index The index.
   * @return The element at {@code index}.
   *
   * @throws IndexOutOfBoundsException If {@code index} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   */
  @Override
  public E get(int index) {
    if (!isInRange(index)) throw new IndexOutOfBoundsException("invalid index: " + index);

    return nodeAt(index).elmt;
  }

  /**
   * Set {@code elmt} at index {@code index}.
   * The old value stored at the given index is discarded.
   *
   * @param index The index to change value at.
   * @param elmt  The new element.
   * @throws IndexOutOfBoundsException If {@code index} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   */
  @Override
  public void set(int index, E elmt) {
    if (!isInRange(index)) throw new IndexOutOfBoundsException("invalid index: " + index);

    nodeAt(index).elmt = elmt;
  }

  /**
   * Get the index of the first occurrence of {@code elmt} in the list.
   *
   * @param elmt The element to look for.
   * @return The index of first occurrence of {@code elmt} in the list; -1 if
   *     {@code elmt} does not exist in the list.
   */
  @Override
  public int indexOf(E elmt) {
    return indexOf(elmt, 0, size());
  }

  /**
   * Get the index of the first occurrence of {@code elmt} between indices {@code fromIndex}
   * and {@code toIndex}.
   * If {@code fromIndex} happens to be larger than or equal to {@code toIndex}, then -1
   * is returned.
   *
   * @param elmt      The element to look for.
   * @param fromIndex The index to start looking at (inclusive).
   * @param toIndex   The index to look upto (exclusive).
   * @return The index of the first occurrence of {@code elmt} in the range, {@literal -1}
   *     if {@code elmt} does not exist in the given range.
   *
   * @throws IndexOutOfBoundsException If either {@code fromIndex} or {@code toIndex} is
   *                                   not in range <code>[0, {@link #size()}]</code>.
   */
  @Override
  public int indexOf(E elmt, int fromIndex, int toIndex) {
    if (!isInRange(fromIndex)) throw new IndexOutOfBoundsException("invalid fromIndex: " + fromIndex);
    if (!(isInRange(toIndex) || toIndex == size())) {
      throw new IndexOutOfBoundsException("invalid toIndex: " + toIndex);
    }

    Node current = nodeAt(fromIndex);

    for (int i = fromIndex; i < toIndex; i++) {
      if (Objects.equals(current.elmt, elmt)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Delete the element at the given index in the list and return it.
   *
   * @param index The index to delete element at.
   * @return The deleted element.
   *
   * @throws IndexOutOfBoundsException If {@code index} is not in range <code>
   *                                   [0, {@link #size()})</code>.
   * @see #remove(E elmt)
   */
  @Override
  public E delete(int index) {
    if (!isInRange(index)) throw new IndexOutOfBoundsException("invalid index: " + index);

    E elmt = unlinkNext((size() == 0) ? null : nodeAt(index - 1)).elmt;
    length--;
    return elmt;
  }

  /**
   * Remove the first occurrence of the element {@code elmt} in the list and return its
   * index.
   *
   * @param elmt The element to remove.
   * @return The index of the now removed element; -1 if {@code elmt} did not exist in
   *     the list.
   *
   * @see #delete(int index)
   */
  @Override
  public int remove(E elmt) {
    Node prevNode = (size() == 1) ? null : head;
    Node current = head;
    for (int i = 0; i < size(); i++) {
      if (Objects.equals(current.elmt, elmt)) {
        unlinkNext(prevNode);
        length--;
        return i;
      }
      prevNode = current;
      current = current.next;
    }

    return -1;
  }

  /* **************************************************************************
   * Section: Helper Classes and Methods
   ************************************************************************** */

  /**
   * Is index {@code i} in the range <code>[0, {@link #size()})</code>?
   *
   * @param i The index to check for.
   * @return {@code true} if {@code i} falls in range, {@code false} otherwise.
   */
  private boolean isInRange(int i) {
    return i >= 0 && i < length;
  }

  /**
   * Get the node at the given index {@code index}.
   * Does not validate the index; it is an internal private method.
   *
   * @param index The node with this index will be returned.
   * @return The node with index {@code index}.
   */
  private Node nodeAt(int index) {
    Node current = head;
    for (int i = 0; i < index; i++) {
      current = current.next;
    }
    return current;
  }

  /**
   * Given a {@link Node} object {@code node}, remove the node <code>node.next</code>
   * from the linked list. <strong>The method does not change the value of the member field
   * {@link #length}.</strong>
   *
   * <p>
   * By the convention followed in the method, if {@code node} is {@code null}, it is assumed that
   * the node to remove is the head and it is the last node in the list. For all other values, the
   * particular node {@code node.next} is removed from the chain.
   * </p>
   *
   * @param node The node which precedes the node to be removed/unlinked from the list.
   * @return The now deleted/unlinked node.
   */
  private Node unlinkNext(Node node) {
    Node deletedNode;

    if (node == null) {
      deletedNode = head;
      head = null;
      tail = null;

    } else {

      deletedNode = node.next;
      node.next = node.next.next;
    }

    return deletedNode;
  }

  /**
   * Represents a single node in the singly linked list. Each node holds the client value
   * in {@code elmt} field and a reference to the next node in the linked list in {@code next}
   */
  private class Node {
    E elmt;             // holds the client value
    Node next = null;   // reference to the next Node in list

    /**
     * Initialize and return a new Node object which holds {@code elmt} as the client
     * value.
     *
     * @param elmt The client value to hold.
     */
    Node(E elmt) {
      this.elmt = elmt;
    }
  }

  /**
   * A {@link Iterator} class that iterates over the nodes of a singly linked list.
   */
  private class NodeIterator implements Iterator<E> {
    private Node current = head;  // start of the linked list

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
     * Produce the next value from the iterator and return it.
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
