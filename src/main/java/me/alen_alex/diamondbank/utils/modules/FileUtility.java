package me.alen_alex.diamondbank.utils.modules;

import de.leonhard.storage.Config;
import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.internal.settings.ConfigSettings;
import de.leonhard.storage.internal.settings.DataType;
import de.leonhard.storage.internal.settings.ReloadSettings;
import me.alen_alex.diamondbank.utils.UtilityManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;

public class FileUtility {

    private UtilityManager manager;

    public FileUtility(UtilityManager manager) {
        this.manager = manager;
    }

    public InputStream getStreamFromResource(@NotNull String resName){
        return manager.getPlugin().getResource(resName);
    }

    public Config createConfiguration(){
        return LightningBuilder
                .fromFile(new File(manager.getPlugin().getDataFolder(),"config.yml"))
                .addInputStream(getStreamFromResource("config.yml"))
                .setConfigSettings(ConfigSettings.PRESERVE_COMMENTS)
                .setReloadSettings(ReloadSettings.AUTOMATICALLY)
                .setDataType(DataType.SORTED)
                .createConfig();
    }


}
