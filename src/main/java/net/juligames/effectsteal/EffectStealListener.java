package net.juligames.effectsteal;

import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public final class EffectStealListener implements Listener {

    public EffectStealListener(@NotNull EffectSteal plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


}
