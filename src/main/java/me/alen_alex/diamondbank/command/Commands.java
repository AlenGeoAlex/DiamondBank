package me.alen_alex.diamondbank.command;

import me.alen_alex.diamondbank.DiamondBank;
import me.alen_alex.diamondbank.utils.modules.MessageUtils;
import me.mattstudios.mf.base.CommandManager;

import java.util.ArrayList;
import java.util.List;

public class Commands {

    private final DiamondBank plugin;
    private final CommandManager manager;

    public static List<String> helpCommand = new ArrayList<>(){{
       add(MessageUtils.colorize("&c------------------------------------"));
       add(MessageUtils.colorize("&b/bank &7» &fOpens up the Bank GUI"));
       add(MessageUtils.colorize("&b/bank deposit &7» &fOpens up Deposit GUI"));
       add(MessageUtils.colorize("&b/bank withdraw &7» &fOpens up Withdraw GUI"));
       add(MessageUtils.colorize("&b/bank set &6[player] [amount]&7» &fSets the player diamond to the specified value"));
       add(MessageUtils.colorize("&c------------------------------------"));
    }};

    public Commands(DiamondBank plugin) {
        this.plugin = plugin;
        this.manager = new CommandManager(plugin);
    }

    public void initCommand(){
        manager.register(new BankCommand(this));
    }

    public DiamondBank getPlugin() {
        return plugin;
    }

    public CommandManager getManager() {
        return manager;
    }
}
