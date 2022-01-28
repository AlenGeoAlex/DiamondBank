package me.alen_alex.diamondbank.utils;

import me.alen_alex.diamondbank.DiamondBank;
import me.alen_alex.diamondbank.utils.modules.FileUtility;
import me.alen_alex.diamondbank.utils.modules.ItemUtils;
import me.alen_alex.diamondbank.utils.modules.MessageUtils;

public class UtilityManager {

    private final DiamondBank plugin;
    private final FileUtility fileUtility;
    private final MessageUtils messageUtils;
    private final ItemUtils itemUtils;

    public UtilityManager(DiamondBank plugin) {
        this.plugin = plugin;
        this.fileUtility = new FileUtility(this);
        this.messageUtils = new MessageUtils(this);
        this.itemUtils = new ItemUtils(this);
    }

    public FileUtility getFileUtility() {
        return fileUtility;
    }

    public DiamondBank getPlugin() {
        return plugin;
    }

    public MessageUtils getMessageUtils() {
        return messageUtils;
    }

    public ItemUtils getItemUtils() {
        return itemUtils;
    }
}
