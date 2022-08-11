package net.juligames.effectsteal.util;

import net.juligames.effectsteal.effect.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public final class EffectMap extends HashMap<UUID,EffectArrayList> {


    public void plus(UUID uuid) {
        int calculateValue = calculateValue(uuid);
        EffectArrayList effectArrayList = get(uuid);

        if(calculateValue <= -1) {
            MyEffect oneRandom = effectArrayList.getOneRandom(EffectType.BAD);
            effectArrayList.remove(oneRandom != null? oneRandom : new UnknownEffect());
        } else if( calculateValue >= 0) {
            effectArrayList.add(GoodMyEffect.values()[0].getOneNewRandom(effectArrayList.toArray(new MyEffect[0])));
        }
    }

    public void minus(UUID uuid) {
        int calculateValue = calculateValue(uuid);
        EffectArrayList effectArrayList = get(uuid);

        if(calculateValue >= 1) {
            MyEffect oneRandom = effectArrayList.getOneRandom(EffectType.GOOD);
            effectArrayList.remove( oneRandom != null? oneRandom : new UnknownEffect());
        } else if( calculateValue <= 0) {
            effectArrayList.add(BadMyEffect.values()[0].getOneNewRandom(effectArrayList.toArray(new MyEffect[0])));
        }
    }


    protected int calculateValue(UUID uuid) {
        return get(uuid).calculateValue();
    }

    /**
     *
     * @param uuid
     * @return the resulting EffectArrayList from the Map
     */
    public EffectArrayList create(UUID uuid) {
        return put(uuid, new EffectArrayList(Bukkit.getPlayer(uuid)));
    }

    public boolean prepare(@NotNull Player player) {
        UUID uniqueId = player.getUniqueId();
        if(containsKey(uniqueId))
            return true;
        create(uniqueId);
        return false;
    }

    public void prepare(@NotNull Player @NotNull ... players){
        for (Player player : players) {
            prepare(player);
        }
    }

    /**
     * ! This does not clear the Map
     * @see EffectMap#clear()
     */
    public void reset() {
     this.forEach((uuid, potionEffects) -> potionEffects.reset());
    }

    @Override
    public void clear() {
        reset();
        super.clear();
    }
}


