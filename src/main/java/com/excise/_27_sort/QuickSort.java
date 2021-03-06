package com.excise._27_sort;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuickSort {

	// static int[] arr = { 5, 52, 6, 3, 4, 10, 8, 100, 35, 78, 64, 31, 77, 90,
	// 45, 53, 89, 78, 1 };

	static int[] arr = { 5, 52, 6, 3, 4 };

	static ExecutorService pool = Executors.newCachedThreadPool();

	public static class QsortTask implements Runnable {

		int start, end;

		public QsortTask(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public void run() {
			pQsort(arr, start, end);
		}

	}

	/**
	 * 并行递归
	 * 
	 * @param arr
	 * @param low
	 * @param high
	 * @throws InterruptedException
	 */
	private static void pQsort(int[] arr, int low, int high) {
		if (low < high) {
			int pivot = partition(arr, low, high);
			pool.submit(new QsortTask(low, pivot - 1));
			pool.submit(new QsortTask(pivot + 1, high));
		}
	}

	/**
	 * 串行快排
	 * @param arr
	 * @param low
	 * @param high
	 */
	private static void qsort(int[] arr, int low, int high) {
		if (low < high) {
			int pivot = partition(arr, low, high);
			qsort(arr, low, pivot - 1);
			qsort(arr, pivot + 1, high);
		}
	}

	private static int partition(int[] arr, int low, int high) {
		int pivot = arr[low];
		while (low < high) {
			while (low < high && arr[high] >= pivot)
				--high;
			arr[low] = arr[high];
			while (low < high && arr[low] <= pivot)
				++low;
			arr[high] = arr[low];
		}
		arr[low] = pivot;
		return low;
	}

	/**
	 * 串行快排
	 * @param arr
	 */
	public static void quickSort(int[] arr) {
		qsort(arr, 0, arr.length - 1);
	}

	/**
	 * 并行快排
	 * @param arr
	 */
	public static void pQuickSort(int[] arr) {
		pQsort(arr, 0, arr.length - 1);
	}

	public static void main(String[] args) throws InterruptedException {
//		pQuickSort(arr);
		arr = new int[100000];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * 10000);
		}
//		quickSort(arr);
		pQuickSort(arr);
		Thread.sleep(1000);
		pool.shutdown();
		System.out.println(Arrays.toString(arr));
	}

}
