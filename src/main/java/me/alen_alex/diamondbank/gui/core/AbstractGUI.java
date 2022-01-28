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

    public abstract void initGui();

    public abstract void openMenuFor(@NotNull Player player);

    public abstract CompletableFuture<Gui> prepareGUI(@NotNull Player player);
}
