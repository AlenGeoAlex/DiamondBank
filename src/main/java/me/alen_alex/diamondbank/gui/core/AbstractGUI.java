package me.alen_alex.diamondbank.gui.core;


import dev.triumphteam.gui.guis.Gui;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public abstract class AbstractGUI {

    protected final GUIManager manager;

    public AbstractGUI(GUIManager manager) {
        this.manager = manager;
    }


    public void openMenuFor(@NotNull Player player){
        prepareGUI(player).thenAccept((menu) -> {
            manager.getPlugin().getServer().getScheduler().runTask(manager.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    menu.open(player);
                }
            });
        });
    }

    protected abstract CompletableFuture<Gui> prepareGUI(@NotNull Player player);
}
