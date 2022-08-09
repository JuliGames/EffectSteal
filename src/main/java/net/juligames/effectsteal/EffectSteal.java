package net.juligames.effectsteal;

import net.juligames.effectsteal.util.EffectMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.initialization.qual.UnknownInitialization;
import org.checkerframework.common.initializedfields.qual.EnsuresInitializedFields;

import java.util.logging.Level;

public final class EffectSteal extends JavaPlugin {

    @UnknownInitialization
    public static EffectSteal get() { return plugin;}

    public static void log(String s) {
        if(get() == null) System.out.println(s); else
        get().getLogger().log(Level.INFO,s);
    }


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
