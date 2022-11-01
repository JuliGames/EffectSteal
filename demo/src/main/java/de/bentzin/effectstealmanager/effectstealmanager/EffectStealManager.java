package de.bentzin.effectstealmanager.effectstealmanager;

import net.juligames.effectsteal.service.EffectStealService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.nio.Buffer;

public final class EffectStealManager extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        EffectStealService service = service();
        if(service == null) try {
            throw new ClassNotFoundException("EffectSteal is not installed!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        service.startNewGame(System.currentTimeMillis() + 1000000);

    }

    @Nullable
    public EffectStealService service(){
        RegisteredServiceProvider<EffectStealService> serviceProvider =
                Bukkit.getServicesManager().getRegistration(EffectStealService.class);
        if(serviceProvider == null) return null;
        EffectStealService provider = serviceProvider.getProvider();
        return provider;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
