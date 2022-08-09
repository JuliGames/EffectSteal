package net.juligames.effectsteal;

import net.juligames.effectsteal.util.EffectMap;
import org.bukkit.plugin.java.JavaPlugin;

public final class EffectSteal extends JavaPlugin {

    private final EffectMap effectMap = new EffectMap();

    @Override
    public void onEnable() {
        // Plugin startup logic
        EffectStealListener effectStealListener = new EffectStealListener(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public EffectMap getEffectMap() {
        return effectMap;
    }


}
