package me.alen_alex.diamondbank.config;

import me.alen_alex.diamondbank.DiamondBank;
import me.alen_alex.diamondbank.config.core.AbstractFile;
import me.alen_alex.diamondbank.gui.core.button.Buttons;
import me.alen_alex.diamondbank.gui.core.button.Fillers;
import me.alen_alex.diamondbank.utils.modules.ItemUtils;
import me.alen_alex.diamondbank.utils.modules.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public final class Configuration extends AbstractFile {

    private String mainMenuName;
    private int mainMenuSize;
    private Buttons mainCloseButton,mainDepositButton,mainWithdrawButton,mainDetailButton;
    private final List<Fillers> mainMenuFillers;

    public Configuration(DiamondBank plugin) {
        super(plugin);
        this.mainMenuFillers = new ArrayList<>();
    }

    public boolean initConfig(){
        return this.verifyFile();
    }

    public void loadConfigurationFile(){
        this.mainMenuName = MessageUtils.colorize(this.file.getString("gui.bank-gui.name"));
        this.mainMenuSize = this.file.getInt("gui.bank-gui.size");
        this.mainCloseButton = Buttons.buildButtons(getSectionOf("gui.bank-gui.button.close-button"));
        this.mainDepositButton = Buttons.buildButtons(getSectionOf("gui.bank-gui.button.deposit-button"));
        this.mainWithdrawButton = Buttons.buildButtons(getSectionOf("gui.bank-gui.button.withdraw-button"));
        this.mainDetailButton = Buttons.buildButtons(getSectionOf("gui.bank-gui.button.detail-button"));
        this.file.singleLayerKeySet("gui.bank-gui.filler").forEach(stringMaterial -> {
            mainMenuFillers.add(Fillers.buildFrom(ItemUtils.getMaterialFrom(stringMaterial),this.file.getStringList("gui.bank-gui.filler."+stringMaterial)));
        });


    }

    public Buttons getMainCloseButton() {
        return mainCloseButton;
    }

    public Buttons getMainDepositButton() {
        return mainDepositButton;
    }

    public Buttons getMainWithdrawButton() {
        return mainWithdrawButton;
    }

    public Buttons getMainDetailButton() {
        return mainDetailButton;
    }

    public String getMainMenuName() {
        return mainMenuName;
    }

    public int getMainMenuSize() {
        return mainMenuSize;
    }

    public List<Fillers> getMainMenuFillers() {
        return mainMenuFillers;
    }
}

