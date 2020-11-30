package com.excise._15_fork;

public class CountTaskOneThread {

    public static void main(String[] args) {
        long sum = 0;
        for (long i = 1; i <= 200000L; i++) {
            sum += i;
        }
        System.out.println(sum);
    }

}
