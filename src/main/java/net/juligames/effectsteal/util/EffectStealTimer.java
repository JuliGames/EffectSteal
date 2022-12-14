package net.juligames.effectsteal.util;

import net.juligames.effectsteal.EffectSteal;
import net.juligames.effectsteal.event.DefinedTimerTickEvent;
import net.juligames.effectsteal.event.TimerTickEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/**
 * @author Ture Bentzin
 * 30.10.2022
 */
@SuppressWarnings("UnusedReturnValue")
public class EffectStealTimer {
    private final Date endDate;
    private final DateFormatter dateFormatter;
    private final Runnable runnable = () -> {
        Instant now = Instant.now();
        Duration between = Duration.between(now, getEndDate().toInstant());
        String apply = getDateFormatter().apply(between);
        String mm = "<blue>Time remaining: <yellow>" + apply;
        TimerTickEvent timerTickEvent = new DefinedTimerTickEvent(now, getEndDate(), mm , this);
        Bukkit.getPluginManager().callEvent(timerTickEvent);

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendActionBar(timerTickEvent.getActionBarAsComponent());
            //System.out.println("timer: " + apply );
        });
    };

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

    public final @NotNull BukkitTask startNew() {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(
                EffectSteal.get(),
                runnable,
                0, 20);
    }

    /**
     * This will chancel <bold>all</bold> pendingTasks form EffectSteal
     */
    public final void chancelAllTasks() {
        for (BukkitTask pendingTask : Bukkit.getScheduler().getPendingTasks()) {
            if (pendingTask.getOwner().equals(EffectSteal.get())) {
                pendingTask.cancel();
            }
        }
    }
}
