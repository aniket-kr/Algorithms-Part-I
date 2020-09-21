package com.company.aniketkr.algorithms1.collections.stack;

import com.company.aniketkr.algorithms1.collections.Collection;

import java.util.Iterator;
import java.util.function.Function;

/**
 * A stack is a collection of objects that follows <em>First-In-Last-Out</em> (FILO)
 * order.
 * Alongside all {@link Collection} operations, this interface guarantees support for
 * the usual <em>push</em>, <em>pop</em> and <em>peek</em> operations. The <em>copy</em>
 * and <em>deepcopy</em> operations return an instance of {@link Stack}. The iteration
 * over elements of the stack happens in <em>FILO</em> order.
 *
 * @param <E> The type of element in the stack.
 * @author Aniket Kumar
 */
public interface Stack<E> extends Collection<E> {

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  /**
   * Is the stack equal to the given object {@code obj}?
   * A stack will be deemed equal to {@code obj} if {@code obj} satisfies the following
   * conditions:
   * <ol>
   *   <li>{@code obj} is a {@link Stack} object or one of its subclasses.</li>
   *   <li>
   *     All the elements of {@code obj} are equal to the corresponding elements in this
   *     stack.
   *   </li>
   * </ol>
   *
   * <p>
   *   Because of Java's use of erasure, it is currently not possible to differentiate
   *   between the type of elements in two empty stacks. This implies that if two
   *   empty {@link Stack} objects - which store two different types of elements, are
   *   compared with {@code equals()}, then the result will always be {@code true}.
   * </p>
   *
   * @param obj The other Object to compare {@code this} with for equality.
   * @return {@code true} if {@code obj} is equal to this stack,
   *     {@code false} otherwise.
   */
  @Override
  boolean equals(Object obj);

  /**
   * Get a string representation of the stack.
   * Primarily for debugging and helping find bugs in client code.
   *
   * @return A string.
   */
  @Override
  String toString();

  /* **************************************************************************
   * Section: Iterable Methods
   ************************************************************************** */

  /**
   * Get an iterator that produces the elements of the stack in <em>FILO</em> order.
   *
   * @return An iterator.
   */
  @Override
  Iterator<E> iterator();

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * How many elements are present in the stack?
   *
   * @return The count of elements present in the stack.
   */
  @Override
  int size();

  /**
   * Is the stack empty?
   *
   * @return {@code true} if stack is empty, {@code false} otherwise.
   */
  @Override
  boolean isEmpty();

  /**
   * Does {@code elmt} exist in the stack?
   *
   * @param elmt The element to check existence of.
   * @return {@code true} if {@code elmt} exists, {@code false} otherwise.
   *
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  @Override
  boolean contains(E elmt);

  /**
   * Clear the stack of all its elements.
   * Sets all internal state members to their <em>default</em> initial state.
   */
  @Override
  void clear();

  /**
   * Return a shallow copy of the stack.
   * A shallow copy creates a copy of the stack but not the elements in the
   * stack.
   *
   * @return A shallow copy of the stack.
   *
   * @see #deepcopy(Function elmtCopyFunction)
   */
  @Override
  Stack<E> copy();

  /**
   * Returns a deepcopy of the stack.
   * A deepcopy creates a copy of the stack and populates it with copies of the
   * original elements.
   *
   * @param copyFn A {@link Function} that takes original element as the
   *               argument and returns a deepcopy of that element.
   * @return A deepcopy of the stack.
   *
   * @throws IllegalArgumentException If {@code copyFn} is {@code null}.
   * @see #copy()
   */
  @Override
  Stack<E> deepcopy(Function<? super E, E> copyFn);

  /* **************************************************************************
   * Section: Stack Methods
   ************************************************************************** */

  /**
   * Push the given element onto the stack.
   *
   * @param elmt The new element to add to the stack.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  void push(E elmt);

  /**
   * Pop the last pushed element off the stack.
   *
   * @return The removed/popped element.
   *
   * @throws java.util.NoSuchElementException If the stack is empty; <em>underflow</em>.
   */
  E pop();

  /**
   * Get the element that was last pushed onto the stack, without popping it off.
   *
   * @return The last pushed element.
   *
   * @throws java.util.NoSuchElementException If the stack is empty; <em>underflow</em>.
   */
  E peek();
}