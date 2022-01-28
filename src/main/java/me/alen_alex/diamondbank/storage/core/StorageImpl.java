package me.alen_alex.diamondbank.storage.core;

import me.alen_alex.diamondbank.model.PlayerData;
import org.jetbrains.annotations.NotNull;

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

    CompletableFuture<PlayerData> getOrElseRegister(@NotNull UUID playerID);

    String storageType();

    void prepareDatabaseTables();

    void closeDatabase();

    void saveAsync(@NotNull PlayerData playerData);

    void saveSync(@NotNull PlayerData playerData);

}
