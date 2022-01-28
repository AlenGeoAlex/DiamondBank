package me.alen_alex.diamondbank.gui.core;

import me.alen_alex.diamondbank.DiamondBank;
import me.alen_alex.diamondbank.gui.BankMenu;

public class GUIManager {

    private final DiamondBank plugin;
    private final BankMenu bankMenu;

    public GUIManager(DiamondBank plugin) {
        this.plugin = plugin;
        this.bankMenu = new BankMenu(this);
    }

    public void initAllMenus(){
        bankMenu.initGui();
    }

    public DiamondBank getPlugin() {
        return plugin;
    }

    public BankMenu getBankMenu() {
        return bankMenu;
    }
}
