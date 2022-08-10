package net.juligames.effectsteal;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class EffectStealListener implements Listener {

    public EffectStealListener(@NotNull EffectSteal plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private Map<UUID, Collection<PotionEffect>> emap = new HashMap<>();


    @EventHandler
    public void onKill(@NotNull PlayerDeathEvent deathEvent) {
        Player player = deathEvent.getPlayer();
        Player killer = player.getKiller();
        if(killer == null){
            emap.put(player.getUniqueId(),player.getActivePotionEffects());
            return;
        }
        EffectSteal.log(player.getName() + " was killed by " + killer);
        EffectSteal.get().reportKill(killer,player);
        emap.put(player.getUniqueId(),player.getActivePotionEffects());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRespawn(@NotNull PlayerPostRespawnEvent respawnEvent) {
        Player player = respawnEvent.getPlayer();
        Collection<PotionEffect> effects = emap.get(player.getUniqueId());
        if(effects != null && effects.size() != 0)
           player.addPotionEffects(effects);


    }


}
