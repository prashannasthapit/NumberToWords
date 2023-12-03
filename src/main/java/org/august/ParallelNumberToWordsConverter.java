package org.august;

import java.util.ArrayList;
import java.util.List;

public class ParallelNumberToWordsConverter {
    public static String convertNumberToWordsParallel(long num) throws InterruptedException {
        // constants
        final String[] THOUSANDS_WORDS = {"", "thousand", "million", "billion", "trillion"};
        final int THOUSAND = 1000;

        StringBuilder words = new StringBuilder();
        // to store threads
        List<ConverterThread> threadList = new ArrayList<>();

        while (num != 0) {
            // generate threads for every 3-digit block
            ConverterThread converterThread = new ConverterThread((int) (num % THOUSAND));
            num /= THOUSAND;
            // store
            threadList.add(converterThread);
        }

        // start all threads
        for (ConverterThread thread : threadList) {
            thread.start();
        }

        // wait for all threads to complete
        for (Thread thread : threadList) {
            thread.join();
        }

        // assemble all blocks with placeholders
        for (byte i = 0; i < (byte) (threadList.size()); i++) {
            words.insert(0, threadList.get(i).getWord() + THOUSANDS_WORDS[i] + " ");
        }
        return words.toString();
    }

    private static class ConverterThread extends Thread {
        private final int num;
        private final StringBuilder words = new StringBuilder();

        public ConverterThread(int num) {
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

