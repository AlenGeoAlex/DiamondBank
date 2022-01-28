package me.alen_alex.diamondbank.listener;

import me.alen_alex.diamondbank.DiamondBank;
import me.alen_alex.diamondbank.model.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerConnectionListener implements Listener {

    private final DiamondBank plugin;

    public PlayerConnectionListener(DiamondBank plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        final Player player = event.getPlayer();
        plugin.getStorage().getStorageEngine().getOrElseRegister(player.getUniqueId()).thenAccept((playerData) -> {
           plugin.getManager().insertPlayerData(playerData);
        });
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event){
        final Player player = event.getPlayer();
        plugin.getManager().unloadFromCache(player);
    }
}
