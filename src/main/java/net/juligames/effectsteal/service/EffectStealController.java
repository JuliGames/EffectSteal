package net.juligames.effectsteal.service;

import de.bentzin.tools.register.Registerator;
import net.juligames.effectsteal.EffectSteal;
import net.juligames.effectsteal.event.GameStartEvent;
import net.juligames.effectsteal.util.DateFormatter;
import net.juligames.effectsteal.util.EffectMap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.UUID;
import java.util.function.Function;

/**
 * @author Ture Bentzin
 * 29.10.2022
 */
public class EffectStealController implements EffectStealService {

    private DateFormatter dateFormatter = duration ->
            DurationFormatUtils.formatDurationWords(duration.toMillis(),
                    true, true);


    /**
     * reset current game (removes all effects)
     *
     * @param endTime    when the game should be stopped (in unix timemilis)
     * @param afterStart
     * @implNote same as {@link EffectStealService#startNewGame(long)} but afterStart will be executed after start procedure
     * but before the game is running
     * @deprecated afterStart is no longer necessary {@link net.juligames.effectsteal.event.GameStartEvent}
     */
    @Override
    @Deprecated
    public void startNewGame(long endTime, @NotNull Runnable afterStart) {
        EffectSteal effectSteal = EffectSteal.get();
        effectSteal.setStartTime(System.currentTimeMillis());
        effectSteal.setEndTime(endTime);
        Bukkit.getPluginManager().callEvent(new GameStartEvent(endTime));
        afterStart.run();
        effectSteal.getRunning().set(true);

    }

    /**
     * Reset all effects
     * kick all players for specified reason
     *
     * @param reason
     */
    @Override
    public void killGameEarly(Component reason) {
        EffectSteal.get().killGame(reason);
    }

    /**
     * Reset all effects
     * kick all players for a default reason
     */
    @Override
    public void killGameEarly() {
        killGameEarly(MiniMessage.miniMessage().deserialize("<yellow>Your EffectSteal game was killed!"));
    }

    @Override
    public void addGameEndHandler(Runnable handler) {
        try {
            EffectSteal.get().getGameEndHandlers().register(handler);
        } catch (Registerator.DuplicateEntryException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return time in unix timemilis when the game is scheduled to end
     */
    @Override
    public @Range(from = 0, to = Long.MAX_VALUE) long getEndTime() {
        return EffectSteal.get().getEndTime();
    }

    /**
     * @return time the game has started
     * @implNote returns -1 if game was not started yet
     */
    @Override
    public @Range(from = -1, to = Long.MAX_VALUE) long getStartTime() {
        return EffectSteal.get().getStartTime();
    }

    /**
     * @return true if the game is finished and false if not (also returns false if game was not started yet)
     * @implSpec Finish can be archived by the Timer running out or the game being killed by the specified methods
     */
    @Override
    public boolean isGameFinished() {
        return !EffectSteal.get().isRunning();
    }


    /**
     * Send a message to all players on effectSteal
     *
     * @param message message that should be sent to console, log and players
     */
    @Override
    public void broadCast(Component message) {
        EffectSteal.get().broadCast(message);
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

    /**
     * The Function to identify who has won a game
     */
    public Function<EffectMap, UUID[]> getWinnerGenerator() {
        return EffectSteal.get().getWinnerGenerator();
    }

    /**
     * The Function to identify who has won a game
     */
    public void setWinnerGenerator(Function<EffectMap, UUID[]> winnerGenerator) {
        EffectSteal.get().setWinnerGenerator(winnerGenerator);
    }

    @Override
    @ApiStatus.Internal
    public String toString() {
        return this.getClass().getName();
    }
}
