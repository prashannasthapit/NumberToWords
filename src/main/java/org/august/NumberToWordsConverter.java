package org.august;

public class NumberToWordsConverter {
    // Constants
    private static final String[] NUMBERS_BELOW_20 = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    private static final String[] TENS_WORDS = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
    private static final String[] THOUSANDS_WORDS = {"", "thousand", "million", "billion", "trillion"};
    private static final int THOUSAND = 1000;

    public static String convertNumberToWords(long number) {
        // when number is zero
        if (number == 0) {
            return "zero";
        }
        // when number is negative
        if (number < 0) {
            return "minus " + convertNumberToWords(-number);
        }

        StringBuilder words = new StringBuilder();
        // counter
        int i = 0;


        while (number > 0) {
            // checks if last three digits is not zero
            if (number % THOUSAND != 0) {
                // checks if string builder is empty or not for space placement
                if (!words.isEmpty()) {
                    words.insert(0, " ");
                }
                // Assembly point for 3-digit blocks
                words.insert(0, convertBelowThousand((int) (number % THOUSAND), new StringBuilder()) + THOUSANDS_WORDS[i]);
            }
            // removes last three digits
            number /= THOUSAND;
            i++;
        }

        return words.toString();
    }

    // recursive function for 3-digit block
    public static StringBuilder convertBelowThousand(int number, StringBuilder words) {
        if (number < 0) {
            convertBelowThousand(-number,words);
        } else {
            if (number < 20) {
                // appends equivalent value from array
                words.append(NUMBERS_BELOW_20[number]).append(" ");
            } else if (number < 100) {
                // removes last digit of 2-digit string and appends equivalent value for first digit in tens
                words.append(TENS_WORDS[number / 10]).append(" ");
                // recursive call for last digit equivalent value
                // appends value to same string builder thus change is persisted
                convertBelowThousand(number % 10, words);
            } else {
                // removes last 2 digits from 3-digit string and appends equivalent value in hundreds
                words.append(NUMBERS_BELOW_20[number / 100]).append(" hundred ");
                // recursive call for last 2 digits
                convertBelowThousand(number % 100, words);
            }
        }

        return words;
    }
}
