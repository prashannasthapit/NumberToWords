package org.august;

import java.util.*;

public class ParallelNumberToWordsConverter {
    public static String convertNumberToWordsParallel(long num) throws InterruptedException {
        // constants
        final String[] THOUSANDS_WORDS = {"", "thousand", "million", "billion", "trillion"};
        final int THOUSAND = 1000;

        StringBuilder words = new StringBuilder();
        // to store threads
        Set<ConverterThread> threadSet = new LinkedHashSet<>();

        while (num != 0) {
            // generate threads for every 3-digit block
            ConverterThread converterThread = new ConverterThread((short) (num % THOUSAND));
            num /= THOUSAND;
            // store
            threadSet.add(converterThread);
        }

        // start all threads
        for (ConverterThread thread : threadSet) {
            thread.start();
        }

        // wait for all threads to complete
        for (ConverterThread thread : threadSet) {
            thread.join();
        }

        // assemble all blocks with placeholders
        int i = 0;
        for (ConverterThread thread : threadSet) {
            words.insert(0, thread.getWord() + THOUSANDS_WORDS[i] + " ");
            i++;
        }
        return words.toString();
    }

    private static class ConverterThread extends Thread {
        private final short num;
        private final StringBuilder words = new StringBuilder();

        public ConverterThread(short num) {
            this.num = num;
        }

        @Override
        public void run() {
            setWord(NumberToWordsConverter.convertBelowThousand(num, new StringBuilder()).toString());
        }

        public String getWord() {
            return words.toString();
        }

        public synchronized void setWord(String word) {
            this.words.append(word);
        }
    }
}

