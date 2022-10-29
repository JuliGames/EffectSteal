package de.bentzin.tools;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Shuffle {

    //credits: PhiLho
    // Implementing Fisher–Yates shuffle
    public static <E> void shuffleArray(E @NotNull [] ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            E a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;

        }
    }
}
