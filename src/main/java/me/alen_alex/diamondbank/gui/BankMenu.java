package me.alen_alex.diamondbank.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.alen_alex.diamondbank.gui.core.AbstractGUI;
import me.alen_alex.diamondbank.gui.core.GUIManager;
import me.alen_alex.diamondbank.utils.InternalPlaceholders;
import me.alen_alex.diamondbank.utils.modules.MessageUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class BankMenu extends AbstractGUI {

    public BankMenu(GUIManager manager) {
        super(manager);
    }

    @Override
    public CompletableFuture<BaseGui> prepareGUI(@NotNull Player player) {
        return CompletableFuture.supplyAsync(new Supplier<BaseGui>() {
            @Override
            public Gui get() {
                final Gui gui =  Gui.gui()
                        .disableAllInteractions()
                        .title(Component.text(manager.getPlugin().getConfiguration().getMainMenuName()))
                        .rows(manager.getPlugin().getConfiguration().getMainMenuSize())
                        .create();

                manager.getPlugin().getConfiguration().getMainMenuFillers().forEach((filler) -> {
                    filler.getSlotList().forEach((slot) -> {
                        gui.setItem(slot,ItemBuilder.from(filler.getMaterial()).asGuiItem());
                    });
                });

                final GuiItem closeButton = ItemBuilder.from(manager.getPlugin().getConfiguration().getMainCloseButton().getItemStack())
                        .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getMainCloseButton().getName()))
                        .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getMainCloseButton().getLore()))
                        .amount(manager.getPlugin().getConfiguration().getMainCloseButton().getAmount())
                        .asGuiItem(event -> {
                            gui.close(player);
                        });

                final GuiItem detailButton = ItemBuilder.from(manager.getPlugin().getConfiguration().getMainDetailButton().getItemStack())
                        .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getMainDetailButton().getName()))
                        .amount(manager.getPlugin().getConfiguration().getMainDetailButton().getAmount())
                        .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getMainDetailButton().getLore(
                                new InternalPlaceholders("%balance%",manager.getPlugin().getManager().getPlayerData(player).getPlayerDiamond()),
                                new InternalPlaceholders("%player_uuid%",player.getUniqueId())
                        )))
                        .asGuiItem();

                final GuiItem depositButton = ItemBuilder.from(manager.getPlugin().getConfiguration().getMainDepositButton().getItemStack())
                        .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getMainDepositButton().getName()))
                        .amount(manager.getPlugin().getConfiguration().getMainDepositButton().getAmount())
                        .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getMainDepositButton().getLore()))
                        .asGuiItem(event -> {
                            gui.close(player);
                            manager.getPlugin().getServer().getScheduler().runTaskLater(manager.getPlugin(), new Runnable() {
                                @Override
                                public void run() {
                                    manager.getDepositMenu().openMenuFor(player);
                                }
                            },11);
                        });

                final GuiItem withdrawButton = ItemBuilder.from(manager.getPlugin().getConfiguration().getMainWithdrawButton().getItemStack())
                        .name(MessageUtils.serializeToComponent(manager.getPlugin().getConfiguration().getMainWithdrawButton().getName()))
                        .amount(manager.getPlugin().getConfiguration().getMainWithdrawButton().getAmount())
                        .lore(MessageUtils.serializeToComponents(manager.getPlugin().getConfiguration().getMainWithdrawButton().getLore()))
                        .asGuiItem(event -> {
                            gui.close(player);
                            manager.getPlugin().getServer().getScheduler().runTaskLater(manager.getPlugin(), new Runnable() {
                                @Override
                                public void run() {
                                    manager.getWithdrawMenu().openMenuFor(player);
                                }
                            },11);
                        });

                gui.setItem(manager.getPlugin().getConfiguration().getMainDetailButton().getSlot(),detailButton);
                gui.setItem(manager.getPlugin().getConfiguration().getMainCloseButton().getSlot(),closeButton);
                gui.setItem(manager.getPlugin().getConfiguration().getMainDepositButton().getSlot(),depositButton);
                gui.setItem(manager.getPlugin().getConfiguration().getMainWithdrawButton().getSlot(),withdrawButton);
                return gui;
            }
        });
    }
}
