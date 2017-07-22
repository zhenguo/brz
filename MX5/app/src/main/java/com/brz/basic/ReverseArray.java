package com.brz.basic;

/**
 * Created by macro on 2017/1/16.
 */

public class ReverseArray {

    private void reverse(int[] array) {
        int start = 0;
        while (start < array.length / 2) {
            int tmp = array[start];
            array[start] = array[array.length - 1 - start];
            array[array.length - 1 - start] = tmp;

            start++;
        }

        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }

    public static void main(String[] args) {
        int[] array = {6, 8, 2, 3, 1, 4};

        ReverseArray reverseArray = new ReverseArray();
        reverseArray.reverse(array);
    }
}
