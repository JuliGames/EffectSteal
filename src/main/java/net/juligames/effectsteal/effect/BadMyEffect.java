package net.juligames.effectsteal.effect;

import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public enum BadMyEffect implements MyEffect {
    SLOWNESS(PotionEffectType.SLOW, 0),
    SLOWNESS_2(PotionEffectType.SLOW, 1, SLOWNESS),

    WEAKNESS(PotionEffectType.WEAKNESS,0),
    SLOW_DIGGING(PotionEffectType.SLOW_DIGGING, 0),
    HUNGER(PotionEffectType.HUNGER,0),
    UNLUCKY(PotionEffectType.UNLUCK,0),
    SLOW_FALLING(PotionEffectType.SLOW_FALLING,1, GoodMyEffect.SLOW_FALLING),
    BAD_OMEN(PotionEffectType.BAD_OMEN,0),
    ;


    private final PotionEffectType type;
    private final int level;
    private final MyEffect[] dependencies;

    BadMyEffect(PotionEffectType type, int level, MyEffect... dependencies) {

        this.type = type;
        this.level = level;
        this.dependencies = dependencies;
    }

    BadMyEffect(PotionEffectType type, int level) {

        this.type = type;
        this.level = level;
        this.dependencies = new MyEffect[0];
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
    public @NotNull PotionEffectType getType() {
        return type;
    }

    @Override
    public MyEffect[] getMyEffects() {
        return values();
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.BAD;
    }

    public static @NotNull Collection<BadMyEffect> search(PotionEffect potionEffect) {
        Collection<BadMyEffect> collection = new ArrayList<>();
        for (BadMyEffect value : values()) {
            if(value.getType().equals(potionEffect.getType()) && value.getLevel() == potionEffect.getAmplifier()) {
                collection.add(value);
            }
        }
        return collection;
    }

    @Override
    public Sound additionSound() {
        return Sound.ENTITY_ENDER_DRAGON_GROWL;
    }

    @Override
    public Sound removalSound() {
        return Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
    }
}
