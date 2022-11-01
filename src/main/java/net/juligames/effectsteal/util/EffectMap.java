package net.juligames.effectsteal.util;

import net.juligames.effectsteal.effect.*;
import net.juligames.effectsteal.event.EffectStealActionEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public final class EffectMap extends HashMap<UUID, EffectArrayList> {

    /**
     * @param effectMap effectMap
     * @return a new effect map containing the content of effectMap
     */
    public static @NotNull EffectMap copyFrom(EffectMap effectMap) {
        EffectMap effectMap1 = new EffectMap();
        effectMap1.putAll(effectMap);
        return effectMap1;
    }

    public void plus(UUID uuid) {
        int calculateValue = calculateValue(uuid);
        EffectArrayList effectArrayList = get(uuid);

        if (calculateValue <= -1) {
            MyEffect oneRandom = effectArrayList.getOneRandom(EffectType.BAD,true);
            MyEffect myEffect = (oneRandom != null ? oneRandom : new UnknownEffect());
            //fire event
            Bukkit.getPluginManager().callEvent(new EffectStealActionEvent(uuid,myEffect, EffectStealActionEvent.ActionType.PLUS));
            effectArrayList.remove(myEffect);
        } else if (calculateValue >= 0) {
            effectArrayList.add(GoodMyEffect.values()[0].getOneNewRandom(effectArrayList.toArray(new MyEffect[0])));
        }
    }

    public void minus(UUID uuid) {
        int calculateValue = calculateValue(uuid);
        EffectArrayList effectArrayList = get(uuid);

        if (calculateValue >= 1) {
            MyEffect oneRandom = effectArrayList.getOneRandom(EffectType.GOOD,true);
            MyEffect myEffect = (oneRandom != null ? oneRandom : new UnknownEffect());
            //fire Event
            Bukkit.getPluginManager().callEvent(new EffectStealActionEvent(uuid,myEffect, EffectStealActionEvent.ActionType.MINUS));
            effectArrayList.remove(myEffect);
        } else if (calculateValue <= 0) {
            effectArrayList.add(BadMyEffect.values()[0].getOneNewRandom(effectArrayList.toArray(new MyEffect[0])));
        }
    }


    protected int calculateValue(UUID uuid) {
        return get(uuid).calculateValue();
    }

    /**
     * @param uuid
     * @return the resulting EffectArrayList from the Map
     */
    public EffectArrayList create(UUID uuid) {
        return put(uuid, new EffectArrayList(Bukkit.getPlayer(uuid)));
    }

    public boolean prepare(@NotNull Player player) {
        UUID uniqueId = player.getUniqueId();
        if (containsKey(uniqueId))
            return true;
        create(uniqueId);
        return false;
    }

    public void prepare(@NotNull Player @NotNull ... players) {
        for (Player player : players) {
            prepare(player);
        }
    }

    /**
     * ! This does not clear the Map
     *
     * @see EffectMap#clear()
     */
    public void reset() {
        this.forEach((uuid, potionEffects) -> potionEffects.reset());
    }

    /**
     * This clears the data (map) and inputs the data of effectMap into this
     *
     * @see EffectMap#clear()
     * @see EffectMap#reset()
     * @see EffectMap#copyFrom(EffectMap)
     */
    @ApiStatus.Internal
    public void clear(EffectMap effectMap) {
        clear();
        putAll(effectMap);
    }

    @Override
    public void clear() {
        reset();
        super.clear();
    }
}


