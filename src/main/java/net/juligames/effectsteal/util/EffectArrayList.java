package net.juligames.effectsteal.util;

import de.bentzin.tools.SubscribableList;
import net.juligames.effectsteal.Calcable;
import net.juligames.effectsteal.EffectSteal;
import net.juligames.effectsteal.effect.EffectType;
import net.juligames.effectsteal.effect.MyEffect;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.InvalidParameterException;
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
            grantEffect(effect);
        }, SubscriptionType.ADD);

        subscribe((effect, subscriptionType) -> {
            revokeEffect(effect);
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
            effect.grant(player);
            Color color = effect.getType().getColor();
            TextComponent component = Component.text(effect.getType().getName() + ", " + effect.getLevel() + " was added!")
                    .color(TextColor.color(color.getRed(), color.getGreen(), color.getBlue()));
            player.sendMessage(component);
            player.sendActionBar(component);
        }
        else reset();
    }

    private void revokeEffect(MyEffect effect) {
        if (player.isOnline()) {
            effect.revoke(player);
            Color color = effect.getType().getColor();
            TextComponent component = Component.text(effect.getType().getName() + ", " + effect.getLevel() + " was removed!")
                    .color(TextColor.color(color.getRed(), color.getGreen(), color.getBlue()));
            player.sendMessage(component);
            player.sendActionBar(component);
        }
        else reset();
    }

    public int calculateValue() {
        int i = 0;
        for (MyEffect myEffect : this) {
            Calcable effectType = myEffect.getEffectType();
            i = effectType.calc(i);
        }
        player.sendMessage("your current value: " + i);
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

