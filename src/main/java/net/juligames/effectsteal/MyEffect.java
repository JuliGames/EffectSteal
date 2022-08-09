package net.juligames.effectsteal;

import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public interface MyEffect {

    @NotNull PotionEffectType getType();
    int getLevel();
    @NotNull MyEffect[] getDependencies();

    default boolean hasDependencies() {
        return getDependencies().length != 0;
    }

    default MyEffect getOneRandom(MyEffect[] current) {

    }
}

enum GoodMyEffect implements MyEffect {
    SPEED(PotionEffectType.SPEED,1),
    SPEED_2(PotionEffectType.SPEED,2,new MyEffect[]{SPEED}),
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
    public PotionEffectType getType() {
        return type;
    }
}

enum BadMyEffect implements MyEffect {
    SLOWNESS(PotionEffectType.SLOW,1),
    SLOWNESS_2(PotionEffectType.SLOW,2,new MyEffect[]{SLOWNESS})
    ;


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
}
