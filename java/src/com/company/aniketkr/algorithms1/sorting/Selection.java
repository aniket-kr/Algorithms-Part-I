package com.company.aniketkr.algorithms1.sorting;

import java.util.Comparator;

/**
 * Sorts the array using selection sort. Uses time proportional to <code>&theta;(n<sup>2</sup>)
 * </code> and no additional space, where <code>n</code> is the number of elements to sort.
 * The sort is <b>NOT</b> stable.
 *
 * @author Aniket Kumar
 */
public final class Selection {

  /**
   * Sort the array {@code arr} in ascending order.
   *
   * @param arr The array to sort.
   * @param <T> The type of elements of the array.
   * @throws IllegalArgumentException If {@code arr} is {@code null}.
   */
  public static <T extends Comparable<T>> void sort(T[] arr) {
  }

  /**
   * Sort the array {@code arr} in ascending order starting from {@code fromIndex} to
   * {@code toIndex}.
   *
   * @param arr       The array to sort.
   * @param fromIndex The index to start sorting from (inclusive).
   * @param toIndex   The index to sort upto (exclusive).
   * @param <T>       The type of elements of the array.
   * @throws IllegalArgumentException  If {@code arr} is {@code null}.
   * @throws IndexOutOfBoundsException If either {@code fromIndex} or {@code toIndex} is
   *                                   not in the range {@code [0, arr.length]}.
   */
  public static <T extends Comparable<T>> void sort(T[] arr, int fromIndex, int toIndex) {
  }

  /**
   * Sort the array {@code arr} in ascending order according to the comparator.
   *
   * @param arr        The array to sort.
   * @param comparator Comparator object that implements a total order.
   * @param <T>        The type of elements of the array and the type of arguments to
   *                   {@code comparator}.
   * @throws IllegalArgumentException If either {@code arr} or {@code comparator} is
   *                                  {@code null}.
   */
  public static <T> void sort(T[] arr, Comparator<T> comparator) {
  }

  /**
   * Sort the array {@code arr} in ascending order according to the comparator, starting from
   * {@code fromIndex} to {@code toIndex}.
   *
   * @param arr        The array to sort.
   * @param comparator Comparator object that implements a total order.
   * @param fromIndex  The index to start sorting from (inclusive).
   * @param toIndex    The index to sort upto (exclusive).
   * @param <T>        The type of elements of the array and the type of arguments to
   *                   {@code comparator}.
   * @throws IllegalArgumentException  When either {@code arr} or {@code comparator} is
   *                                   {@code null}.
   * @throws IndexOutOfBoundsException If either {@code fromIndex} or {@code toIndex} is
   *                                   not in the range {@code [0, arr.length]}.
   */
  public static <T> void sort(T[] arr, Comparator<T> comparator, int fromIndex, int toIndex) {
  }
}