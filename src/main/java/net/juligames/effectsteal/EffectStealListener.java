package net.juligames.effectsteal;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import net.juligames.effectsteal.event.TimerTickEvent;
import net.juligames.effectsteal.util.EffectArrayList;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class EffectStealListener implements Listener {

    private final Map<UUID, Collection<PotionEffect>> emap = new HashMap<>();

    public EffectStealListener(@NotNull EffectSteal plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onKill(@NotNull PlayerDeathEvent deathEvent) {
        if (EffectSteal.get().isRunning()) {
            Player player = deathEvent.getPlayer();
            Player killer = player.getKiller();
            if (killer == null) {
                emap.put(player.getUniqueId(), player.getActivePotionEffects());
                return;
            }
            EffectSteal.log(player.getName() + " was killed by " + killer);
            EffectSteal.get().reportKill(killer, player);
            emap.put(player.getUniqueId(), player.getActivePotionEffects());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRespawn(@NotNull PlayerPostRespawnEvent respawnEvent) {
        if (EffectSteal.get().isRunning()) {
            Player player = respawnEvent.getPlayer();
            Collection<PotionEffect> effects = emap.get(player.getUniqueId());
            if (effects != null && effects.size() != 0)
                player.addPotionEffects(effects);
        }
    }

    @EventHandler
    public void onEffect(@NotNull EntityPotionEffectEvent event) {
        if (EffectSteal.get().isRunning()) {
            Entity entity = event.getEntity();
            if (entity instanceof Player player) {

                EffectArrayList myEffects = EffectSteal.get().getEffectMap().get(player.getUniqueId());
                if (myEffects == null) {
                    event.setCancelled(true);
                    return;
                }

                if (event.getAction().equals(EntityPotionEffectEvent.Action.ADDED)) {
                    event.setCancelled(!myEffects.containsEffect(event.getNewEffect()));
                } else if (event.getAction().equals(EntityPotionEffectEvent.Action.REMOVED)
                        || event.getAction().equals(EntityPotionEffectEvent.Action.CLEARED)) {

                    event.setCancelled(myEffects.containsEffect(event.getOldEffect()));
                } else if (event.getAction().equals(EntityPotionEffectEvent.Action.CHANGED)) {
                    event.setCancelled(!myEffects.containsEffect(event.getNewEffect()));
                }
         /*   if(event.isCancelled()) {
                if(event.getNewEffect() != null)
                    EffectSteal.log("Blocked effect: " + event.getNewEffect().getType().getName() + " on " + event.getEntity().getName());
                else
                    EffectSteal.log("Blocked removal of effect: " + event.getOldEffect().getType().getName() + " on " + event.getEntity().getName());

            }

          */
            } else {
            }
        }
    }


    @Contract(pure = true)
    @EventHandler
    public void onMilk(@NotNull PlayerInteractEvent event) {
        if (EffectSteal.get().isRunning()) {
            if (event.getMaterial().equals(Material.MILK_BUCKET)) {
                if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                        || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                    event.setCancelled(true);
                }
            }

        }
    }


    //internal

    @EventHandler
    public void onTimerTick(@NotNull TimerTickEvent timerTickEvent) {
        if (timerTickEvent.between().isZero() || timerTickEvent.between().isNegative()) {
            //notify gameEnd
            EffectSteal.get().notifyGameEnd();
        }
    }

}
