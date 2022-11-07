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
 * 07.11.2022
 */
public sealed class TimerTickEvent extends Event permits DefinedTimerTickEvent {
    private static final HandlerList handlerList = new HandlerList();

    @NotNull
    protected final Instant now;
    @NotNull
    protected final Date endDate;
    @NotNull
    protected String actionBarMiniMessage;

    public TimerTickEvent(@NotNull Instant now, @NotNull Date endDate, @NotNull String actionBarMiniMessage) {
        super(true);
        this.now = now;
        this.endDate = endDate;
        this.actionBarMiniMessage = actionBarMiniMessage;
    }

    public static HandlerList getHandlerList() {
        return  handlerList;
    }

    /**
     * @return duration between now and the end of the timer
     */
    public Duration between() {
        return Duration.between(Instant.now(), getEndInstant());
    }

    /**
     * @return the date the timer estimates to run out
     */
    public @NotNull Date getEndDate() {
        return endDate;
    }

    /**
     * @return the point in time the timer estimates to run out
     */
    public Instant getEndInstant() {
        return endDate.toInstant();
    }

    /**
     * @return the point in time the event is assigned to
     */
    public @NotNull Instant getNow() {
        return now;
    }

    /**
     * @return new message that will be displayed in every players hotbar as minimessage
     * @see MiniMessage
     */
    public @NotNull String getActionBarMiniMessage() {
        return actionBarMiniMessage;
    }

    /**
     * @param actionBarMiniMessage new message that will be displayed in every players hotbar as minimessage
     * @see MiniMessage
     */
    public void setActionBarMiniMessage(@NotNull String actionBarMiniMessage) {
        this.actionBarMiniMessage = actionBarMiniMessage;
    }

    /**
     * @return the message that will be displayed in every players hotbar
     */
    public @NotNull Component getActionBarAsComponent() {
        return MiniMessage.miniMessage().deserialize(getActionBarMiniMessage());
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    /**
     * @return if this is a {@link DefinedTimerTickEvent} or not
     */
    public boolean isDefined() {
        return false;
    }

    @Override
    public @NotNull String toString() {
        return "TimerTickEvent{" + "now=" + now +
                ", endDate=" + endDate.toInstant() +
                ", actionBarMiniMessage='" + actionBarMiniMessage + '\'' +
                '}';
    }
}
