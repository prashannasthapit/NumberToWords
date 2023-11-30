package org.august;

import java.util.ArrayList;
import java.util.List;

public class ParallelNumberToWordsConverter {
    public static String convertNumberToWordsParallel(long num) throws InterruptedException {
        final String[] THOUSANDS_WORDS = {"", "thousand", "million", "billion", "trillion"};
        final int THOUSAND = 1000;

        StringBuilder words = new StringBuilder();
        List<ConverterThread> threadList = new ArrayList<>();

        while (num != 0) {
            ConverterThread converterThread = new ConverterThread((int) (num % THOUSAND));
            num /= THOUSAND;
            threadList.add(converterThread);
        }

        for (ConverterThread thread : threadList) {
            thread.start();
        }

        for (Thread thread : threadList) {
            thread.join();
        }

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

        public void setWord(String word) {
            this.words.append(word);
        }
    }

}

