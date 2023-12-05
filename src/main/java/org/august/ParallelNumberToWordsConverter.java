package org.august;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelNumberToWordsConverter {
    private static final int availableThreads = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService executor = Executors.newFixedThreadPool(availableThreads);

    public static String convertNumberToWordsParallel(long num) {
//        System.out.println(availableThreads);
        final String[] THOUSANDS_WORDS = {"", "thousand", "million", "billion", "trillion"};
        final int THOUSAND = 1000;

        StringBuilder words = new StringBuilder();
        List<Future<String>> futures = new ArrayList<>();

        int blockIndex = 0;
        while (num != 0) {
            try {
                Future<String> future = executor.submit(new ConverterTask((short) (num % THOUSAND), blockIndex));
                futures.add(future);
            } catch (RejectedExecutionException e) {
                // Handle the exception as needed
                e.printStackTrace();
            }
            num /= THOUSAND;
            blockIndex++;
        }

        try {
            for (Future<String> future : futures) {
                String result = future.get();
                words.insert(0, result + THOUSANDS_WORDS[futures.indexOf(future)] + " ");
            }
        } catch (InterruptedException | ExecutionException e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }

        return words.toString();
    }

    private record ConverterTask(short num, int blockIndex) implements Callable<String> {
        @Override
        public String call() {
            return NumberToWordsConverter.convertBelowThousand(num, new StringBuilder()).toString();
        }
    }
}
