package net.juligames.effectsteal;

import net.juligames.effectsteal.util.EffectMap;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.initialization.qual.UnknownInitialization;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public final class EffectSteal extends JavaPlugin {

    @UnknownInitialization
    public static EffectSteal get() { return plugin;}

    public static void log(String s) {
        if(get() == null) System.out.println(s); else
         get().getLogger().log(Level.INFO,s);
    }

    public static boolean hasPluginOpPermissions(@NotNull CommandSender subject) {
        return subject.hasPermission("effectsteal.operator") || subject.isOp();
    }

    private final EffectMap effectMap = new EffectMap();

    @UnknownInitialization
    private static EffectSteal plugin;

    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        log("Hello World!");
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
