package me.alen_alex.diamondbank.gui.core.button;

import de.leonhard.storage.sections.FlatFileSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Fillers{

    private final ItemStack material;
    private final List<Integer> slotList;

    public Fillers(ItemStack material, List<Integer> slotList) {
        this.material = material;
        this.slotList = slotList;
    }

    public ItemStack getMaterial() {
        return material;
    }

    public List<Integer> getSlotList() {
        return slotList;
    }

    public static Fillers buildFrom(@NotNull ItemStack stack, List<String> slotNo){
        return new Fillers(stack,slotNo.stream().map(Integer::parseInt).toList());
    }

}
