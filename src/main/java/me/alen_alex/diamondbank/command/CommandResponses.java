package me.alen_alex.diamondbank.command;

import me.alen_alex.diamondbank.utils.InternalPlaceholders;
import me.alen_alex.diamondbank.utils.modules.MessageUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public enum CommandResponses {
    NO_ARG_PLAYER(MessageUtils.colorize("&cYou didn't provide a player to process with")),
    NO_ARG_SET_AMOUNT(MessageUtils.colorize("&cYou didn't provide a value to set")),
    SUCCESSFULLY_SET_AMOUNT(MessageUtils.colorize("&aYou have set the %amount% diamond successfully for the player %player%"))
    ;

    private String response;

    CommandResponses(String response) {
        this.response = response;
    }

    private String getResponse(InternalPlaceholders... placeholders) {
        String message = response;
        for (InternalPlaceholders pl : placeholders){
            message = pl.replacePlaceholders(message);
        }
        return message;
    }

    public void sendFor(@NotNull final Player player, InternalPlaceholders... placeholders){
        MessageUtils.sendColorizedMessage(player,getResponse(placeholders));
    }
}
