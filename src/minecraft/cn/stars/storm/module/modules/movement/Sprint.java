package cn.stars.storm.module.modules.movement;

import cn.stars.storm.module.Module;
import cn.stars.storm.module.Category;
import cn.stars.storm.utils.ChatUtils;
import cn.stars.storm.utils.RenderUtils;
import et.event.EventHandler;
import et.event.events.EventPreUpdate;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Sprint extends Module {
    public static Minecraft mc;
    public Sprint() {
        super("Sprint", Category.MOVEMENT, Keyboard.KEY_Z);
    }

    @Override
    public void onEnable() {
        ChatUtils.displayMessage("Sprint Enabled.");
    }

    @Override
    public void onDisable() {
        ChatUtils.displayMessage("Sprint Disabled.");
    }

    @EventHandler
    private void onUpdate(EventPreUpdate event) {
        mc.gameSettings.keyBindSprint.pressed = true;
    }
}
