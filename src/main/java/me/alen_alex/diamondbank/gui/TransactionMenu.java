package me.alen_alex.diamondbank.gui;

import dev.triumphteam.gui.guis.Gui;
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
    public CompletableFuture<Gui> prepareGUI(@NotNull Player player) {
        return CompletableFuture.supplyAsync(new Supplier<Gui>() {
            @Override
            public Gui get() {
                final Gui gui;
                return null;
            }
        });
    }
}
