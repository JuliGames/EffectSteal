package net.juligames.effectsteal.util;

import de.bentzin.tools.SubscribableList;
import net.juligames.effectsteal.Calcable;
import net.juligames.effectsteal.EffectSteal;
import net.juligames.effectsteal.effect.MyEffect;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;


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
        if (player.isOnline())
            effect.grant(player);
        else reset();
    }

    private void revokeEffect(MyEffect effect) {
        if (player.isOnline())
            effect.revoke(player);
        else reset();
    }

    public int calculateValue() {
        int i = 0;
        for (MyEffect myEffect : this) {
            Calcable effectType = myEffect.getEffectType();
            effectType.calc(i);
        }
        return i;
    }


    public MyEffect getOneRandom() {
        return null;
    }

    //TODO: Pick one random
}

