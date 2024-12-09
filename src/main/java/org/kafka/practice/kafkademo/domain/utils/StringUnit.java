package org.kafka.practice.kafkademo.domain.utils;

import lombok.NonNull;

public class StringUnit {

    public static String equalsRepeat() {
        return equalsRepeat(80);
    }

    public static String equalsRepeat(final int count) {
        return repeat("=", count);
    }

    public static String repeat(@NonNull final String toRepeat, int count) {
        return toRepeat.repeat(count);
    }

}
