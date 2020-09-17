package com.company.aniketkr.algorithms1.collections.list;

import com.company.aniketkr.algorithms1.collections.Collection;

public interface List<E> extends Collection<E> {

  void add(E elmt);

  void add(E elmt, int atIndex);

  void addAll(E[] elmts);

  void addAll(E[] elmts, int fromIndex);

  void addAll(Iterable<? extends E> elmts);

  void addAll(Iterable<? extends E> elmts, int fromIndex);

  E get(int index);

  int indexOf(E elmt);

  int indexOf(E elmt, int fromIndex, int toIndex);

  E delete(int index);

  int remove(E elmt);

  /* **************************************************************************
   * Section: Collection Methods
   ************************************************************************** */

  @Override
  List<E> copy();
}
