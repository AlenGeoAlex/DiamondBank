package me.alen_alex.diamondbank.gui;

import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import me.alen_alex.diamondbank.gui.core.AbstractGUI;
import me.alen_alex.diamondbank.gui.core.GUIManager;
import me.alen_alex.diamondbank.utils.modules.MessageUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TransactionMenu extends AbstractGUI {

    public TransactionMenu(GUIManager manager) {
        super(manager);
    }


    @Override
    public CompletableFuture<BaseGui> prepareGUI(@NotNull Player player) {
        return CompletableFuture.supplyAsync(new Supplier<BaseGui>() {
            @Override
            public BaseGui  get() {
                final PaginatedGui gui = Gui.paginated()
                        .title(MessageUtils.serializeToComponent(MessageUtils.colorize("&6Transactions")))
                        .disableAllInteractions()
                        .create();

                manager.getPlugin().getManager().getTransactionsOf(player).thenAccept(tr -> {

                });
                return gui;
            }
        });
    }
}
