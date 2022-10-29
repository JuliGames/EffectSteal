package net.juligames.effectsteal.service;

import de.bentzin.tools.DoNotOverride;
import net.juligames.effectsteal.util.EffectMap;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.UUID;

/**
 * @author Ture Bentzin
 * 29.10.2022
 *
 * Main Interface to controll EffectSteal
 */
public interface EffectStealService {

    /**
     * reset current game (removes all effects)
     * @param endTime when the game should be stopped (in unix timemilis)
     */
    default void startNewGame(Long endTime) {
        startNewGame(endTime,() -> {});
    }

    /**
     * reset current game (removes all effects)
     * @implNote same as {@link EffectStealService#startNewGame()} but afterStart will be executed after start procedure
     * but before the game is running
     * @param endTime when the game should be stopped (in unix timemilis)
     */
    void startNewGame(Long endTime, Runnable afterStart);

    /**
     * Reset all effects
     * kick all players for specified reason
     */
    void killGameEarly(Component reason);

    /**
     * Reset all effects
     * kick all players for a default reason
     */
    void killGameEarly();

    /**
     * @return time in unix timemilis when the game is scheduled to end
     */
    @Range(from = 0,to = Long.MAX_VALUE)
    long getEndTime();

    /**
     * @return time the game has started
     * @implNote returns -1 if game was not started yet
     */
    @Range(from = -1,to = Long.MAX_VALUE)
    long getStartTime();

    /**
     * @return true if the game is started already and false if not (also returns true after game is finished)
     * @implNote can be used to control access to the EffectSteal server
     */
    @DoNotOverride
    default boolean isGameStarted() {
        return getStartTime() != -1;
    }

    /**
     *
     * @return true if the game is finished and false if not (also returns false if game was not started yet)
     * @implSpec Finish can be archived by the Timer running out or the game being killed by the specified methods
     */
    boolean isGameFinished();

    /**
     * Send a message to all players on effectSteal
     * @param message message that should be sent to console, log and players
     */
    void broadCast(Component message);

    /**
     * Gets the "Brain" of effectSteal
     * @implNote Attention: This is mutable
     * @return effectMap currently used
     */
    @NotNull
    EffectMap getEffectMap();

    /**
     *
     * @param effectMap map containing the new data!
     * @return the old EffectMap
     * @implNote after this <code>getEffectMap() == effectMap</code> can be false, because getEffectMap()s Object will remain unchanged
     */
    @NotNull
    EffectMap setEffectMap(EffectMap effectMap);

    /**
     * Increase "effect" of player with uuid
     * @param uuid player
     */
    @DoNotOverride
    default void plus(UUID uuid) {
        getEffectMap().plus(uuid);
    }

    /**
     * Decrease "effect" of player with uuid
     * @param uuid player
     */
    @DoNotOverride
    default void minus(UUID uuid) {
        getEffectMap().minus(uuid);
    }


}
