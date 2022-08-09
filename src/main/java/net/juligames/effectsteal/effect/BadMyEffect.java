package net.juligames.effectsteal.effect;

import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public enum BadMyEffect implements MyEffect {
    SLOWNESS(PotionEffectType.SLOW, 1),
    SLOWNESS_2(PotionEffectType.SLOW, 2, new MyEffect[]{SLOWNESS});


    private final PotionEffectType type;
    private final int level;
    private final MyEffect[] dependencies;

    BadMyEffect(PotionEffectType type, int level, MyEffect[] dependencies) {

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
    public PotionEffectType getType() {
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

    public void test() {
        return;
    }
}
