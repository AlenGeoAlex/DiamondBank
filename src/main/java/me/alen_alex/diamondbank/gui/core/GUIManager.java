package me.alen_alex.diamondbank.gui.core;

import me.alen_alex.diamondbank.DiamondBank;
import me.alen_alex.diamondbank.gui.BankMenu;
import me.alen_alex.diamondbank.gui.DepositMenu;

public class GUIManager {

    private final DiamondBank plugin;
    private final BankMenu bankMenu;
    private final DepositMenu depositMenu;

    public GUIManager(DiamondBank plugin) {
        this.plugin = plugin;
        this.bankMenu = new BankMenu(this);
        this.depositMenu = new DepositMenu(this);
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
}
