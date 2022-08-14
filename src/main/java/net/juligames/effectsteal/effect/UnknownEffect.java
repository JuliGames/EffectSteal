package net.juligames.effectsteal.effect;

import org.bukkit.Sound;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class UnknownEffect implements MyEffect {
    @Override
    public @NotNull PotionEffectType getType() {
        throw new UnsupportedOperationException("getType() is not supported for " + this.getClass().getSimpleName());
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public @NotNull MyEffect[] getDependencies() {
        return new MyEffect[0];
    }

    @Override
    public MyEffect[] getMyEffects() {
        return new MyEffect[0];
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.UNKNOWN;
    }


    public Sound failSound() {
        return Sound.BLOCK_ANVIL_FALL;
    }

    @Override
    public Sound additionSound() {
        return failSound();
    }

    @Override
    public Sound removalSound() {
        return failSound();
    }
}
