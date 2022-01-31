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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class WithdrawMenu extends AbstractGUI {

    public WithdrawMenu(GUIManager manager) {
        super(manager);
    }

    @Override
    public CompletableFuture<Gui> prepareGUI(@NotNull Player player) {
        return CompletableFuture.supplyAsync(new Supplier<Gui>() {
            @Override
            public Gui get() {
                final Gui gui = Gui.gui()
                        .title(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawMenuName()))
                        .rows(manager.getPlugin().getConfiguration().getWithdrawMenuSize())
                        .disableAllInteractions()
                        .create();

                manager.getPlugin().getConfiguration().getWithdrawMenuFillers().forEach((filler) -> {
                    filler.getSlotList().forEach((slot) -> {
                        gui.setItem(slot, ItemBuilder.from(filler.getMaterial()).asGuiItem());
                    });
                });

                final GuiItem closeButton = ItemBuilder.from(manager.getPlugin().getConfiguration().getWithdrawCloseButton().getItemStack())
                        .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawCloseButton().getName()))
                        .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getWithdrawCloseButton().getLore()))
                        .amount(manager.getPlugin().getConfiguration().getWithdrawCloseButton().getAmount())
                        .asGuiItem(event -> gui.close(player));

                gui.setItem(manager.getPlugin().getConfiguration().getWithdrawCloseButton().getSlot(),closeButton);

                //If the player has no balance
                if(manager.getPlugin().getManager().getPlayerData(player).getPlayerDiamond() < 1){

                    final GuiItem item = ItemBuilder.from(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholderZeroBalance().getItemStack())
                            .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholderZeroBalance().getName()))
                            .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholderZeroBalance().getLore()))
                            .amount(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholderZeroBalance().getAmount())
                            .asGuiItem();

                    gui.setItem(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholderZeroBalance().getSlot(),item);
                    return gui;
                }
                //If the player has balance
                final GuiItem diamondButton = ItemBuilder.from(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getItemStack())
                        .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getName()))
                        .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getLore()))
                        .asGuiItem();

                final GuiItem addOne = ItemBuilder.from(manager.getPlugin().getConfiguration().getWithdrawAddOneButton().getItemStack())
                        .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawAddOneButton().getName()))
                        .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getWithdrawAddOneButton().getLore()))
                        .amount(manager.getPlugin().getConfiguration().getWithdrawAddOneButton().getAmount())
                        .asGuiItem(event -> {
                            final GuiItem diamondStack = gui.getGuiItem(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getSlot());

                            if(diamondStack == null)
                                return;

                            int finalCount = diamondStack.getItemStack().getAmount() + 1;

                            if(finalCount >= 64)
                                finalCount = 64;

                            if(finalCount >= manager.getPlugin().getManager().getPlayerData(player).getPlayerDiamond())
                                finalCount = manager.getPlugin().getManager().getPlayerData(player).getPlayerDiamond();


                            final GuiItem updatedItem = ItemBuilder.from(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getItemStack())
                                    .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getName()))
                                    .amount(finalCount)
                                    .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getLore()))
                                    .asGuiItem();
                            gui.updateItem(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getSlot(),updatedItem);
                        });

                final GuiItem addFive = ItemBuilder.from(manager.getPlugin().getConfiguration().getWithdrawAddFiveButton().getItemStack())
                        .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawAddFiveButton().getName()))
                        .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getWithdrawAddFiveButton().getLore()))
                        .amount(manager.getPlugin().getConfiguration().getWithdrawAddFiveButton().getAmount())
                        .asGuiItem(event -> {
                            final GuiItem diamondStack = gui.getGuiItem(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getSlot());

                            if(diamondStack == null)
                                return;

                            int finalCount = diamondStack.getItemStack().getAmount() + 5;

                            if(finalCount >= 64)
                                finalCount = 64;

                            if(finalCount >= manager.getPlugin().getManager().getPlayerData(player).getPlayerDiamond())
                                finalCount = manager.getPlugin().getManager().getPlayerData(player).getPlayerDiamond();



                            final GuiItem updatedItem = ItemBuilder.from(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getItemStack())
                                    .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getName()))
                                    .amount(finalCount)
                                    .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getLore()))
                                    .asGuiItem();
                            gui.updateItem(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getSlot(),updatedItem);
                        });

                final GuiItem addTen = ItemBuilder.from(manager.getPlugin().getConfiguration().getWithdrawAddTenButton().getItemStack())
                        .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawAddTenButton().getName()))
                        .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getWithdrawAddTenButton().getLore()))
                        .amount(manager.getPlugin().getConfiguration().getWithdrawAddTenButton().getAmount())
                        .asGuiItem(event -> {
                            final GuiItem diamondStack = gui.getGuiItem(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getSlot());

                            if(diamondStack == null)
                                return;

                            int finalCount = diamondStack.getItemStack().getAmount() + 10;

                            if(finalCount >= 64)
                                finalCount = 64;

                            if(finalCount >= manager.getPlugin().getManager().getPlayerData(player).getPlayerDiamond())
                                finalCount = manager.getPlugin().getManager().getPlayerData(player).getPlayerDiamond();



                            final GuiItem updatedItem = ItemBuilder.from(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getItemStack())
                                    .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getName()))
                                    .amount(finalCount)
                                    .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getLore()))
                                    .asGuiItem();
                            gui.updateItem(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getSlot(),updatedItem);
                        });

                final GuiItem removeOne = ItemBuilder.from(manager.getPlugin().getConfiguration().getWithdrawRemoveOneButton().getItemStack())
                        .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawRemoveOneButton().getName()))
                        .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getWithdrawRemoveOneButton().getLore()))
                        .amount(manager.getPlugin().getConfiguration().getWithdrawRemoveOneButton().getAmount())
                        .asGuiItem(event -> {
                            final GuiItem diamondStack = gui.getGuiItem(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getSlot());

                            if(diamondStack == null)
                                return;

                            int finalCount = diamondStack.getItemStack().getAmount() - 1;

                            if(finalCount <= 1)
                                finalCount = 1;


                            final GuiItem updatedItem = ItemBuilder.from(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getItemStack())
                                    .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getName()))
                                    .amount(finalCount)
                                    .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getLore()))
                                    .asGuiItem();

                            gui.updateItem(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getSlot(),updatedItem);
                        });

                final GuiItem removeFive = ItemBuilder.from(manager.getPlugin().getConfiguration().getWithdrawRemoveFiveButton().getItemStack())
                        .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawRemoveFiveButton().getName()))
                        .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getWithdrawRemoveFiveButton().getLore()))
                        .amount(manager.getPlugin().getConfiguration().getWithdrawRemoveFiveButton().getAmount())
                        .asGuiItem(event -> {
                            final GuiItem diamondStack = gui.getGuiItem(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getSlot());

                            if(diamondStack == null)
                                return;

                            int finalCount = diamondStack.getItemStack().getAmount() - 5;

                            if(finalCount <= 1)
                                finalCount = 1;



                            final GuiItem updatedItem = ItemBuilder.from(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getItemStack())
                                    .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getName()))
                                    .amount(finalCount)
                                    .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getLore()))
                                    .asGuiItem();
                            gui.updateItem(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getSlot(),updatedItem);
                        });

                final GuiItem removeTen = ItemBuilder.from(manager.getPlugin().getConfiguration().getWithdrawRemoveTenButton().getItemStack())
                        .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawRemoveTenButton().getName()))
                        .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getWithdrawRemoveTenButton().getLore()))
                        .amount(manager.getPlugin().getConfiguration().getWithdrawRemoveTenButton().getAmount())
                        .asGuiItem(event -> {
                            final GuiItem diamondStack = gui.getGuiItem(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getSlot());

                            if(diamondStack == null)
                                return;

                            int finalCount = diamondStack.getItemStack().getAmount() - 10;

                            if(finalCount <= 1)
                                finalCount = 1;



                            final GuiItem updatedItem = ItemBuilder.from(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getItemStack())
                                    .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getName()))
                                    .amount(finalCount)
                                    .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getLore()))
                                    .asGuiItem();
                            gui.updateItem(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getSlot(),updatedItem);
                        });

                final GuiItem confirmYes = ItemBuilder.from(manager.getPlugin().getConfiguration().getWithdrawConfirmationYes().getItemStack())
                        .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawConfirmationYes().getName()))
                        .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getWithdrawConfirmationYes().getLore()))
                        .amount(manager.getPlugin().getConfiguration().getWithdrawConfirmationYes().getAmount())
                        .asGuiItem(event -> {

                            final ItemStack stack = gui.getGuiItem(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getSlot()).getItemStack();

                            if(stack.getAmount() > manager.getPlugin().getManager().getPlayerData(player).getPlayerDiamond())
                                return;

                            gui.close(player);
                            if(player.getInventory().firstEmpty() == -1){
                                MessageUtils.sendColorizedMessage(player,manager.getPlugin().getConfiguration().getNoSpace());
                                if(manager.getPlugin().getConfiguration().isDropOnFull()){
                                    player.getWorld().dropItem(player.getLocation(),stack);
                                    MessageUtils.sendColorizedMessage(player,manager.getPlugin().getConfiguration().getDroppedDiamond());
                                }else return;
                            }else {
                                player.getInventory().addItem(stack);
                            }
                            final Transaction newTrans = new Transaction(player.getUniqueId(),stack.getAmount(), TransactionWay.WITHDRAW);
                            manager.getPlugin().getManager().processTransaction(newTrans);
                            MessageUtils.sendColorizedMessage(player,manager.getPlugin().getConfiguration().getWithdrewDiamonds(new InternalPlaceholders("%amount%",stack.getAmount())));
                        });

                final GuiItem confirmNo = ItemBuilder.from(manager.getPlugin().getConfiguration().getWithdrawConfirmationNo().getItemStack())
                        .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawConfirmationNo().getName()))
                        .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getWithdrawConfirmationNo().getLore()))
                        .amount(manager.getPlugin().getConfiguration().getWithdrawConfirmationNo().getAmount())
                        .asGuiItem(event -> {
                            final GuiItem updatedItem = ItemBuilder.from(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getItemStack())
                                    .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getName()))
                                    .amount(1)
                                    .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getLore()))
                                    .asGuiItem();

                            gui.updateItem(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getSlot(),updatedItem);

                        });

                gui.setItem(manager.getPlugin().getConfiguration().getWithdrawRemoveTenButton().getSlot(),removeTen);
                gui.setItem(manager.getPlugin().getConfiguration().getWithdrawRemoveFiveButton().getSlot(),removeFive);
                gui.setItem(manager.getPlugin().getConfiguration().getWithdrawRemoveOneButton().getSlot(),removeOne);

                gui.setItem(manager.getPlugin().getConfiguration().getWithdrawAddTenButton().getSlot(),addTen);
                gui.setItem(manager.getPlugin().getConfiguration().getWithdrawAddFiveButton().getSlot(),addFive);
                gui.setItem(manager.getPlugin().getConfiguration().getWithdrawAddOneButton().getSlot(),addOne);

                gui.setItem(manager.getPlugin().getConfiguration().getWithdrawConfirmationYes().getSlot(),confirmYes);
                gui.setItem(manager.getPlugin().getConfiguration().getWithdrawConfirmationNo().getSlot(),confirmNo);

                gui.setItem(manager.getPlugin().getConfiguration().getWithdrawDiamondPlaceholder().getSlot(),diamondButton);

                return gui;
            }
        });
    }
}
