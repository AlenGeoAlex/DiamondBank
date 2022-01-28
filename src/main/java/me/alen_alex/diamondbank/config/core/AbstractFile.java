package me.alen_alex.diamondbank.config.core;

import de.leonhard.storage.Config;
import de.leonhard.storage.internal.FlatFile;
import de.leonhard.storage.sections.FlatFileSection;
import me.alen_alex.diamondbank.DiamondBank;
import me.alen_alex.diamondbank.utils.modules.ItemUtils;
import me.alen_alex.diamondbank.utils.modules.MessageUtils;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class AbstractFile {

    protected final DiamondBank plugin;
    protected final FlatFile file;

    private static final Material DEFAULT_MATERIAL = Material.GRASS_BLOCK;
    private static final String BASE_HEAD_CODE = "base64:";

    private String fileInitialVersion;

    public AbstractFile(DiamondBank plugin) {
        this.plugin = plugin;
        file = plugin.getPluginUtils().getFileUtility().createConfiguration();
    }

    protected boolean verifyFile(){
        if(file == null)
            return false;

        this.fileInitialVersion = file.getString("version");
        this.file.setDefault("version",plugin.getDescription().getVersion());
        return true;
    }

    protected String parseMessageFrom(@NotNull String fullPath){
        return MessageUtils.colorize(this.file.getString(fullPath));
    }

    protected ArrayList<String> parseMessagesFrom(@NotNull String fullPath){
        final ArrayList<String> messageList = new ArrayList<String>(this.file.getStringList(fullPath).stream().map(MessageUtils::colorize).toList());;
        return messageList;
    }

    protected ItemStack getMaterialFrom(@NotNull String fullPath){
        final String materialNameFetched = this.file.getString(fullPath);
        if(Material.matchMaterial(materialNameFetched) != null){
            return new ItemStack(Material.getMaterial(materialNameFetched));
        }else {
            if(materialNameFetched.startsWith(BASE_HEAD_CODE)){
                final String textureString = materialNameFetched.substring(BASE_HEAD_CODE.length());
                final ItemStack head = ItemUtils.getHead(textureString);
                if(head == null){
                    plugin.getLogger().severe("Unable to fetch the head from the given texture. The default item of "+DEFAULT_MATERIAL.name()+" would be used!");
                    return new ItemStack(DEFAULT_MATERIAL);
                }

                return head;
            }else {
                plugin.getLogger().warning("Unable to properly identify the material provided at "+fullPath+". The default item of "+DEFAULT_MATERIAL.name()+" would be used!");
                return new ItemStack(DEFAULT_MATERIAL);
            }
        }
    }

    protected boolean isConfig(){
        return file instanceof Config;
    }

    protected FlatFileSection getSectionOf(@NotNull String path){
        return new FlatFileSection(this.file,path);
    }

    public String getFileInitialVersion() {
        return fileInitialVersion;
    }
}
