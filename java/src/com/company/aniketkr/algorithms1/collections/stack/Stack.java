package com.company.aniketkr.algorithms1.collections.stack;

import com.company.aniketkr.algorithms1.collections.Collection;

/**
 * A stack is a collection of objects that follows a <em>FILO</em> order. Alongside
 * all {@link Collection} operations, this interface guarantees support for the usual
 * <em>push</em>, <em>pop</em> and <em>peek</em> operations.
 *
 * <p>The <em>copy</em> operation returns an instance of {@link Stack}. The iteration of
 * elements of the stack happens in <em>FILO</em> order.</p>
 *
 * @param <E> The type of elements in the stack.
 * @author Aniket Kumar
 */
public interface Stack<E> extends Collection<E> {

  /**
   * Push the element {@code elmt} onto the stack.
   *
   * @param elmt The element to push.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  void push(E elmt);

  /**
   * Pop/Remove the last inserted element off the stack.
   *
   * @return The removed element.
   * @throws java.util.NoSuchElementException If the stack is empty (underflow).
   */
  E pop();

  /**
   * Return the last inserted element from the stack, without removing it.
   *
   * @return The last inserted element.
   * @throws java.util.NoSuchElementException If the stack is empty (underflow).
   */
  E peek();

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * Get a shallow copy of the stack.
   *
   * @return A new stack.
   */
  @Override
  Stack<E> copy();
}
