package me.alen_alex.diamondbank;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPI extends PlaceholderExpansion {

    private final DiamondBank plugin;

    public PlaceholderAPI(DiamondBank plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "dbank";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Alen_Alex";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @NotNull
    public String getName(){
        return "Diamond Bank";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {

        if(params.equals("balance")){
            if(player.isOnline())
                return String.valueOf(plugin.getManager().getPlayerData(player.getPlayer()).getPlayerDiamond());
            else return "0";
        }

        return null;
    }
}
