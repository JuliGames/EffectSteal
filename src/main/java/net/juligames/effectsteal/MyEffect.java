package net.juligames.effectsteal;

import de.bentzin.tools.Shuffle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public interface MyEffect {

    @NotNull PotionEffectType getType();
    int getLevel();
    @NotNull MyEffect[] getDependencies();
    MyEffect[] getMyEffects();

    default boolean hasDependencies() {
        return getDependencies().length != 0;
    }

    default void grant(@NotNull Player player) {
        player.addPotionEffect(new PotionEffect(getType(),Integer.MAX_VALUE,getLevel()));
    }
    
    default void revoke(@NotNull Player player) {
        player.removePotionEffect(getType());
    }

    /**
     *
     * @param current
     * @return null - if no new effect can be found...
     */
   @Nullable
   default MyEffect getOneNewRandom(@NotNull MyEffect[] current) {
        MyEffect[] myEffects = getMyEffects();
        Shuffle.shuffleArray(myEffects);
        
        for (MyEffect myEffect : myEffects) {
            boolean contains = false;
            for (MyEffect effect : current) {
                if(effect.equals(myEffect)) contains = true;
            }
            if(contains)
                continue;
            //myEffect is not in current -> check for depends
            if(!myEffect.hasDependencies()) return myEffect;
            //okey there are depends


            @NotNull MyEffect[] dependencies = myEffect.getDependencies();
            boolean next = false;

            for (MyEffect dependency : dependencies) {
                boolean b = false;
                for (MyEffect effect : current) {
                    if(effect.equals(dependency)) b = true;
                }
                if(!b) {
                    next = true; break; //exit for() and search a new one
                }
            }
            if(next) continue;
            //All depends are there!! wowo
            return myEffect;
        }
        //No result!
        return null;
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
    public MyEffect[] getMyEffects() {
        return values();
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

    @Override
    public MyEffect[] getMyEffects() {
        return values();
    }
}
