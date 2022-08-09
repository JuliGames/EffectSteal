package net.juligames.effectsteal;

import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.units.qual.Speed;
import org.jetbrains.annotations.NotNull;

public interface Effects {

    @NotNull PotionEffectType getType();
    int getLevel();
    @NotNull Effects[] getDependencies();

    default boolean hasDependencies() {
        return getDependencies().length != 0;
    }
}

enum GoodEffects implements Effects{
    SPEED(PotionEffectType.SPEED,1),
    SPEED_2(PotionEffectType.SPEED,2,new Effects[]{SPEED})
    ;

    private final PotionEffectType type;
    private final int level;
    private final Effects[] dependencies;

    GoodEffects(PotionEffectType type, int level, Effects[] dependencies) {

        this.type = type;
        this.level = level;
        this.dependencies = dependencies;
    }

    GoodEffects(PotionEffectType type, int level) {

        this.type = type;
        this.level = level;
        this.dependencies = new Effects[0];
    }

    @Override
    public int getLevel() {
        return level;
    }

    @NotNull
    @Override
    public Effects[] getDependencies() {
        return dependencies;
    }

    @Override
    public PotionEffectType getType() {
        return type;
    }
}

enum BadEffects implements Effects{
    SLOWNESS(PotionEffectType.SLOW,1),
    SLOWNESS_2(PotionEffectType.SLOW,2,new Effects[]{SLOWNESS})
    ;
    private final PotionEffectType type;
    private final int level;
    private final Effects[] dependencies;

    BadEffects(PotionEffectType type, int level, Effects[] dependencies) {

        this.type = type;
        this.level = level;
        this.dependencies = dependencies;
    }

    BadEffects(PotionEffectType type, int level) {

        this.type = type;
        this.level = level;
        this.dependencies = new Effects[0];
    }

    @Override
    public int getLevel() {
        return level;
    }

    @NotNull
    @Override
    public Effects[] getDependencies() {
        return dependencies;
    }

    @Override
    public PotionEffectType getType() {
        return type;
    }
}
