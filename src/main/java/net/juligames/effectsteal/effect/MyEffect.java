package net.juligames.effectsteal.effect;

import de.bentzin.tools.misc.Shuffle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MyEffect {

    @NotNull PotionEffectType getType();

    int getLevel();

    @NotNull MyEffect[] getDependencies();

    MyEffect[] getMyEffects();

    EffectType getEffectType();

    default boolean hasDependencies() {
        return getDependencies().length != 0;
    }

    default void grant(@NotNull Player player, boolean silent) {
        player.addPotionEffect(new PotionEffect(getType(), Integer.MAX_VALUE, getLevel()));
        player.playSound(player.getLocation(), additionSound(), 100, 0);
    }

    default void revoke(@NotNull Player player, boolean silent) {
        player.removePotionEffect(getType());//TODO removes every effect

        player.playSound(player.getLocation(), removalSound(), 100, 0);
    }

    default void revoke(@NotNull Player player) {
        revoke(player, false);
    }

    default void grant(@NotNull Player player) {
        grant(player, false);
    }


    default Sound additionSound() {
        return Sound.UI_BUTTON_CLICK;
    }

    default Sound removalSound() {
        return Sound.UI_BUTTON_CLICK;
    }

    /**
     * @param current
     * @return null - if no new effect can be found...
     */
    @Nullable
    default MyEffect getOneNewRandom(@NotNull MyEffect[] current) {
        MyEffect[] myEffects = getMyEffects();
        if (myEffects.length == 0) return null;
        Shuffle.shuffleArray(myEffects);

        for (MyEffect myEffect : myEffects) {
            boolean contains = false;
            for (MyEffect effect : current) {
                if (effect == null) {
                    return null;
                }
                if (effect.equals(myEffect)) {
                    contains = true;
                    break;
                }
            }
            if (contains)
                continue;
            //myEffect is not in current -> check for depends
            if (!myEffect.hasDependencies()) return myEffect;
            //okey there are depends


            @NotNull MyEffect[] dependencies = myEffect.getDependencies();
            boolean next = false;

            for (MyEffect dependency : dependencies) {
                boolean b = false;
                for (MyEffect effect : current) {
                    if (effect.equals(dependency)) {
                        b = true;
                        break;
                    }
                }
                if (!b) {
                    next = true;
                    break; //exit for() and search a new one
                }
            }
            if (next) continue;
            //All depends are there!! wowo
            return myEffect;
        }
        //No result!
        return null;
    }

    /**
     * @param myEffect The effect to check similarity on
     * @return if this and myEffect share the same PotionEffectType
     */
    default <E extends MyEffect> boolean isSimilar(@NotNull E myEffect) {
        return myEffect.getType().equals(getType());
    }

}

