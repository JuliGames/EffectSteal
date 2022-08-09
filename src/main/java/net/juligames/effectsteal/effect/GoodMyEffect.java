package net.juligames.effectsteal.effect;

import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public enum GoodMyEffect implements MyEffect {
    SPEED(PotionEffectType.SPEED, 0),
    SPEED_2(PotionEffectType.SPEED, 1, new MyEffect[]{SPEED}),
    ;


    private final PotionEffectType type;
    private final int level;
    private final MyEffect[] dependencies;

    GoodMyEffect(PotionEffectType type, int level, MyEffect[] dependencies) {

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
