package net.juligames.effectsteal;

import de.bentzin.tools.misc.SubscribableType;
import de.bentzin.tools.register.Registerator;
import net.juligames.effectsteal.command.ESCommand;
import net.juligames.effectsteal.service.EffectStealController;
import net.juligames.effectsteal.service.EffectStealService;
import net.juligames.effectsteal.util.EffectMap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
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

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;

public final class EffectSteal extends JavaPlugin {

    @UnknownInitialization
    private static EffectSteal plugin;
    private final EffectMap effectMap = new EffectMap();
    private final SubscribableType<Boolean> running = new SubscribableType<>(false);
    private long startTime = -1L;
    private long endTime = -1L;

    private final Registerator<Runnable> gameEndHandlers = new Registerator<>();

    @UnknownInitialization
    public static EffectSteal get() {
        return plugin;
    }

    public static void log(String s) {
        if (get() == null) System.out.println(s);
        else
            get().getLogger().log(Level.INFO, s);
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Registerator<Runnable> getGameEndHandlers() {
        return gameEndHandlers;
    }

    @Nullable
    public RegisteredServiceProvider<EffectStealService> serviceProvider() {
        return Bukkit.getServicesManager().getRegistration(EffectStealService.class);
    }

    public boolean isRunning() {
        return running.getOrCatch(false);
    }

    public SubscribableType<Boolean> getRunning() {
        return running;
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
        Objects.requireNonNull(getCommand("es")).setExecutor(new ESCommand());
        EffectStealListener effectStealListener = new EffectStealListener(this);

        //service
        EffectStealController controller = new EffectStealController();
        Bukkit.getServer().getServicesManager().register(EffectStealService.class, controller,this, ServicePriority.Normal);

        subscribe();
    }

    private void subscribe() {
        running.subscribe((SubscribableType.QuietSubscription<Boolean>) (subscriptionType, newElement, oldElement) -> {
            if(newElement) {
                broadCast("<lime>Game is now running!");
            }else {
                broadCast("<red>Game is now stopped!");

                //reset
                getEffectMap().reset();
            }
        }, SubscribableType.SubscriptionType.CHANGE, SubscribableType.SubscriptionType.INITIALIZE);
    }

    public final Component broadCastPrefix =
            MiniMessage.miniMessage().deserialize("<gray>[<red>Effect<yellow>Steal</yellow></red>]</gray><color:#3b83ff> ");

    /**
     * Send a message to all players on effectSteal
     *
     * @param message message that should be sent to console, log and players
     */
    public void broadCast(Component message) {
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(broadCastPrefix.append(message)));
    }


    /**
     * Send a message to all players on effectSteal
     *
     * @param miniMessage message that should be sent to console, log and players
     */
    public void broadCast(String miniMessage) {
        Bukkit.getOnlinePlayers().forEach(player ->
                player.sendMessage(broadCastPrefix.append(MiniMessage.miniMessage().deserialize(miniMessage))));
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

    public void gameEnd() {
        gameEndHandlers.forEach(Runnable::run);
    }

    public void killGame(Component reason) {
        //1. set running false
        running.set(false);

        //gameEnd routine
        gameEnd();

        //2. kick
        Bukkit.getOnlinePlayers().forEach(player -> player.kick(reason));
    }

}
