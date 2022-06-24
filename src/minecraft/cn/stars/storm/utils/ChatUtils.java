package cn.stars.storm.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtils {
    public static String prefix = "§8[§b§lStorm§r§8]§r";
    public static Minecraft mc = Minecraft.getMinecraft();

    public static void displayMessage(Object message) {
        String m2 = prefix + message;
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(m2));
    }

    public static void displayMessageNoPrefix(Object message) {
        String m2 = String.valueOf(message);
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(m2));
    }
}
