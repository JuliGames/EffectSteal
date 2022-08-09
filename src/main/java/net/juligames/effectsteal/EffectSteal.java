package net.juligames.effectsteal;

import net.juligames.effectsteal.util.EffectMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.initialization.qual.UnknownInitialization;

public final class EffectSteal extends JavaPlugin {

    @UnknownInitialization
    public static EffectSteal get() { return plugin;}


    private final EffectMap effectMap = new EffectMap();

    @UnknownInitialization
    private static EffectSteal plugin;

    @Override
    public void onEnable() {
        plugin = this;
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
