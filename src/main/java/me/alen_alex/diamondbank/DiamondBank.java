package me.alen_alex.diamondbank;

import me.alen_alex.diamondbank.config.Configuration;
import me.alen_alex.diamondbank.gui.core.GUIManager;
import me.alen_alex.diamondbank.listener.PlayerConnectionListener;
import me.alen_alex.diamondbank.manager.DataManager;
import me.alen_alex.diamondbank.storage.core.StorageHandler;
import me.alen_alex.diamondbank.test.TestCase;
import me.alen_alex.diamondbank.utils.UtilityManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DiamondBank extends JavaPlugin {

    private UtilityManager pluginUtils;
    private Configuration configuration;
    private StorageHandler storage;
    private DataManager manager;
    private GUIManager guiManager;

    @Override
    public void onEnable() {
        this.pluginUtils = new UtilityManager(this);
        this.configuration = new Configuration(this);
        this.storage = new StorageHandler(this);
        this.manager = new DataManager(this);
        this.guiManager = new GUIManager(this);

        if(!configuration.initConfig()){
            getLogger().severe("Unable to instantiate configuration class, The plugin wil be disabled!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        configuration.loadConfigurationFile();

        if(!storage.initStorageSystem()){
            getLogger().severe("Unable to connect to "+storage.getStorageEngine().storageType()+ "system. The plugin will be disabled!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        storage.getStorageEngine().prepareDatabaseTables();

        guiManager.initAllMenus();

        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(this),this);

        //Remove Later
        getCommand("test").setExecutor(new TestCase(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("Saving current player data's");
        manager.getIteratorForPlayerData().forEachRemaining(playerData -> {
            storage.getStorageEngine().saveSync(playerData);
        });
        getLogger().info("Save complete!");
        getLogger().info("Closing database");
        storage.closeDB();
    }

    public UtilityManager getPluginUtils() {
        return pluginUtils;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public StorageHandler getStorage() {
        return storage;
    }

    public DataManager getManager() {
        return manager;
    }

    public GUIManager getGuiManager() {
        return guiManager;
    }
}
