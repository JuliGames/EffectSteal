package net.juligames.effectsteal.effect;

import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public enum GoodMyEffect implements MyEffect {
    SPEED(PotionEffectType.SPEED, 0),
    SPEED_2(PotionEffectType.SPEED, 1, SPEED),

    JUMP_BOOST(PotionEffectType.JUMP,0),
    JUMP_BOOST_2(PotionEffectType.JUMP,1,JUMP_BOOST),

    RESISTANCE(PotionEffectType.DAMAGE_RESISTANCE,0),
    FIRE_RESISTANCE(PotionEffectType.FIRE_RESISTANCE,0),
    ABSORPTION(PotionEffectType.ABSORPTION,0),
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
}
