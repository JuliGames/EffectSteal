package net.juligames.effectsteal.util;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.Date;

/**
 * @author Ture Bentzin
 * 30.10.2022
 */
public class EffectStealTimer{

    public static final ThreadGroup THREAD_GROUP = new ThreadGroup("EffectSteal-Timers");

    private final Date endDate;
    private final DateFormatter dateFormatter;

    public EffectStealTimer(Date endDate, DateFormatter dateFormatter) {
        this.endDate = endDate;
        this.dateFormatter = dateFormatter;
    }

    public final Date getEndDate() {
        return endDate;
    }

    public DateFormatter getDateFormatter() {
        return dateFormatter;
    }

    public final Thread startNew() {
        Thread thread = new Thread(THREAD_GROUP, runnable, "Timer");
        thread.start();
        return thread;
    }

    public final Collection<Thread> get() {
        return Thread.getAllStackTraces().keySet().stream().filter(thread -> thread.getThreadGroup().equals(THREAD_GROUP)).toList();
    }

    private final Runnable runnable = () -> Bukkit.getOnlinePlayers().forEach(player -> {
        player.sendMessage(MiniMessage.miniMessage().deserialize("<blue>Time remaining: " +
                getDateFormatter().apply(Duration.between(Instant.now(), getEndDate().toInstant()))));
    });
}
