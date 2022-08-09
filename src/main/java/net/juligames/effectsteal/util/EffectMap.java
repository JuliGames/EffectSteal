package net.juligames.effectsteal.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public final class EffectMap extends HashMap<UUID,EffectArrayList> {


    public void plus(UUID uuid) {

    }

    public void minus(UUID uuid) {

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


