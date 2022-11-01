package net.juligames.effectsteal.event;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/**
 * @author Ture Bentzin
 * 01.11.2022
 */
public final class TimerTickEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    @NotNull
    private final Instant now;
    @NotNull
    private final Date endDate;

    @NotNull
    private  String actionBarMiniMessage;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public TimerTickEvent(@NotNull Instant now, @NotNull Date endDate, @NotNull String actionBarMiniMessage){
        this.now = now;
        this.endDate = endDate;
        this.actionBarMiniMessage = actionBarMiniMessage;
    }

    /**
     *
     * @return duration between now and the end of the timer
     */
    public Duration between() {
        return Duration.between(Instant.now(), getEndInstant());
    }

    /**
     *
     * @return the date the timer estimates to run out
     */
    public @NotNull Date getEndDate() {
        return endDate;
    }

    /**
     *
     * @return the point in time the timer estimates to run out
     */
    public Instant getEndInstant() {
        return endDate.toInstant();
    }

    /**
     *
     * @return the point in time the event is assigned to
     */
    public @NotNull Instant getNow() {
        return now;
    }


    /**
     *
     * @return  new message that will be displayed in every players hotbar as minimessage
     * @see MiniMessage
     */
    public @NotNull String getActionBarMiniMessage() {
        return actionBarMiniMessage;
    }

    /**
     *
     * @param actionBarMiniMessage new message that will be displayed in every players hotbar as minimessage
     * @see MiniMessage
     */
    public void setActionBarMiniMessage(@NotNull String actionBarMiniMessage) {
        this.actionBarMiniMessage = actionBarMiniMessage;
    }

    /**
     *
     * @return the message that will be displayed in every players hotbar
     */
    public @NotNull Component getActionBarAsComponent() {
        return MiniMessage.miniMessage().deserialize(getActionBarMiniMessage());
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public @NotNull String toString() {
        final StringBuffer sb = new StringBuffer("TimerTickEvent{");
        sb.append("now=").append(now);
        sb.append(", endDate=").append(endDate.toInstant());
        sb.append(", actionBarMiniMessage='").append(actionBarMiniMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
