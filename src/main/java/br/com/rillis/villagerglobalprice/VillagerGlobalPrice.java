package br.com.rillis.villagerglobalprice;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class VillagerGlobalPrice extends JavaPlugin {

    public static JavaPlugin plugin;

    @Override
    public void onEnable() {
        System.out.println("[VillagerGlobalPrice] Starting");
        plugin = this;
        registerEvents(new VillagerClickEvent());
    }

    @Override
    public void onDisable() {
        System.out.println("[VillagerGlobalPrice] Shutdown");
    }

    private void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }
}
