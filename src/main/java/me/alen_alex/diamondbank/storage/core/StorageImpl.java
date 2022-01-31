package me.alen_alex.diamondbank.storage.core;

import me.alen_alex.diamondbank.model.PlayerData;
import me.alen_alex.diamondbank.model.Transaction;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface StorageImpl {
    /**
     * This is used to connect and register into the storage system.
     * NOTE: This must return false if the connection is unsuccessful
     * @return boolean Whether connection was successful or not
     */
    boolean connectToStorageSys();

    /**
     * Get the data of player as a future. If the data is not present, You should register it and return the data
     * see {@link me.alen_alex.diamondbank.storage.SQLite#getOrElseRegister(UUID)} for an example
     * @param playerID UUID of the Player
     * @return A newly or existing {@link PlayerData}
     */
    CompletableFuture<PlayerData> getOrElseRegister(@NotNull UUID playerID);

    /**
     * Just Return it as a string of name
     * @return
     */
    String storageType();

    /**
     * Prepare database tables if needed
     */
    void prepareDatabaseTables();

    /**
     * Closes databases
     */
    void closeDatabase();

    /**
     * Save data as async
     * @param playerData
     */
    void saveAsync(@NotNull PlayerData playerData);
    /**
     * Save data in sync
     * @param playerData
     */
    void saveSync(@NotNull PlayerData playerData);

    /**
     * Add a new transaction log to database
     * @param transaction
     */
    void addAsyncTransactionLog(@NotNull Transaction transaction);

    CompletableFuture<List<Transaction>> getPlayerTransaction(@NotNull UUID player);

}
