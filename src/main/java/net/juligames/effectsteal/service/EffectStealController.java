package net.juligames.effectsteal.service;

import net.juligames.effectsteal.EffectSteal;
import net.juligames.effectsteal.util.DateFormatter;
import net.juligames.effectsteal.util.EffectMap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

/**
 * @author Ture Bentzin
 * 29.10.2022
 */
public class EffectStealController implements EffectStealService{

    private DateFormatter dateFormatter = duration ->
        DurationFormatUtils.formatDurationWords(duration.toMillis(),
                true,true);
    ;


    /**
     * reset current game (removes all effects)
     *
     * @param endTime    when the game should be stopped (in unix timemilis)
     * @param afterStart
     * @implNote same as {@link EffectStealService#startNewGame(Long)} but afterStart will be executed after start procedure
     * but before the game is running
     */
    @Override
    public void startNewGame(Long endTime, Runnable afterStart) {

    }

    /**
     * Reset all effects
     * kick all players for specified reason
     *
     * @param reason
     */
    @Override
    public void killGameEarly(Component reason) {

    }

    /**
     * Reset all effects
     * kick all players for a default reason
     */
    @Override
    public void killGameEarly() {

    }

    /**
     * @return time in unix timemilis when the game is scheduled to end
     */
    @Override
    public @Range(from = 0, to = Long.MAX_VALUE) long getEndTime() {
        return 0;
    }

    /**
     * @return time the game has started
     * @implNote returns -1 if game was not started yet
     */
    @Override
    public @Range(from = -1, to = Long.MAX_VALUE) long getStartTime() {
        return 0;
    }

    /**
     * @return true if the game is finished and false if not (also returns false if game was not started yet)
     * @implSpec Finish can be archived by the Timer running out or the game being killed by the specified methods
     */
    @Override
    public boolean isGameFinished() {
        return false;
    }

    public final Component broadCastPrefix =
            MiniMessage.miniMessage().deserialize("<gray>[<red>Effect<yellow>Steal</yellow></red>]</gray><color:#3b83ff> ");

    /**
     * Send a message to all players on effectSteal
     *
     * @param message message that should be sent to console, log and players
     */
    @Override
    public void broadCast(Component message) {
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(broadCastPrefix.append(message)));
    }

    /**
     * Gets the "Brain" of effectSteal
     *
     * @return effectMap currently used
     * @implNote Attention: This is mutable
     */
    @Override
    public @NotNull EffectMap getEffectMap() {
        return EffectSteal.get().getEffectMap();
    }

    /**
     * @param effectMap map containing the new data!
     * @return the old EffectMap (only the old data <code>returnValue != getEffectMap()</code>)
     * @implNote after this <code>getEffectMap() == effectMap</code> can be false, because getEffectMap()s Object will remain unchanged
     */
    @Override
    public @NotNull EffectMap setEffectMap(EffectMap effectMap) {
        EffectMap oldMap = EffectMap.copyFrom(effectMap);
        EffectSteal.get().getEffectMap().clear(effectMap);
        return oldMap;
    }

    @Override
    public DateFormatter getDateFormatter() {
        return dateFormatter;
    }

    @Override
    public void setDateFormatter(DateFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    @Override
    @ApiStatus.Internal
    public String toString() {
        return this.getClass().getName();
    }
}
