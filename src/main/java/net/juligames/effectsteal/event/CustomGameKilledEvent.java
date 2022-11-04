package net.juligames.effectsteal.event;

import jdk.jfr.Experimental;
import net.kyori.adventure.text.Component;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ture Bentzin
 * 04.11.2022
 *
 * @apiNote currently not in use, maybe put into use later
 */
@Experimental
public final class CustomGameKilledEvent extends GameKilledEvent{

    public static HandlerList handlerList = new HandlerList();

    public Component reason;

    public CustomGameKilledEvent(Component reason) {
        super(reason);
        this.reason = reason;
    }

    @Override
    public Component getReason() {
        return reason;
    }

    public void setReason(Component reason) {
        this.reason = reason;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return super.getHandlers();
    }
}
