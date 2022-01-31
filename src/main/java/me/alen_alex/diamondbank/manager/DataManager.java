package me.alen_alex.diamondbank.manager;

import me.alen_alex.diamondbank.DiamondBank;
import me.alen_alex.diamondbank.enums.TransactionWay;
import me.alen_alex.diamondbank.model.PlayerData;
import me.alen_alex.diamondbank.model.Transaction;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class DataManager {

    private final DiamondBank plugin;
    private final HashMap<UUID, PlayerData> playerDataHashMap;

    public DataManager(DiamondBank plugin) {
        this.plugin = plugin;
        this.playerDataHashMap = new HashMap<UUID,PlayerData>();
    }

    public boolean doContainsFor(@NotNull UUID uuid){
        return playerDataHashMap.containsKey(uuid);
    }

    public boolean doContainsFor(@NotNull Player player){
        return this.doContainsFor(player.getUniqueId());
    }

    public void insertPlayerData(@NotNull PlayerData data){
        playerDataHashMap.put(data.getPlayerUUID(),data);
    }

    public PlayerData getPlayerData(@NotNull UUID uuid){
        return playerDataHashMap.get(uuid);
    }

    public PlayerData getPlayerData(@NotNull Player player){
        return this.getPlayerData(player.getUniqueId());
    }

    public void unloadFromCache(@NotNull Player player){
        playerDataHashMap.remove(player.getUniqueId());
    }

    public Iterator<PlayerData> getIteratorForPlayerData(){
        return playerDataHashMap.values().iterator();
    }

    public void processTransaction(@NotNull Transaction transaction){
        if(!playerDataHashMap.containsKey(transaction.getUuid()))
            return;

        final PlayerData data = getPlayerData(transaction.getUuid());
        if(transaction.getWay() == TransactionWay.DEPOSIT) {
            data.addPlayerDiamond(transaction.getAmount());
        }else {
            data.reducePlayerDiamond(transaction.getAmount());


        }

        plugin.getStorage().getStorageEngine().saveAsync(data);
        plugin.getStorage().getStorageEngine().addAsyncTransactionLog(transaction);
    }


}
