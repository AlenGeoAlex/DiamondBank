package me.alen_alex.diamondbank.gui.core;

import me.alen_alex.diamondbank.DiamondBank;
import me.alen_alex.diamondbank.gui.BankMenu;
import me.alen_alex.diamondbank.gui.DepositMenu;
import me.alen_alex.diamondbank.gui.TransactionMenu;
import me.alen_alex.diamondbank.gui.WithdrawMenu;

public class GUIManager {

    private final DiamondBank plugin;
    private final BankMenu bankMenu;
    private final DepositMenu depositMenu;
    private final WithdrawMenu withdrawMenu;

    private final TransactionMenu transactionMenu;

    public GUIManager(DiamondBank plugin) {
        this.plugin = plugin;
        this.bankMenu = new BankMenu(this);
        this.depositMenu = new DepositMenu(this);
        this.withdrawMenu = new WithdrawMenu(this);
        this.transactionMenu = new TransactionMenu(this);
    }

    public void initAllMenus(){

    }

    public DiamondBank getPlugin() {
        return plugin;
    }

    public BankMenu getBankMenu() {
        return bankMenu;
    }

    public DepositMenu getDepositMenu() {
        return depositMenu;
    }

    public WithdrawMenu getWithdrawMenu() {
        return withdrawMenu;
    }

    public TransactionMenu getTransactionMenu() {
        return transactionMenu;
    }
}
