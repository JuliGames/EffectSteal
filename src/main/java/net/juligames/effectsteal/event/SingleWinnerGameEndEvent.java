package net.juligames.effectsteal.event;

import net.juligames.effectsteal.util.EffectArrayList;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Ture Bentzin
 * 04.11.2022
 */
public final class SingleWinnerGameEndEvent extends GameEndEvent {

    private static final HandlerList handlerList = new HandlerList();


    public SingleWinnerGameEndEvent(UUID winner) {
        super(new UUID[]{winner});
    }

    public EffectArrayList getSingleEffects() {
        return getEffects()[0];
    }

    public OfflinePlayer getSingleWinnerPlayer() {
        return Objects.requireNonNull(getWinnerPlayer())[0];
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
