package net.juligames.effectsteal.util;

import java.time.Duration;
import java.util.function.Function;

/**
 * @author Ture Bentzin
 * 30.10.2022
 */
@FunctionalInterface
public interface DateFormatter extends Function<Duration, String> {
}
