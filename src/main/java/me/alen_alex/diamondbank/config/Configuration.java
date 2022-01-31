package me.alen_alex.diamondbank.config;

import me.alen_alex.diamondbank.DiamondBank;
import me.alen_alex.diamondbank.config.core.AbstractFile;
import me.alen_alex.diamondbank.gui.core.button.Buttons;
import me.alen_alex.diamondbank.gui.core.button.Fillers;
import me.alen_alex.diamondbank.utils.InternalPlaceholders;
import me.alen_alex.diamondbank.utils.modules.ItemUtils;
import me.alen_alex.diamondbank.utils.modules.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public final class Configuration extends AbstractFile {

    private boolean dropOnFull;

    private String mainMenuName;
    private int mainMenuSize;
    private Buttons mainCloseButton,mainDepositButton,mainWithdrawButton,mainDetailButton;
    private final List<Fillers> mainMenuFillers;

    private int minAmountNeededToDeposit;

    private String depositMenuName;
    private int depositMenuSize;
    private Buttons depositCloseButton,depositDepositButton,depositConfirmationYes,depositConfirmationNo;
    private final List<Fillers> depositMenuFillers;

    private String withdrawMenuName;
    private int withdrawMenuSize;
    private Buttons withdrawCloseButton,withdrawAddOneButton,withdrawAddFiveButton,withdrawAddTenButton,withdrawRemoveOneButton,withdrawRemoveFiveButton,withdrawRemoveTenButton,withdrawConfirmationYes,withdrawConfirmationNo,withdrawDiamondPlaceholder,withdrawDiamondPlaceholderZeroBalance;
    private final List<Fillers> withdrawMenuFillers;


    private String noSpace,droppedDiamond,withdrewDiamonds,depositedDiamonds,reqMinAmount;

    public Configuration(DiamondBank plugin) {
        super(plugin);
        this.mainMenuFillers = new ArrayList<>();
        this.depositMenuFillers = new ArrayList<>();
        this.withdrawMenuFillers = new ArrayList<>();
    }

    public boolean initConfig(){
        return this.verifyFile();
    }

    public void loadConfigurationFile(){
        this.mainMenuFillers.clear();
        this.depositMenuFillers.clear();
        this.withdrawMenuFillers.clear();

        this.dropOnFull = this.file.getBoolean("drop-if-inventory-is-full");
        this.minAmountNeededToDeposit = this.file.getInt("min-amount-needed-to-deposit");

        this.mainMenuName = MessageUtils.colorize(this.file.getString("gui.bank-gui.name"));
        this.mainMenuSize = this.file.getInt("gui.bank-gui.size");
        this.mainCloseButton = Buttons.buildButtons(getSectionOf("gui.bank-gui.button.close-button"));
        this.mainDepositButton = Buttons.buildButtons(getSectionOf("gui.bank-gui.button.deposit-button"));
        this.mainWithdrawButton = Buttons.buildButtons(getSectionOf("gui.bank-gui.button.withdraw-button"));
        this.mainDetailButton = Buttons.buildButtons(getSectionOf("gui.bank-gui.button.detail-button"));
        this.file.singleLayerKeySet("gui.bank-gui.filler").forEach(stringMaterial -> {
            mainMenuFillers.add(Fillers.buildFrom(ItemUtils.getMaterialFrom(stringMaterial),this.file.getStringList("gui.bank-gui.filler."+stringMaterial)));
        });

        this.depositMenuName = MessageUtils.colorize(this.file.getString("gui.deposit-gui.name"));
        this.depositMenuSize = this.file.getInt("gui.deposit-gui.size");
        this.depositCloseButton = Buttons.buildButtons(getSectionOf("gui.deposit-gui.button.close-button"));
        this.depositDepositButton = Buttons.buildButtons(getSectionOf("gui.deposit-gui.button.deposit-button"));
        this.depositConfirmationYes = Buttons.buildButtons(getSectionOf("gui.deposit-gui.button.confirmation-yes"));
        this.depositConfirmationNo = Buttons.buildButtons(getSectionOf("gui.deposit-gui.button.confirmation-no"));
        this.file.singleLayerKeySet("gui.deposit-gui.filler").forEach((stringMaterial) -> {
            this.depositMenuFillers.add(Fillers.buildFrom(ItemUtils.getMaterialFrom(stringMaterial),this.file.getStringList("gui.deposit-gui.filler."+stringMaterial)));
        });

        this.withdrawMenuName = MessageUtils.colorize(this.file.getString("gui.withdraw-button.name"));
        this.withdrawMenuSize = this.file.getInt("gui.withdraw-button.size");
        this.withdrawCloseButton = Buttons.buildButtons(getSectionOf("gui.withdraw-button.button.close-button"));
        this.withdrawAddOneButton = Buttons.buildButtons(getSectionOf("gui.withdraw-button.button.add-one"));
        this.withdrawAddFiveButton = Buttons.buildButtons(getSectionOf("gui.withdraw-button.button.add-five"));
        this.withdrawAddTenButton = Buttons.buildButtons(getSectionOf("gui.withdraw-button.button.add-ten"));
        this.withdrawRemoveOneButton = Buttons.buildButtons(getSectionOf("gui.withdraw-button.button.reduce-one"));
        this.withdrawRemoveFiveButton = Buttons.buildButtons(getSectionOf("gui.withdraw-button.button.reduce-five"));
        this.withdrawRemoveTenButton = Buttons.buildButtons(getSectionOf("gui.withdraw-button.button.reduce-ten"));
        this.withdrawConfirmationYes = Buttons.buildButtons(getSectionOf("gui.withdraw-button.button.confirmation-yes"));
        this.withdrawConfirmationNo = Buttons.buildButtons(getSectionOf("gui.withdraw-button.button.confirmation-no"));
        this.withdrawDiamondPlaceholder = Buttons.buildButtons(getSectionOf("gui.withdraw-button.button.diamond-placeholder"));
        this.withdrawDiamondPlaceholderZeroBalance = Buttons.buildButtons(getSectionOf("gui.withdraw-button.button.diamond-placeholder-zero-bal"));
        this.file.singleLayerKeySet("gui.withdraw-button.filler").forEach(stringMaterial -> {
            withdrawMenuFillers.add(Fillers.buildFrom(ItemUtils.getMaterialFrom(stringMaterial),this.file.getStringList("gui.withdraw-button.filler."+stringMaterial)));
        });

        this.noSpace = MessageUtils.colorize(this.file.getString("messages.not-enough-space"));
        this.droppedDiamond = MessageUtils.colorize(this.file.getString("messages.item-dropped-to-ground"));
        this.withdrewDiamonds = MessageUtils.colorize(this.file.getString("messages.withdrawed-diamond"));
        this.reqMinAmount = MessageUtils.colorize(this.file.getString("messages.need-atleast-min-amount"));
        this.depositedDiamonds = MessageUtils.colorize(this.file.getString("messages.deposited-diamonds"));
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

    public String getDepositMenuName() {
        return depositMenuName;
    }

    public int getDepositMenuSize() {
        return depositMenuSize;
    }

    public Buttons getDepositCloseButton() {
        return depositCloseButton;
    }

    public Buttons getDepositDepositButton() {
        return depositDepositButton;
    }

    public Buttons getDepositConfirmationYes() {
        return depositConfirmationYes;
    }

    public Buttons getDepositConfirmationNo() {
        return depositConfirmationNo;
    }

    public List<Fillers> getDepositMenuFillers() {
        return depositMenuFillers;
    }

    public boolean isDropOnFull() {
        return dropOnFull;
    }

    public int getMinAmountNeededToDeposit() {
        return minAmountNeededToDeposit;
    }

    public String getWithdrawMenuName() {
        return withdrawMenuName;
    }

    public int getWithdrawMenuSize() {
        return withdrawMenuSize;
    }

    public List<Fillers> getWithdrawMenuFillers() {
        return withdrawMenuFillers;
    }

    public Buttons getWithdrawCloseButton() {
        return withdrawCloseButton;
    }

    public Buttons getWithdrawAddOneButton() {
        return withdrawAddOneButton;
    }

    public Buttons getWithdrawAddFiveButton() {
        return withdrawAddFiveButton;
    }

    public Buttons getWithdrawAddTenButton() {
        return withdrawAddTenButton;
    }

    public Buttons getWithdrawRemoveOneButton() {
        return withdrawRemoveOneButton;
    }

    public Buttons getWithdrawRemoveFiveButton() {
        return withdrawRemoveFiveButton;
    }

    public Buttons getWithdrawRemoveTenButton() {
        return withdrawRemoveTenButton;
    }

    public Buttons getWithdrawConfirmationYes() {
        return withdrawConfirmationYes;
    }

    public Buttons getWithdrawConfirmationNo() {
        return withdrawConfirmationNo;
    }

    public Buttons getWithdrawDiamondPlaceholder() {
        return withdrawDiamondPlaceholder;
    }

    public Buttons getWithdrawDiamondPlaceholderZeroBalance() {
        return withdrawDiamondPlaceholderZeroBalance;
    }

    public String getNoSpace() {
        return noSpace;
    }

    public String getDroppedDiamond() {
        return droppedDiamond;
    }

    public String getWithdrewDiamonds(InternalPlaceholders diamondAmount) {
        return diamondAmount.replacePlaceholders(withdrewDiamonds);
    }

    public String getDepositedDiamonds(InternalPlaceholders diamondAmount) {
        return diamondAmount.replacePlaceholders(depositedDiamonds);
    }

    public String getReqMinAmount(InternalPlaceholders reqAmount) {
        return reqAmount.replacePlaceholders(reqMinAmount);
    }
}

