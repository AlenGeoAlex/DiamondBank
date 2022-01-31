package me.alen_alex.diamondbank.command;

import me.alen_alex.diamondbank.utils.InternalPlaceholders;
import me.alen_alex.diamondbank.utils.modules.MessageUtils;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.entity.Player;

@Command("dbank")
public class BankCommand extends CommandBase {

    private final Commands commandsClass;

    public BankCommand(Commands commandsClass) {
        this.commandsClass = commandsClass;
    }

    @Default
    @Permission("dbank.bank")
    public void defaultCommand(final Player player){
        commandsClass.getPlugin().getGuiManager().getBankMenu().openMenuFor(player);
    }

    @SubCommand("deposit")
    @Permission("dbank.deposit")
    public void onDepositMenu(final Player player){
        commandsClass.getPlugin().getGuiManager().getDepositMenu().openMenuFor(player);
    }

    @SubCommand("withdraw")
    @Permission("dbank.withdraw")
    public void onWithdrawMenu(final Player player){
        commandsClass.getPlugin().getGuiManager().getWithdrawMenu().openMenuFor(player);
    }

    @SubCommand("transaction")
    @Permission("dbank.permission")
    public void onTransactionMenu(final Player player){
        commandsClass.getPlugin().getGuiManager().getTransactionMenu().openMenuFor(player);
    }

    @SubCommand("help")
    public void onHelpMenu(final Player player){
        Commands.helpCommand.forEach((s) -> {
            MessageUtils.sendColorizedMessage(player,s);
        });
    }

    @SubCommand("set")
    @Permission("dbank.admin.set")
    public void onSetCommand(final Player player,final Player onWhom,Integer amount){
        if(onWhom == null){
            CommandResponses.NO_ARG_PLAYER.sendFor(player);
            return;
        }

        if(amount == null){
            CommandResponses.NO_ARG_SET_AMOUNT.sendFor(player);
            return;
        }

        commandsClass.getPlugin().getManager().getPlayerData(onWhom).setPlayerDiamond(amount);
        CommandResponses.SUCCESSFULLY_SET_AMOUNT.sendFor(player,new InternalPlaceholders("%amount%",amount),new InternalPlaceholders("%player%",player.getName()));
    }
}
