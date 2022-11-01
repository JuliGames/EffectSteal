package net.juligames.effectsteal.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ture Bentzin
 * 01.11.2022
 */
public final class GameEndEvent extends Event {

    private static HandlerList handlerList = new HandlerList();
    public GameEndEvent() {
    }


    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

}
