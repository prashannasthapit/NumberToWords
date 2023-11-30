package org.august;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            numbers.add(randInt(0, 999999));
        }

        int total=0;
        int totalPar=0;

        for (int number : numbers) {
            long startTime = System.nanoTime();
            NumberToWordsConverter.convertNumberToWords(number);
            long endTime = System.nanoTime();
            total += (int) (endTime - startTime);


            long startTimePar = System.nanoTime();
            ParallelNumberToWordsConverter.convertNumberToWordsParallel(number);
            long endTimePar = System.nanoTime();
            totalPar += (int) (endTimePar - startTimePar);
        }
        System.out.println("Average time for basic: "+total/1000);
        System.out.println("Average time for parallel: "+totalPar/1000);

    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
