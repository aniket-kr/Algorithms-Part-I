package com.company.aniketkr.algorithms1.sorting;

/**
 * Utility class for all publicly accessible classes in
 * {@link com.company.aniketkr.algorithms1.sorting sorting} package.
 *
 * @author Aniket Kumar
 */
class Sorter {

  /**
   * Swap the elements of the array {@code arr} at indices {@code i} and {@code j} with
   * each other.
   *
   * @param arr The array to swap elements in.
   * @param i   Index to swap with element at {@code arr[j]}.
   * @param j   Index to swap with element at {@code arr[i]}.
   * @throws NullPointerException           If {@code arr} is {@code null}.
   * @throws ArrayIndexOutOfBoundsException If {@code i} and {@code j} are out of range.
   */
  static void swap(Object[] arr, int i, int j) {
    Object tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }

  /**
   * Validate if the index {code i} is a valid index w.r.t. the array {@code arr}. An index is
   * "valid" if it is in the range {@code [0, arr.length]} (both endpoints included). Simply
   * returns out of the method if index is "valid".
   *
   * @param arr The array to check {@code i} against.
   * @param i   The index.
   * @throws ArrayIndexOutOfBoundsException If {@code i} is not "valid".
   */
  static void checkIndex(Object[] arr, int i) {
    if (i < 0 || i > arr.length) {
      throw new ArrayIndexOutOfBoundsException("index out of range: " + i);
    }
  }

  /**
   * Shuffle the array {@code arr} using <em>Knuth's shuffling</em>. It takes time proportional
   * to <code>n</code> and constant extra space to uniformly shuffle the array.
   *
   * @param arr The array.
   */
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

  /**
   * Uses {@link Math#random()} to randomly generate integer numbers between {@code low}
   * and {@code high}.
   *
   * @param low  The lower limit (inclusive).
   * @param high The upper limit (exclusive).
   * @return A random integer between given limits.
   * @implNote Note that the method does not validate if {@code low} is actually lower than
   *     {@code high}. In the event that this assumption fails, <b>NO EXCEPTION</b>
   *     is thrown and the method behaves unexpectedly.
   */
  private static int random(int low, int high) {
    return low + (int) (Math.random() * (high - low));
  }
}
