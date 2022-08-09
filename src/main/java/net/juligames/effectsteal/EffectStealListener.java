package net.juligames.effectsteal;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

public final class EffectStealListener implements Listener {

    public EffectStealListener(@NotNull EffectSteal plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onKill(@NotNull PlayerDeathEvent deathEvent) {
        Player player = deathEvent.getPlayer();
        Player killer = player.getKiller();
        if(killer == null) return;
        EffectSteal.log(player.getName() + " was killed by " + killer);

    }


}
