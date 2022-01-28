package me.alen_alex.diamondbank.gui.core.button;

import de.leonhard.storage.sections.FlatFileSection;
import me.alen_alex.diamondbank.utils.InternalPlaceholders;
import me.alen_alex.diamondbank.utils.modules.ItemUtils;
import me.alen_alex.diamondbank.utils.modules.MessageUtils;
import me.clip.placeholderapi.libs.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Buttons {

    private final ItemStack itemStack;
    private final String name;
    private final int slot;
    private final List<String> lore;

    public Buttons(ItemStack itemStack, String name, int slot, List<String> lore) {
        this.itemStack = itemStack;
        this.name = name;
        this.slot = slot;
        this.lore = lore;
    }

    public List<String> getLore(InternalPlaceholders... placeholders) {
        List<String> list = lore;
        for(InternalPlaceholders pl : placeholders){
           list = pl.replacePlaceholders(list);
        }
        return list;
    }

    public static Buttons buildButtons(@NotNull FlatFileSection section){
        return new Buttons(
                ItemUtils.getMaterialFrom(section.getString("item")),
                section.getString("name"),
                section.getInt("slot"),
                MessageUtils.colorize(section.getStringList("lore"))
        );
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getName() {
        return name;
    }

    public int getSlot() {
        return slot;
    }

    public List<String> getLore() {
        return lore;
    }
}
