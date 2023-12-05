package org.august;

import java.util.concurrent.*;

public class ParallelNumberToWordsConverter {
    private static final String[] THOUSANDS_WORDS = {"", "thousand", "million", "billion", "trillion"};
    private static final int THOUSAND = 1000;

    public static String convertNumberToWordsParallel(long num) throws InterruptedException {
        StringBuilder words = new StringBuilder();
        int i = 0;

        try (ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())) {
            while (num != 0) {
                short block = (short) (num % THOUSAND);
                num /= THOUSAND;

                ConverterThread converterThread = new ConverterThread(block);
                Future<String> future = executorService.submit(converterThread);

                // Block until the thread completes
                String blockWords = future.get();

                words.insert(0, blockWords + THOUSANDS_WORDS[i] + " ");
                i++;
            }
        } catch (ExecutionException e) {
            e.printStackTrace(); // Handle the exception appropriately later
        }

        return words.toString().trim();
    }

    private record ConverterThread(short num) implements Callable<String> {
        @Override
        public String call() {
            return NumberToWordsConverter.convertBelowThousand(num, new StringBuilder()).toString();
        }
    }
}

