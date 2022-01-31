package me.alen_alex.diamondbank.gui.core.button;

import de.leonhard.storage.sections.FlatFileSection;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import me.alen_alex.diamondbank.utils.InternalPlaceholders;
import me.alen_alex.diamondbank.utils.modules.ItemUtils;
import me.alen_alex.diamondbank.utils.modules.MessageUtils;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Buttons{

    private final ItemStack itemStack;
    private final String name;
    private final int slot;
    private final List<String> lore;
    private final int amount;

    public Buttons(ItemStack itemStack, String name, int slot, List<String> lore) {
        this.itemStack = itemStack;
        this.name = name;
        this.slot = slot;
        this.lore = lore;
        this.amount = 1;
    }

    public Buttons(ItemStack itemStack, String name, int slot, List<String> lore, int amount) {
        this.itemStack = itemStack;
        this.name = name;
        this.slot = slot;
        this.lore = lore;
        this.amount = amount;
    }

    public List<String> getLore(InternalPlaceholders... placeholders) {
        List<String> list = lore;
        for(InternalPlaceholders pl : placeholders){
           list = pl.replacePlaceholders(list);
        }
        return list;
    }

    public String getName(InternalPlaceholders... placeholders){
        String returnString = this.name;
        for(InternalPlaceholders pl : placeholders){
            returnString = pl.replacePlaceholders(returnString);
        }
        return returnString;
    }

    public static Buttons buildButtons(@NotNull FlatFileSection section){
        return new Buttons(
                ItemUtils.getMaterialFrom(section.getString("item")),
                section.getString("name"),
                section.getInt("slot"),
                MessageUtils.colorize(section.getStringList("lore")),
                section.getInt("amount")
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

    public int getAmount() {
        return amount;
    }


}
