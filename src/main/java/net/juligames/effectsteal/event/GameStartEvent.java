package net.juligames.effectsteal.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ture Bentzin
 * 01.11.2022
 */
public final class GameStartEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final long endTime;

    public GameStartEvent(long endTime) {
        this.endTime = endTime;
    }

    public long getEndTime() {
        return endTime;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

}
