package com.company.aniketkr.algorithms1.collections.queue;

import com.company.aniketkr.algorithms1.collections.Collection;

public interface Queue<E> extends Collection<E> {

  void enqueue(E elmt);

  E dequeue();

  E peek();

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  @Override
  Queue<E> copy();
}