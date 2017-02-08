package com.brz.service;

/**
 * Created by macro on 2017/2/7.
 */

public class SequenceOfFor {
    public static boolean foo(char c) {
        System.out.print(c);
        return true;
    }

    public static void main(String[] argv) {
        int i = 0;

        for (foo('A'); foo('B') && i < 2; foo('C')) {
            i++;
            foo('D');
        }

        /**
         * 输出 ABDCBDCB
         */
    }
}
