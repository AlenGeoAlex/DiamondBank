package me.alen_alex.diamondbank.utils.modules;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import me.alen_alex.diamondbank.utils.UtilityManager;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageUtils {

    private final UtilityManager manager;

    public MessageUtils(UtilityManager manager) {
        this.manager = manager;
    }

    /*
        Adding this function as static as it doesn't depend on any of the plugin, rather the BukkitAPI itself
    */
    public static String colorize(@NotNull String message){
       return IridiumColorAPI.process(message);
    }

    public static List<String> colorize(@NotNull List<String> messages){
        return messages.stream().map(MessageUtils::colorize).toList();
    }

    public static Component serializeToComponent(@NotNull String message){
        return Component.text(colorize(message));
    }

    public static List<Component> serializeToComponents(@NotNull List<String> messages){
        return messages.stream().map(MessageUtils::serializeToComponent).toList();
    }
    /*
        Adding this function as static as it doesn't depend on any of the plugin, rather the BukkitAPI itself
    */
    public static String deColorize(@NotNull String message){
        return IridiumColorAPI.stripColorFormatting(message);
    }
}
