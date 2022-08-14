package net.juligames.effectsteal.effect;

import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public enum GoodMyEffect implements MyEffect {
    SPEED(PotionEffectType.SPEED, 0),
    SPEED_2(PotionEffectType.SPEED, 1, SPEED),

    JUMP_BOOST(PotionEffectType.JUMP, 0),
    JUMP_BOOST_2(PotionEffectType.JUMP, 1, JUMP_BOOST),

    RESISTANCE(PotionEffectType.DAMAGE_RESISTANCE, 0),
    RESISTANCE_2(PotionEffectType.DAMAGE_RESISTANCE, 1, RESISTANCE),

    HASTE(PotionEffectType.FAST_DIGGING, 0),
    HASTE_2(PotionEffectType.FAST_DIGGING, 1, HASTE),

    REGENERATION(PotionEffectType.REGENERATION, 0),
    REGENERATION_2(PotionEffectType.REGENERATION, 1, REGENERATION),

    WATER_BREATHING(PotionEffectType.WATER_BREATHING, 0),
    NIGHT_VISION(PotionEffectType.NIGHT_VISION, 0),
    HEALTH(PotionEffectType.HEALTH_BOOST, 0),
    LUCK(PotionEffectType.LUCK, 0),

    SLOW_FALLING(PotionEffectType.SLOW_FALLING, 0),

    CONDUIT_POWER(PotionEffectType.CONDUIT_POWER, 0),
    DOLPHINS_GRACE(PotionEffectType.DOLPHINS_GRACE, 0),
    HERO(PotionEffectType.HERO_OF_THE_VILLAGE, 0),

    FIRE_RESISTANCE(PotionEffectType.FIRE_RESISTANCE, 0),
    STRENGTH(PotionEffectType.INCREASE_DAMAGE, 0),

    ;


    private final PotionEffectType type;
    private final int level;
    private final MyEffect[] dependencies;

    GoodMyEffect(PotionEffectType type, int level, MyEffect... dependencies) {
        this.type = type;
        this.level = level;
        this.dependencies = dependencies;
    }

    GoodMyEffect(PotionEffectType type, int level) {

        this.type = type;
        this.level = level;
        this.dependencies = new MyEffect[0];
    }

    public static @NotNull Collection<GoodMyEffect> search(PotionEffect potionEffect) {
        Collection<GoodMyEffect> collection = new ArrayList<>();
        for (GoodMyEffect value : values()) {
            if (value.getType().equals(potionEffect.getType()) && value.getLevel() == potionEffect.getAmplifier()) {
                collection.add(value);
            }
        }
        return collection;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @NotNull
    @Override
    public MyEffect[] getDependencies() {
        return dependencies;
    }

    @Override
    public MyEffect[] getMyEffects() {
        return values();
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.GOOD;
    }

    @Override
    public PotionEffectType getType() {
        return type;
    }

    @Override
    public Sound additionSound() {
        return Sound.UI_TOAST_IN;
    }

    @Override
    public Sound removalSound() {
        return Sound.UI_TOAST_OUT;
    }
}
