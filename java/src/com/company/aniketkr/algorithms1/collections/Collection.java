package com.company.aniketkr.algorithms1.collections;

import java.util.Iterator;

public interface Collection<E> extends Iterable<E> {

  int size();

  boolean isEmpty();

  void clear();

  Collection<E> copy();

  Iterator<E> iterator();

  /* **************************************************************************
   * Section: Object Methods
   ************************************************************************** */

  boolean equals(Object obj);

  String toString();
}
