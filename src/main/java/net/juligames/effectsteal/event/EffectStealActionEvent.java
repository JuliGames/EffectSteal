package net.juligames.effectsteal.event;

import net.juligames.effectsteal.effect.MyEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * @author Ture Bentzin
 * 01.11.2022
 */
public final class EffectStealActionEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final UUID player;
    private final MyEffect effect;
    private final ActionType actionType;

    public EffectStealActionEvent(UUID player, MyEffect effect, ActionType actionType) {
        this.player = player;
        this.effect = effect;
        this.actionType = actionType;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public MyEffect getEffect() {
        return effect;
    }

    public UUID getPlayer() {
        return player;
    }

    public Player getPlayerAsPlayer() {
        return Bukkit.getPlayer(getPlayer());
    }

    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public enum ActionType {
        PLUS,
        MINUS
    }
}
