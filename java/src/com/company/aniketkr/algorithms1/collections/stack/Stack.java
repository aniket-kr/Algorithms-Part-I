package com.company.aniketkr.algorithms1.collections.stack;

import com.company.aniketkr.algorithms1.collections.Collection;

public interface Stack<E> extends Collection<E> {

  void push(E elmt);

  E pop();

  E peek();

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  @Override
  Stack<E> copy();
}
