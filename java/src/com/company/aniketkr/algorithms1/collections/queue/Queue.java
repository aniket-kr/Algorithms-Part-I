package com.company.aniketkr.algorithms1.collections.queue;

import com.company.aniketkr.algorithms1.collections.Collection;

/**
 * A queue is a collection of objects that follows a <em>FIFO</em> order. Alongside
 * all {@link Collection} operations, this interface guarantees support for the usual
 * <em>enqueue</em>, <em>dequeue</em> and <em>peek</em> operations.
 *
 * <p>The <em>copy</em> operation returns a new {@link Queue} object. The iteration of
 * the elements of the queue happens in <em>FIFO</em> order.</p>
 *
 * @param <E> The type of elements in the queue.
 * @author Aniket Kumar
 */
public interface Queue<E> extends Collection<E> {

  /**
   * Add the given element {@code elmt} to the back of the queue.
   *
   * @param elmt The element to add to the queue.
   * @throws IllegalArgumentException If {@code elmt} is {@code null}.
   */
  void enqueue(E elmt);

  /**
   * Remove the first element from the front of the queue.
   *
   * @return The removed element.
   * @throws java.util.NoSuchElementException If queue is empty (underflow).
   */
  E dequeue();

  /**
   * Return the first element from the front of the queue, without removing it.
   *
   * @return The first element of the queue.
   * @throws java.util.NoSuchElementException If queue is empty (underflow).
   */
  E peek();

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  /**
   * Get a shallow copy of the queue.
   *
   * @return A new queue.
   */
  @Override
  Queue<E> copy();
}