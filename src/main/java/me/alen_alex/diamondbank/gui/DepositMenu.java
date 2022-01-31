package me.alen_alex.diamondbank.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.alen_alex.diamondbank.enums.TransactionWay;
import me.alen_alex.diamondbank.gui.core.AbstractGUI;
import me.alen_alex.diamondbank.gui.core.GUIManager;
import me.alen_alex.diamondbank.model.Transaction;
import me.alen_alex.diamondbank.utils.InternalPlaceholders;
import me.alen_alex.diamondbank.utils.modules.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class DepositMenu extends AbstractGUI {

    public DepositMenu(GUIManager manager) {
        super(manager);
    }

    @Override
    public CompletableFuture<Gui> prepareGUI(@NotNull Player player) {
        return CompletableFuture.supplyAsync(new Supplier<Gui>() {
            @Override
            public Gui get() {
                final Gui gui = Gui.gui()
                        .disableAllInteractions()
                        .title(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getDepositMenuName()))
                        .rows(manager.getPlugin().getConfiguration().getDepositMenuSize())
                        .create();

                final GuiItem closeButton = ItemBuilder.from(manager.getPlugin().getConfiguration().getDepositCloseButton().getItemStack())
                        .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getDepositCloseButton().getName()))
                        .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getDepositCloseButton().getLore()))
                        .asGuiItem(event -> gui.close(player));

                final GuiItem depositButton = ItemBuilder.from(manager.getPlugin().getConfiguration().getDepositDepositButton().getItemStack())
                        .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getDepositDepositButton().getName()))
                        .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getDepositDepositButton().getLore(
                                new InternalPlaceholders("%min-deposit%",manager.getPlugin().getConfiguration().getMinAmountNeededToDeposit())
                        )))
                        .asGuiItem();

                gui.setDefaultClickAction(event -> {
                    if(event.getCurrentItem() == null) {
                        return;
                    }

                    if(event.getCurrentItem().getAmount() < manager.getPlugin().getConfiguration().getMinAmountNeededToDeposit()){
                        MessageUtils.sendColorizedMessage(player,manager.getPlugin().getConfiguration().getReqMinAmount(new InternalPlaceholders("%min%",manager.getPlugin().getConfiguration().getMinAmountNeededToDeposit())));
                        event.setCancelled(true);
                        return;
                    }

                    if(event.getCurrentItem().getType() == Material.DIAMOND && gui.getGuiItem(manager.getPlugin().getConfiguration().getDepositDepositButton().getSlot()).getItemStack().equals(manager.getPlugin().getConfiguration().getDepositDepositButton().getItemStack())){
                        gui.updateTitle(MessageUtils.colorize("&aConfirm transactions"));
                        gui.updateItem(manager.getPlugin().getConfiguration().getDepositDepositButton().getSlot(),ItemBuilder.from(Material.DIAMOND)
                                .amount(event.getCurrentItem().getAmount())
                                .asGuiItem());
                        player.getInventory().setItem(event.getSlot(),null);

                        final GuiItem item = gui.getGuiItem(manager.getPlugin().getConfiguration().getDepositDepositButton().getSlot());

                        final GuiItem yesButton = ItemBuilder.from(manager.getPlugin().getConfiguration().getDepositConfirmationYes().getItemStack())
                                .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getDepositConfirmationYes().getName()))
                                .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getDepositConfirmationYes().getLore()))
                                .asGuiItem(event2 -> {
                                    final Transaction newTrans = new Transaction(player.getUniqueId(),item.getItemStack().getAmount(), TransactionWay.DEPOSIT);
                                    manager.getPlugin().getManager().processTransaction(newTrans);
                                    gui.updateItem(manager.getPlugin().getConfiguration().getDepositDepositButton().getSlot(),depositButton);
                                    gui.close(player);
                                    MessageUtils.sendColorizedMessage(player,manager.getPlugin().getConfiguration().getDepositedDiamonds(new InternalPlaceholders("%amount%",newTrans.getAmount())));
                                });
                        final GuiItem noButton = ItemBuilder.from(manager.getPlugin().getConfiguration().getDepositConfirmationNo().getItemStack())
                                .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getDepositConfirmationNo().getName()))
                                .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getDepositConfirmationNo().getLore()))
                                .asGuiItem(event3 -> {
                                    if(player.getInventory().firstEmpty() == -1){
                                        MessageUtils.sendColorizedMessage(player,manager.getPlugin().getConfiguration().getNoSpace());
                                        if(manager.getPlugin().getConfiguration().isDropOnFull()){
                                            player.getWorld().dropItem(player.getLocation(),item.getItemStack());
                                            MessageUtils.sendColorizedMessage(player,manager.getPlugin().getConfiguration().getDroppedDiamond());
                                        }
                                    }else {
                                        player.getInventory().addItem(item.getItemStack());
                                    }

                                    gui.removeItem(manager.getPlugin().getConfiguration().getDepositConfirmationYes().getSlot());
                                    gui.removeItem(manager.getPlugin().getConfiguration().getDepositConfirmationNo().getSlot());

                                    gui.updateItem(manager.getPlugin().getConfiguration().getDepositDepositButton().getSlot(),depositButton);
                                    gui.updateTitle(manager.getPlugin().getConfiguration().getDepositMenuName());

                                });

                        gui.setItem(manager.getPlugin().getConfiguration().getDepositConfirmationYes().getSlot(),yesButton);
                        gui.setItem(manager.getPlugin().getConfiguration().getDepositConfirmationNo().getSlot(),noButton);
                        gui.update();
                    }
                });

                gui.setCloseGuiAction(event -> {
                    final GuiItem itemAtClose = gui.getGuiItem(manager.getPlugin().getConfiguration().getDepositDepositButton().getSlot());
                    if(itemAtClose == null)
                        return;
                    if(itemAtClose.getItemStack().getType() != Material.DIAMOND)
                        return;
                    if(player.getInventory().firstEmpty() == -1){
                        MessageUtils.sendColorizedMessage(player,manager.getPlugin().getConfiguration().getNoSpace());
                        if(manager.getPlugin().getConfiguration().isDropOnFull()){
                            player.getWorld().dropItem(player.getLocation(),itemAtClose.getItemStack());
                            MessageUtils.sendColorizedMessage(player,manager.getPlugin().getConfiguration().getDroppedDiamond());
                        }
                    }else {
                        player.getInventory().addItem(itemAtClose.getItemStack());
                    }
                });

                manager.getPlugin().getConfiguration().getDepositMenuFillers().forEach((filler) -> {
                    filler.getSlotList().forEach((slot) -> {
                        gui.setItem(slot, ItemBuilder.from(filler.getMaterial()).asGuiItem());
                    });
                });



                gui.setItem(manager.getPlugin().getConfiguration().getDepositDepositButton().getSlot(),depositButton);
                gui.setItem(manager.getPlugin().getConfiguration().getDepositCloseButton().getSlot(),closeButton);
                return gui;
            }
        });
    }
}
