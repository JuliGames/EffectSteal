package net.juligames.effectsteal;

import net.juligames.effectsteal.command.ESCommand;
import net.juligames.effectsteal.service.EffectStealController;
import net.juligames.effectsteal.service.EffectStealService;
import net.juligames.effectsteal.util.EffectMap;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.checkerframework.checker.initialization.qual.UnknownInitialization;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.logging.Level;

public final class EffectSteal extends JavaPlugin {

    @UnknownInitialization
    private static EffectSteal plugin;
    private final EffectMap effectMap = new EffectMap();

    @UnknownInitialization
    public static EffectSteal get() {
        return plugin;
    }

    public static void log(String s) {
        if (get() == null) System.out.println(s);
        else
            get().getLogger().log(Level.INFO, s);
    }

    @Nullable
    public RegisteredServiceProvider<EffectStealService> serviceProvider() {
        return Bukkit.getServicesManager().getRegistration(EffectStealService.class);
    }

    @Nullable
    public EffectStealService service() {
        return Bukkit.getServicesManager().load(EffectStealService.class);
    }
    public static boolean hasPluginOpPermissions(@NotNull CommandSender subject) {
        return subject.hasPermission("effectsteal.operator") || subject.isOp();
    }

    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        log("Hello World!");
        getCommand("es").setExecutor(new ESCommand());
        EffectStealListener effectStealListener = new EffectStealListener(this);

        //service
        EffectStealController controller = new EffectStealController();
        Bukkit.getServer().getServicesManager().register(EffectStealService.class, controller,this, ServicePriority.Normal);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        //failsave for debug

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            for (PotionEffect activePotionEffect : onlinePlayer.getActivePotionEffects()) {
                onlinePlayer.removePotionEffect(activePotionEffect.getType());
            }
        }
    }

    public EffectMap getEffectMap() {
        return effectMap;
    }

    public void reportKill(Player killer, Player victim) {
        getEffectMap().prepare(killer, victim);

        getEffectMap().minus(victim.getUniqueId());
        getEffectMap().plus(killer.getUniqueId());
    }

}
