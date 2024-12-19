package org.kafka.practice.kafkademo.domain.utils;

public class StringUnit {

    public static String equalsRepeat() {
        return equalsRepeat(120);
    }

    public static String equalsRepeat(final int count) {
        return repeat("=", count);
    }

    public static String repeat(final String toRepeat, int count) {
        return toRepeat.repeat(count);
    }

    public static boolean isEmpty(final String string) {
        if (string == null) {
            return false;
        }
        return string.trim().isEmpty();
    }

}
