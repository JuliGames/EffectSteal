package net.juligames.effectsteal.event;

import net.kyori.adventure.text.Component;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import javax.script.Compilable;

/**
 * @author Ture Bentzin
 * 01.11.2022
 */
public final class GameKilledEvent extends Event {

    private static HandlerList handlerList = new HandlerList();
    private final Component reason;

    public GameKilledEvent(Component reason) {
        this.reason = reason;
    }

    public Component getReason() {
        return reason;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

}
