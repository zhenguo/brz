package com.brz.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by macro on 2017/2/7.
 */

public class GenerateRandomNumbers {
    private static int range = 100;
    private static ArrayList<Integer> originalList = new ArrayList<>();
    private static ArrayList<Integer> result = new ArrayList<>();

    static {
        for (int i = 1; i <= range; i++) {
            originalList.add(i);
        }
    }

    public static void main(String[] argv) {
        for (int i = 0; i < range; i++) {
            int j = range - i;
            int r = new Random().nextInt(j);

            result.add(originalList.get(r));

            System.out.print(originalList.get(r) + " ");
            originalList.remove(r);
        }

        System.out.print('\n');

        Collections.sort(result);
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i) + " ");
        }
    }
}
