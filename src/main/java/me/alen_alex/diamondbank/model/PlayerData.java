package me.alen_alex.diamondbank.model;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public final class PlayerData {

    private final UUID playerUUID;
    private int playerDiamond;

    private PlayerData(UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.playerDiamond = 0;
    }

    private PlayerData(UUID playerUUID, int playerDiamond) {
        this.playerUUID = playerUUID;
        this.playerDiamond = playerDiamond;
    }

    public Optional<Player> getPlayer(){
        return Optional.ofNullable(Bukkit.getPlayer(playerUUID));
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public int getPlayerDiamond() {
        return playerDiamond;
    }

    public void setPlayerDiamond(int playerDiamond) {
        this.playerDiamond = playerDiamond;
    }

    public static PlayerData of(UUID uuid,int diamonds){
        return new PlayerData(uuid,diamonds);
    }

    public static PlayerData of(UUID uuid){
        return new PlayerData(uuid);
    }

    public void addPlayerDiamond(int toAdd){
        this.playerDiamond += toAdd;
    }

    public void reducePlayerDiamond(int toReduce){
        this.playerDiamond -= toReduce;
    }
}
