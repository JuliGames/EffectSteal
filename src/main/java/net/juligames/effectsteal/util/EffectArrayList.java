package net.juligames.effectsteal.util;

import de.bentzin.tools.SubscribableList;
import net.juligames.effectsteal.Calculable;
import net.juligames.effectsteal.EffectSteal;
import net.juligames.effectsteal.effect.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.InvalidParameterException;
import java.util.*;


public class EffectArrayList extends SubscribableList<MyEffect> {

    private final Player player;

    public EffectArrayList(Player player) {
        this.player = player;
        if (player == null) {
            EffectSteal.get().getLogger().warning("Somebody tried to create an EffectArrayList for a player that is currently offline!");
            throw new InvalidParameterException("the player should not be null!");
        }

        subscribe((effect, subscriptionType) -> {
            if (effect != null)
                grantEffect(effect);
            else
                grantEffect(new UnknownEffect());
        }, SubscriptionType.ADD);

        subscribe((effect, subscriptionType) -> {
            if (effect != null)
                revokeEffect(effect);
            else
                grantEffect(new UnknownEffect());
        }, SubscriptionType.REMOVE);

    }

    /**
     * brings all players "value" to 0
     */
    public void reset() {
        clear();
    }


    private void grantEffect(@NotNull MyEffect effect) {
        if (player.isOnline()) {
            effect.additionSound();
            if (effect.getEffectType().equals(EffectType.UNKNOWN)) {

                TextComponent component = Component.text("There was no effect found, that could be applied to you!")
                        .color(NamedTextColor.RED);
                player.sendMessage(component);
                return;
            }
            effect.grant(player);
            Color color = effect.getType().getColor();
            TextComponent component = Component.text(effect.getType().getName() + ", " + (effect.getLevel() + 1) + " was added!")
                    .color(TextColor.color(color.getRed(), color.getGreen(), color.getBlue()));
            player.sendMessage(component);
            player.sendActionBar(component);
        } else reset();
    }

    private void revokeEffect(MyEffect effect) {
        if (player.isOnline()) {

            effect.removalSound();
            if (effect.getEffectType().equals(EffectType.UNKNOWN)) {
                TextComponent component = Component.text("There was no effect found, that could be applied to you!")
                        .color(NamedTextColor.RED);
                player.sendMessage(component);
                return;
            }
            effect.revoke(player);
            getSimilar(effect).forEach(myEffect -> myEffect.grant(player,true));
            Color color = effect.getType().getColor();
            TextComponent component = Component.text(effect.getType().getName() + ", " + (effect.getLevel() + 1) + " was removed!")
                    .color(TextColor.color(color.getRed(), color.getGreen(), color.getBlue()));
            player.sendMessage(component);
            player.sendActionBar(component);
        } else reset();
    }

    public boolean hasEffect(PotionEffect effect) {
        Collection<MyEffect> myEffects = new ArrayDeque<>();
        myEffects.addAll(BadMyEffect.search(effect));
        myEffects.addAll(GoodMyEffect.search(effect));
        return myEffects.size() > 0;
    }

    public boolean containsEffect(PotionEffect effect) {
        Collection<MyEffect> myEffects = new ArrayDeque<>();
        myEffects.addAll(BadMyEffect.search(effect));
        myEffects.addAll(GoodMyEffect.search(effect));

        return this.containsAll(myEffects);
    }

    public int calculateValue() {
        int i = 0;
        for (MyEffect myEffect : this) {
            if (myEffect != null) {
                Calculable effectType = myEffect.getEffectType();
                i = effectType.calc(i);
            }
        }
        //player.sendMessage("your current value: " + i);
        return i;
    }


    public MyEffect getOneRandom() {
        int rnd = new Random().nextInt(size());
        return get(rnd);
    }


    /**
     *
     * @return all MyEffects in this List that share the PotionEffectType with the given effect!
     */
    public Collection<MyEffect> getSimilar(MyEffect effect) {
        Collection<MyEffect> collection = new ArrayList<>();
        for (MyEffect myEffect : this) {
            if(myEffect.isSimilar(effect)) {
                collection.add(myEffect);
            }
        }
        return collection;
    }


    /**
     * @param myEffect the effect
     * @param save if set to true the myEffect needs to be part of this list!
     * @return all MyEffects in this List that depend on a given MyEffect
     */
    public Collection<MyEffect> getDependent(MyEffect myEffect, boolean save) {
        if(save) {
            if(!contains(myEffect)) throw new InvalidParameterException("myEffect is not contained in this list! Set save = false to run anyway!");
        }
        Collection<MyEffect> collection = new ArrayList<>();
        for (MyEffect effect : this) {
            if(effect.hasDependencies()) {
                for (MyEffect dependency : effect.getDependencies()) {
                    if(dependency.equals(myEffect)) {
                        //dependent for myEffect found!!!
                        collection.add(effect);
                    }
                }
            }
        }
        return collection;
    }



    //Standby
    @Nullable
    public MyEffect getOneRandom(EffectType effectType, boolean forRemoval) {
        return getRandomFromList(effectType,this,true);
    }

    protected MyEffect getRandomFromList(EffectType effectType, ArrayList<MyEffect> myEffects, boolean forRemoval) {
        int rnd = 0;
        for (int i = 0; i < this.size(); i++) {
            rnd = new Random().nextInt(size());
            MyEffect myEffect = myEffects.get(rnd);

            if (myEffect.getEffectType().equals(effectType)) {
                if(!forRemoval) {
                    return myEffect;
                }else {
                    Collection<MyEffect> dependent = getDependent(myEffect, true);
                    if(dependent.size() == 0) {
                        return myEffect;
                    }else {
                        return getRandomFromList(effectType,new ArrayList<>(dependent), forRemoval);
                    }
                }
            }
        }
        return null;
    }

  @Nullable
    public MyEffect getOneRandom(EffectType effectType) {
        return getOneRandom(effectType,false);
    }


}

