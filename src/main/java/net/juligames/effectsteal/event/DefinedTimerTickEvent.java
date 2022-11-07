package net.juligames.effectsteal.event;

import com.google.errorprone.annotations.DoNotCall;
import net.juligames.effectsteal.util.EffectStealTimer;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Date;

/**
 * @author Ture Bentzin
 * 01.11.2022
 * @apiNote If you don't want to make some internal stuff with the timer here then you should use {@link TimerTickEvent}.
 * WARNING: THERE IS NO GARANTIE THAT THE TIMER YOU GET HERE IS INDEED THE TIMER YOU WANT TO RECIEVE!!!
 */
@ApiStatus.Internal
public final class DefinedTimerTickEvent extends TimerTickEvent {


    private static final HandlerList handlerList = new HandlerList();

    private final EffectStealTimer timer;

    public DefinedTimerTickEvent(@NotNull Instant now, @NotNull Date endDate, @NotNull String actionBarMiniMessage, EffectStealTimer timer) {
        super(now, endDate, actionBarMiniMessage);
        this.timer = timer;
    }

    /**
     * WARNING: you should not make changes here (or start a timer) - This can lead to serious issues!
     * @return the EffectStealTimer
     */
    @ApiStatus.Internal
    @DoNotCall
    public EffectStealTimer getTimer() {
        return timer;
    }

    /**
     * @return that this is a {@link DefinedTimerTickEvent}
     */
    @Override
    public boolean isDefined() {
        return true;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
