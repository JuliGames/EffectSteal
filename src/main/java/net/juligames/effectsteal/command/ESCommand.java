package net.juligames.effectsteal.command;

import de.bentzin.tools.Hardcode;
import net.juligames.effectsteal.EffectSteal;
import net.juligames.effectsteal.event.*;
import net.juligames.effectsteal.service.EffectStealService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.velocity.tools.generic.LoopTool;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredListener;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;

public class ESCommand implements CommandExecutor {

    //Message of the Plugin
    @SuppressWarnings("SpellCheckingInspection")
    public final String MOTP = "Welcome to " + EffectSteal.get().getDescription().getFullName() + "! Use this command as an ingame player to access some features!";


    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command, @NotNull String label,
                             @NotNull String[] args) {
        if (EffectSteal.hasPluginOpPermissions(sender)) {
            if (sender instanceof Player player) {
                if (args.length == 0) {
                    sender.sendMessage(MOTP);
                }
                if (args.length >= 1) {
                    String a = args[0];
                    if (a.equalsIgnoreCase("reset")) {
                        EffectSteal.get().getEffectMap().reset();
                        sender.sendMessage("All effects removed!");
                    } else if (a.equalsIgnoreCase("add") || a.equalsIgnoreCase("remove")) {
                        if (args.length == 2) {
                            Player player1 = Bukkit.getPlayer(args[1]);
                            if (player1 != null) {
                                EffectSteal.get().getEffectMap().prepare(player1);
                                UUID uniqueId = player1.getUniqueId();
                                if (a.equalsIgnoreCase("add")) {
                                    sender.sendMessage("[DEBUG]: " + ChatColor.GREEN + "+ 1 for " + player1.getName());
                                    EffectSteal.get().getEffectMap().plus(uniqueId);
                                } else {
                                    sender.sendMessage("[DEBUG]: " + ChatColor.DARK_RED + "- 1 for " + player1.getName());
                                    EffectSteal.get().getEffectMap().minus(uniqueId);
                                }

                            }
                        }
                    }else if(a.equalsIgnoreCase("api")) {
                        //API
                        Component apiComp = miniMessage().deserialize("<aqua>[<yellow>API</yellow>]:<gray> ");
                        EffectStealService effectStealService = Bukkit.getServicesManager().load(EffectStealService.class);
                        assert effectStealService != null;
                        sender.sendMessage(apiComp.append(miniMessage().deserialize("current service: <dark_green>" + effectStealService.getClass().getSimpleName())));

                        //get users
                        mapHandlers().forEach((eventClass, handlerList) ->{
                            StringJoiner handlers = new StringJoiner(", ");

                            for (RegisteredListener registeredListener : handlerList.getRegisteredListeners()) {
                                RegisteredListener listener = registeredListener;
                                Plugin plugin = listener.getPlugin();
                               //handlers.add(plugin.getName() + " v."  + plugin.getDescription().getVersion()
                               // +  "-> " + listener.getListener().getClass().getSimpleName());
                                List<String> authors = plugin.getDescription().getAuthors();
                                String mm = "<hover:show_text:'<gold>" + listener.getListener().getClass().getSimpleName() + "<gray>@" + plugin.getDescription().getFullName() +  (authors.size() != 0? " by " + (authors.size() == 1? authors.get(0) : authors): "")  +"'>" + plugin.getName() + "</hover>";
                                handlers.add(mm);
                            }

                            sender.sendMessage(apiComp.append(miniMessage().deserialize(eventClass.getSimpleName() + " ::: <dark_green>" + handlers)));
                        } );


                    }
                }
            } else {
                sender.sendMessage(MOTP);
            }
        } else {
            PluginDescriptionFile descriptionFile = EffectSteal.get().getDescription();
            sender.sendMessage(descriptionFile.getFullName() + " by " + descriptionFile.getAuthors().get(0));
            //IMPORTANT: If author is added you need to build a dynamic way to display multiple authors
        }

        return true;
    }

    @ApiStatus.Experimental
    private @NotNull Map<Class<? extends Event>,HandlerList> mapHandlers(){
        final Map<Class<? extends Event>, HandlerList> map = new HashMap<>();
        List<Class<? extends Event>> classes = getEvents();

        for (Class<? extends Event> eventClass : classes) {
            try {
                Field handlerField = eventClass.getDeclaredField("handlerList");
                handlerField.setAccessible(true);
                HandlerList handlerList = (HandlerList) handlerField.get(null);
                map.put(eventClass,handlerList);
            } catch (Exception e) {
                EffectSteal.get().getLogger().warning("mapping of the HandlerLists (" + eventClass.getSimpleName() +  ") failed: \"" + e.getMessage() + "\" @" + e.getClass().getSimpleName());
            }
        }
        return map;
    }

    @Hardcode
    private @NotNull List<Class<? extends Event>> getEvents() {
        ArrayList<Class<? extends Event>> arrayList = new ArrayList<>();

        arrayList.add(CustomGameKilledEvent.class);
        arrayList.add(DefinedTimerTickEvent.class);
        arrayList.add(EffectStealActionEvent.class);
        arrayList.add(GameEndEvent.class);
        arrayList.add(GameKilledEvent.class);
        arrayList.add(GameStartEvent.class);
        arrayList.add(SingleWinnerGameEndEvent.class);
        arrayList.add(TimerTickEvent.class);

        return arrayList;
    }
}
