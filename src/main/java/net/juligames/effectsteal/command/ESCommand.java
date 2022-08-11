package net.juligames.effectsteal.command;

import net.juligames.effectsteal.EffectSteal;
import net.juligames.effectsteal.util.EffectArrayList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.plaf.basic.BasicButtonUI;
import java.util.UUID;

public class ESCommand implements CommandExecutor {

    //Message of the Plugin
    public final String MOTP = "Welcome to " + EffectSteal.get().getDescription().getFullName() +"! Use this command as an ingame player to access some features!" ;


    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command, @NotNull String label,
                             @NotNull String[] args) {
        if (EffectSteal.hasPluginOpPermissions(sender)) {
            if (sender instanceof Player) {
                if (args.length == 0) {
                    sender.sendMessage(MOTP);
                }
                Player player = (Player) sender;
                if (args.length >= 1) {
                    String a = args[0];
                    if (a.equalsIgnoreCase("reset")){
                        EffectSteal.get().getEffectMap().reset();
                        sender.sendMessage("All effects removed!");
                    }
                    else if(a.equalsIgnoreCase("add") || a.equalsIgnoreCase("remove")) {
                        if(args.length == 2) {
                            Player player1 = Bukkit.getPlayer(args[1]);
                              if(player1 != null) {
                                  EffectSteal.get().getEffectMap().prepare(player1);
                                  UUID uniqueId = player1.getUniqueId();
                                  if(a.equalsIgnoreCase("add")) {
                                      EffectSteal.get().getEffectMap().plus(uniqueId);
                                  }else {
                                      EffectSteal.get().getEffectMap().minus(uniqueId);
                                  }

                              }
                        }
                    }
                }
            } else {
                sender.sendMessage(MOTP);
            }
        }else {
            PluginDescriptionFile descriptionFile = EffectSteal.get().getDescription();
            sender.sendMessage(descriptionFile.getFullName() + " by " + descriptionFile.getAuthors().get(0));
            //IMPORTANT: If author is added you need to build a dynamic way to display multiple authors
        }

        return true;
    }
}
