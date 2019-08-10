package me.Browk.qGifts.Utils;

import me.Browk.qGifts.Handler.LanguageHandler;
import org.bukkit.command.CommandSender;

public interface MessageUtils {

    default String getMessage(String path) {
        return replace(LanguageHandler.getInstance().getConfig().getString(path));
    }

    default String replace(String s) {
        return s.replace("&", "§");
    }

    default void noConsole(CommandSender sender) {
        sender.sendMessage(getMessage("Command.Must be player"));
    }

    default String noPermission() {
        return getMessage("Command.No permission");
    }

    default void resetAllMessages() {
        LanguageHandler.getInstance().resetConfig();
    }

}
