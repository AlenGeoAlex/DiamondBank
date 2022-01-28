package me.alen_alex.diamondbank.test;

import me.alen_alex.diamondbank.DiamondBank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCase implements CommandExecutor {

    private final DiamondBank plugin;

    public TestCase(DiamondBank plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final Player player = ((Player) sender);

        plugin.getGuiManager().getBankMenu().openMenuFor(player);
        return true;
    }
}
