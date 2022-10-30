package net.juligames.effectsteal.util;

import net.juligames.effectsteal.EffectSteal;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
public class EffectStealTimer{
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

    public final @NotNull BukkitTask startNew() {
        return Bukkit.getScheduler().runTask(EffectSteal.get(),runnable);
    }


    private final Runnable runnable = () -> Bukkit.getOnlinePlayers().forEach(player -> {
        player.sendMessage(MiniMessage.miniMessage().deserialize("<blue>Time remaining: " +
                getDateFormatter().apply(Duration.between(Instant.now(), getEndDate().toInstant()))));
    });
}
