package com.company.aniketkr.algorithms1.sorting;

class Sorter {

  static void swap(Object[] arr, int i, int j) {
    Object tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }

  static void checkIndex(Object[] arr, int i) {
    if (i < 0 || i > arr.length) {
      throw new IndexOutOfBoundsException("index out of range: " + i);
    }
  }

  static void shuffle(Object[] arr) {
    int randIndex;
    for (int i = arr.length - 1; i > 0; i--) {
      randIndex = random(0, i + 1);
      swap(arr, i, randIndex);
    }
  }

  /* **************************************************************************
   * Section: Utility Methods
   ************************************************************************** */

  private static int random(int low, int high) {
    if (low >= high) {
      throw new IllegalArgumentException("`low` can't be equal to or higher that `high`");
    }

    return low + (int) (Math.random() * (high - low));
  }
}
