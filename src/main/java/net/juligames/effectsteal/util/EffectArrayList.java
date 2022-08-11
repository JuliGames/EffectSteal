package net.juligames.effectsteal.util;

import de.bentzin.tools.SubscribableList;
import net.juligames.effectsteal.Calcable;
import net.juligames.effectsteal.EffectSteal;
import net.juligames.effectsteal.effect.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.InvalidParameterException;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Random;


public class EffectArrayList extends SubscribableList<MyEffect> {

    private final Player player;

    public EffectArrayList(Player player) {
        this.player = player;
        if (player == null) {
            EffectSteal.get().getLogger().warning("Somebody tried to create an EffectArrayList for a player that is currently offline!");
            throw new InvalidParameterException("the player should not be null!");
        }

        subscribe((effect, subscriptionType) -> {
            if(effect != null)
                grantEffect(effect);
            else
                grantEffect(new UnknownEffect());
        }, SubscriptionType.ADD);

        subscribe((effect, subscriptionType) -> {
            if(effect != null)
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
            if(effect.getEffectType().equals(EffectType.UNKNOWN)) {

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
        }
        else reset();
    }

    private void revokeEffect(MyEffect effect) {
        if (player.isOnline()) {
            effect.removalSound();
            if(effect.getEffectType().equals(EffectType.UNKNOWN)) {
                TextComponent component = Component.text("There was no effect found, that could be applied to you!")
                        .color(NamedTextColor.RED);
                player.sendMessage(component);
                return;
            }
            effect.revoke(player);
            Color color = effect.getType().getColor();
            TextComponent component = Component.text(effect.getType().getName() + ", " + (effect.getLevel() + 1) + " was removed!")
                    .color(TextColor.color(color.getRed(), color.getGreen(), color.getBlue()));
            player.sendMessage(component);
            player.sendActionBar(component);
        }
        else reset();
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
            if(myEffect != null) {
                Calcable effectType = myEffect.getEffectType();
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

    @Nullable
    public MyEffect getOneRandom(EffectType effectType) {
        int rnd = 0;
        for (int i = 0; i < this.size(); i++) {
            rnd = new Random().nextInt(size());
            MyEffect myEffect = get(rnd);
            if(myEffect.getEffectType().equals(effectType)){
                return myEffect;
            }


        }
        return null;
    }
}

