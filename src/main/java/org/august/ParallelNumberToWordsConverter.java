package org.august;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


public class ParallelNumberToWordsConverter {
    // configure logger
//    private static final Logger logger = LoggerFactory.getLogger(ParallelNumberToWordsConverter.class);
    // get number of available threads
    private static final int availableThreads = Runtime.getRuntime().availableProcessors();
    // setup executor service
    public static final ExecutorService executor = Executors.newFixedThreadPool(availableThreads);

    public static String convertNumberToWordsParallel(long num) {
//        System.out.println(availableThreads);
        // constants
        final String[] THOUSANDS_WORDS = {"", "thousand", "million", "billion", "trillion"};
        final int THOUSAND = 1000;

        // initialise string builder and arraylist for threads
        StringBuilder words = new StringBuilder();
        List<Future<String>> futures = new ArrayList<>();

        // flag if number is negative
        boolean minus = false;
        if (num < 0) {
            // raise flag
            minus = true;
            // turn positive to prevent negative input into threads
            num = -num;
        }

        // loop for every 3-digit block
        while (num != 0) {
            try {
                // create future
                Future<String> future = executor.submit(new ConverterTask((short) (num % THOUSAND)));
                // add thread to list
                futures.add(future);
            } catch (RejectedExecutionException e) {
                // use logging
//                logger.error("An error occurred when submitting task", e);
            }
            // remove last 3-digit block
            num /= THOUSAND;
        }
        try {
            // loop for every future
            for (Future<String> future : futures) {
                // waits if future not complete and stores in variable
                String result = future.get();
                // inserts result of future to front of string builder
                // as futures are set from last 3-digit block
                words.insert(0, result + THOUSANDS_WORDS[futures.indexOf(future)] + " ");
            }
        } catch (InterruptedException | ExecutionException e) {
            // use logging
//            logger.error("An error occurred when assembling words", e);
        }

        // for minus
        if (minus) {
            words.insert(0, "minus ");
        }
        return words.toString().trim();
    }

    // runnable but better
    private record ConverterTask(short num) implements Callable<String> {
        @Override
        public String call() {
            // calls appropriate method for operation on 3-digit block
            return NumberToWordsConverter.convertBelowThousand(num, new StringBuilder()).toString();
        }
    }
}
