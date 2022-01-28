package me.alen_alex.diamondbank.storage.core;

import me.alen_alex.diamondbank.DiamondBank;
import me.alen_alex.diamondbank.storage.SQLite;

public final class StorageHandler {

    private final DiamondBank plugin;
    private StorageImpl storageEngine;

    public StorageHandler(DiamondBank plugin) {
        this.plugin = plugin;
    }

    public boolean initStorageSystem(){
        storageEngine = new SQLite(this);
        return storageEngine.connectToStorageSys();
    }

    public StorageImpl getStorageEngine() {
        return storageEngine;
    }

    public DiamondBank getPlugin() {
        return plugin;
    }

    public void closeDB(){
        storageEngine.closeDatabase();
    }
}
