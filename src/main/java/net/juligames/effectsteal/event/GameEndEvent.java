package net.juligames.effectsteal.event;

import net.juligames.effectsteal.EffectSteal;
import net.juligames.effectsteal.util.EffectArrayList;
import net.juligames.effectsteal.util.EffectMap;
import net.juligames.effectsteal.util.EffectStealTimer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.data.type.BubbleColumn;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Ture Bentzin
 * 01.11.2022
 */
public sealed class GameEndEvent extends Event permits SingleWinnerGameEndEvent{

    private static final HandlerList handlerList = new HandlerList();
    private final UUID[] winner;

    /**
     *
     * @param winner the uuid of the winner (if one could be selected) - null if not
     */
    public GameEndEvent(UUID[] winner) {
        this.winner = winner;
    }

    public UUID[] getWinner() {
        return winner;
    }


    public @Range(from = 0, to = Integer.MAX_VALUE) int getWinnerCount() {
        return winner.length;
    }

    public boolean isWinnerPresent() {
        if(getWinner() == null) {
            return false;
        }
        if(getWinner().length == 0){
            return false;
        }
        return getWinner()[0] != null;
    }

    public @NotNull OfflinePlayer @Nullable [] getWinnerPlayer() {
        List<OfflinePlayer> offlinePlayers = new ArrayList<>();
        for (UUID uuid : getWinner()) {
            offlinePlayers.add(Bukkit.getPlayer(uuid));
        }
        return offlinePlayers.toArray(new OfflinePlayer[0]);
    }

    @Nullable
    public EffectArrayList @NotNull [] getEffects() {
        List<EffectArrayList> effectArrayLists = new ArrayList<>();
        for (UUID uuid : getWinner()) {
            effectArrayLists.add(EffectSteal.get().getEffectMap().get(uuid));
        }
        return effectArrayLists.toArray(new EffectArrayList[0]);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

}
